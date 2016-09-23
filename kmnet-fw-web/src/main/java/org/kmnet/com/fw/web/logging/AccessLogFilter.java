package org.kmnet.com.fw.web.logging;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * アクセスログを出力します.
 * <p>
 * 1処理に対し、リクエスト受信時、レスポンス返却後の二回ログを吐きます。
 */
public class AccessLogFilter implements Filter {

    // ログ出力カテゴリー(デフォルト)
    private static final String DEFAULT_LOG_CATEGORY = "access";

    // Forward時の最終遷移先を識別する為のリクエスト属性キー
    private static final String LAST_SERVLET_PATH = "AccessLogFilter.LAST_SERVLET_PATH";

    // ログ出力クラス
    private Logger log = null;

    /**
     * 初期化
     *
     * @param config
     *            フィルターコンフィグ
     * @throws ServletException
     *
     */
    public void init(FilterConfig config) throws ServletException {
        String logCategory = DEFAULT_LOG_CATEGORY;
        if(config.getInitParameter("LOG_CATEGORY") != null){
            logCategory = config.getInitParameter("LOG_CATEGORY");
        }
        log = LoggerFactory.getLogger(logCategory);
    }

    /**
     * アクセスログを出力します。
     *
     * @param IOException
     * @param ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 開始時間取得
        long startTime = System.nanoTime();

        HttpServletRequest req = (HttpServletRequest) request;

        // 内部でのFORWARD時は実行ログを出力したくないので初回アクセスかどうかを判定
        boolean isFirstAccess = false;

        try {
            if (request.getAttribute(LAST_SERVLET_PATH) == null) {
                // FORWARD時の最終転送先パスがない=初回アクセス
                // 開始ログを出力
                log.info(makeStartLogData(req));
                isFirstAccess = true;
            }
            // FORWARD時の最終転送先パスを保持
            request.setAttribute(LAST_SERVLET_PATH, req.getServletPath());
            // 連鎖(内部でForwardされた場合、再度doFilterから入ってくる)
            chain.doFilter(request, response);
        } finally {
            try {
                // 初回アクセス時の終了処理
                if (isFirstAccess) {
                    // 処理終了時間
                    long endTime = System.nanoTime();
                    // 処理終了ログ
                    log.info(makeEndLogData(startTime, endTime, req));
                }
            } catch (Throwable t) {
                throw new ServletException(t);
            } finally {
            }
        }
    }

    /**
     * 処理開始時のログ出力文字列作成.
     *
     * @param req
     *            サーブレットリクエスト
     * @return ログ出力用文字列
     */
    private String makeStartLogData(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        // Httpメソッド
        sb.append("start");
        sb.append(" ");

        // Httpメソッド
        sb.append(StringUtils.defaultString(req.getMethod(), "-"));
        sb.append(" ");

        // Reference
        if (req.getQueryString() != null) {
            sb.append(req.getRequestURI() + "?" + req.getQueryString());
        } else {
            sb.append(req.getRequestURI());
        }
        sb.append(" ");

        // 接続元アドレス
        sb.append(StringUtils.defaultString(req.getRemoteAddr(), "-"));
        sb.append(" ");

        // リファラー
        sb.append("\"");
        sb.append(StringUtils.defaultString(req.getHeader("Referer"), "-"));
        sb.append("\" ");

        // ユーザエージェント
        sb.append("\"");
        sb.append(StringUtils.defaultString(req.getHeader("User-Agent"), "-"));
        sb.append("\" ");

        return sb.toString();
    }

    /**
     * 処理終了時のログ出力文字列作成.
     *
     * @param startTime
     *            開始時間
     * @param endTime
     *            終了時間
     * @param req
     *            サーブレットリクエスト
     */
    private String makeEndLogData(long startTime, long endTime, HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        sb.append("end");
        sb.append(" ");

        // 遷移先パス
        sb.append(StringUtils.defaultString((String) req.getAttribute(LAST_SERVLET_PATH), "-"));
        sb.append(" ");

        // 処理時間
        long execTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        sb.append(execTime);
        sb.append("ms");
        sb.append(" ");

        return sb.toString();
    }

    public void destroy() {
    }
}
