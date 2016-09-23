package org.kmnet.com.fw.web.logging.mdc;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * LogbackのMDCにログ全体を紐づける為のリクエストIDを払いだします
 * 
 */
public class LogbackPatternParamFilter extends OncePerRequestFilter {

	/** リクエストIDのMDC名 */
	public static final String REQUEST_ID = "ReqID";

	/** セッションIDのMDC名 */
	public static final String SESSION_ID = "SesID";

	/** リクエストIDの長さ. */
	private static final int RANDAM_FORMAT_LENGTH = 5;

	/**
	 * MDCに値を設定します.
	 * 
	 * @param request
	 *            リクエスト
	 * @param response
	 *            レスポンス
	 * @param chain
	 *            フィルターチェーン
	 * @throws IOException
	 *             I/O例外発生
	 * @throws ServletException
	 *             例外発生
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		MDC.put(REQUEST_ID,
				RandomStringUtils.randomAlphanumeric(RANDAM_FORMAT_LENGTH));

		HttpSession session = ((HttpServletRequest) request).getSession(true);
		// jsessionidをそのままログ出力するわけにはいかないのでハッシュを取得
		String sessionIdentifier = (Integer.toHexString(session.hashCode()));
		MDC.put(SESSION_ID, sessionIdentifier);

		// ------------------------
		// 後続Filter処理
		// ------------------------
		try {
			filterChain.doFilter(request, response);
		} finally {

			// ------------------------
			// 処理後
			// ------------------------
			MDC.remove(REQUEST_ID);
			MDC.remove(SESSION_ID);
		}

	}

}
