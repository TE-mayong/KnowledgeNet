package org.kmnet.com.fw.web.logging;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.kmnet.com.fw.common.config.BaseSystemConfig;
import org.kmnet.com.fw.common.util.crypt.Crypter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTPパラメータログを出力します
 */
public class RequestLogFilter implements Filter {

	/** このクラスで利用するLoggerインスタンス. */
	private static final Logger LOG = LoggerFactory.getLogger(RequestLogFilter.class);

	/** ログ出力カテゴリー(デフォルト) */
	private static final String DEFAULT_LOG_CATEGORY = "request";

	/** ログ出力カテゴリを指定する場合のFilterConfig上の名称 */
	private static final String FILTER_CONFIG_NAME_LOG_CATEGORY = "logCategory";

	/** 暗号化有無を指定する場合のFilterConfig上の名称 */
	private static final String FILTER_CONFIG_NAME_NEES_ENCRYPT = "needsEncrypt";

	/** 暗号化する際の鍵ID */
	private static final String FILTER_CONFIG_NAME_CRYPT_KEYID = "cryptKeyId";

	/** ログ暗号化用アルゴリズム　**/
	private static final String FILTER_CONFIG_NAME_ALGORITHM_KEY = "encrypt";

	/** 暗号化用インスタンス */
	private static Crypter crypter = null;

	/** Httpパラメータログ出力クラス */
	private Logger requestLog = null;

	/**
	 * ロガーの初期化
	 * 
	 * @param config
	 *            フィルターコンフィグ
	 * @throws ServletException
	 *             発生しません
	 * 
	 */
	public void init(FilterConfig config) throws ServletException {

		String logCategory = DEFAULT_LOG_CATEGORY;
		if (config.getInitParameter(FILTER_CONFIG_NAME_LOG_CATEGORY) != null) {
			logCategory = config.getInitParameter(FILTER_CONFIG_NAME_LOG_CATEGORY);
		}
		if (config.getInitParameter(FILTER_CONFIG_NAME_NEES_ENCRYPT) != null) {
			boolean needsEncrypt = Boolean.parseBoolean(config.getInitParameter(FILTER_CONFIG_NAME_NEES_ENCRYPT));
			if (needsEncrypt) {
				if (config.getInitParameter(FILTER_CONFIG_NAME_CRYPT_KEYID) != null) {
					String keyId = config.getInitParameter(FILTER_CONFIG_NAME_CRYPT_KEYID);
					crypter = Crypter.getCrypter(keyId);
				} else {
					throw new RuntimeException("cryptKeyId must not be null.if needsEncrypt is true.");
				}
			}
		}
		requestLog = LoggerFactory.getLogger(logCategory);
	}

	/**
	 * カテゴリ="request"でログを出力します。
	 * 
	 * @param request
	 *            リクエスト
	 * @param response
	 *            レスポンス
	 * @param chain
	 *            サーブレットチェーン
	 * @throws IOException
	 *             IO例外
	 * @throws ServletException
	 *             サーブレット例外
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		// 属性値をリストに格納
		Enumeration<?> keyEnum = request.getParameterNames();
		StringBuilder logBuffer = new StringBuilder();
		String keyName;

		// ログ暗号化用クラス
		LogEncryptionParameter LogEncryption = new LogEncryptionParameter();
		StringBuilder encryptBuffer = new StringBuilder();
		while (keyEnum.hasMoreElements()) {
			keyName = (String) keyEnum.nextElement();
			String keyValue = request.getParameter(keyName);

			// 対象のvalueを暗号化
			try {
				// マスキングのチャック
                if(LogEncryptionConst.checkEncryptMa(keyName)){
                	logBuffer.append("[");
        			logBuffer.append(keyName);
        			logBuffer.append("]=");
        			logBuffer.append(LogEncryptionConst.getMa(keyValue.length()));
        			logBuffer.append(",");
                }
                //暗号化チャック
                else if(LogEncryptionConst.checkEncrypt(keyName)){
                	encryptBuffer.append("[");
                	encryptBuffer.append(keyName);
                	encryptBuffer.append("]=");
                	encryptBuffer.append(keyValue);
                	encryptBuffer.append(",");
                }
                else{
                	logBuffer.append("[");
        			logBuffer.append(keyName);
        			logBuffer.append("]=");
        			logBuffer.append(keyValue);
        			logBuffer.append(",");
                }
				

			} catch (Exception e) {
				LOG.error("", e);
			}
			
		}
		if(encryptBuffer.length()>0){
			try {
				logBuffer.append(LogEncryption.logEncryptChange(encryptBuffer.toString()));
			} catch (Exception e) {
				LOG.error("", e);
			} 
		}
		 
		if (logBuffer.length() > 0) {
			String message = logBuffer.substring(0, logBuffer.length() - 1).toString();
			if (crypter != null) {
				try {
					message = "enc{" + crypter.encrypt(message) + "}";
				} catch (Exception e) {
					e.printStackTrace();
					LOG.error("", e);
				}
			}
			requestLog.info(message);
		}

		chain.doFilter(request, response);
	}

	/**
	 * 破棄処理.
	 */
	public void destroy() {

	}
}
