package org.kmnet.com.fw.common.log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.kmnet.com.fw.common.exception.BusinessException;
import org.kmnet.com.fw.common.exception.SystemException;
import org.kmnet.com.fw.common.message.ResultMessages;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public final class Logger implements Serializable {

	/**
	 * Logger suffix of monitoring log.
	 */
	private static final String ALERT_LOG_LOGGER_SUFFIX = "alert";

	/** 管理用Map. */
	private static Map<Object, Logger> loggerMap = new HashMap<Object, Logger>();

	/** 標準ログ用. */
	private transient org.slf4j.Logger applicationLogger = null;

	/** エラーログ用. */
	private static org.slf4j.Logger alertLogger = null;

	static {
		alertLogger = LoggerFactory.getLogger(ALERT_LOG_LOGGER_SUFFIX);
	}

	/**
	 * ログクラスを生成します.
	 */
	private Logger() {

	}

	/**
	 * ログクラスを生成します.
	 * 
	 * @param fqcn
	 *            完全修飾クラス名
	 */
	private Logger(final Class<?> fqcn) {

		this.applicationLogger = LoggerFactory.getLogger(fqcn);
	}

	private Logger(final String fqcn) {

		this.applicationLogger = LoggerFactory.getLogger(fqcn);
	}

	/**
	 * Loggerオブジェクトを作成します.
	 * 
	 * @param fqcn
	 *            完全修飾クラス名
	 * @return 完全修飾クラス名に紐づくLoggerオブジェクト
	 */
	public static final synchronized Logger getLogger(final Class<?> fqcn) {

		Logger logger = (Logger) loggerMap.get(fqcn);
		if (logger == null) {
			logger = new Logger(fqcn);
			loggerMap.put(fqcn, logger);
		}
		return logger;
	}

	/**
	 * Loggerオブジェクトを作成します.
	 * 
	 * @param fqcn
	 *            String
	 * @return Loggerオブジェクト
	 */
	public static final synchronized Logger getLogger(final String fqcn) {

		Logger logger = (Logger) loggerMap.get(fqcn);
		if (logger == null) {
			logger = new Logger(fqcn);
			loggerMap.put(fqcn, logger);
		}
		return logger;
	}

	/**
	 * Debugレベルで出力可能であれば、Debugレベルで出力します.
	 * 
	 * @param message
	 *            出力対象メッセージ
	 */
	public final void debug(final String message) {

		if (isDebugEnabled()) {
			applicationLogger.debug(message);
		}
	}

	/**
	 * Debugレベルで出力可能であれば、Debugレベルで出力します.
	 * 
	 * @param message
	 *            出力対象メッセージ
	 * @param throwable
	 *            スタックトレース対象Throwableオブジェクト
	 */
	public final void debug(final String message, final Throwable throwable) {

		if (isDebugEnabled()) {
			applicationLogger.debug(message, throwable);
		}
	}

	/**
	 * Debugレベルで出力可能かチェックします.
	 * 
	 * @return 出力可能 - true 出力不可 - false
	 */
	public final boolean isDebugEnabled() {

		return applicationLogger.isDebugEnabled();
	}

	/**
	 * Infoレベルで出力可能であれば、Infoレベルで出力します.
	 * 
	 * @param message
	 *            出力対象メッセージ
	 */
	public final void info(final String message) {

		applicationLogger.info(message);
	}

	/**
	 * Infoレベルで出力可能であれば、Infoレベルで出力します.
	 * 
	 * @param message
	 *            出力対象メッセージ
	 * @param throwable
	 *            スタックトレース対象Throwableオブジェクト
	 */
	public final void info(final String message, final Throwable throwable) {

		applicationLogger.info(message, throwable);
	}

	/**
	 * Infoレベルで出力可能かチェックします.
	 * 
	 * @return 出力可能 - true 出力不可 - false
	 */
	public final boolean isInfoEnabled() {

		return applicationLogger.isInfoEnabled();
	}

	/**
	 * Warnレベルで出力可能であれば、Warnレベルで出力します.
	 * 
	 * @param message
	 *            出力対象メッセージ
	 */
	public final void warn(final String message) {

		applicationLogger.warn(message);
	}

	/**
	 * Warnレベルで出力可能であれば、Warnレベルで出力します.
	 * 
	 * @param message
	 *            出力対象メッセージ
	 * @param throwable
	 *            スタックトレース対象Throwableオブジェクト
	 */
	public final void warn(final String message, final Throwable throwable) {

		applicationLogger.warn(message, throwable);
	}

	/**
	 * Warnレベルで出力可能かチェックします.
	 * 
	 * @return 出力可能 - true 出力不可 - false
	 */
	public final boolean isWarnEnabled() {

		return applicationLogger.isWarnEnabled();
	}

	/**
	 * Errorレベルで出力可能であれば、Errorレベルで出力します.
	 * 
	 * @param message
	 *            出力対象メッセージ
	 */
	public final void error(final String message) {

		if (isErrorEnabled()) {
			applicationLogger.error(message);
			alertLogger.error(message);
		}
	}

	/**
	 * Errorレベルで出力可能であれば、Errorレベルで出力します.
	 * 
	 * @param message
	 *            出力対象メッセージ
	 * @param throwable
	 *            スタックトレース対象Throwableオブジェクト
	 */
	public final void error(final String message, final Throwable throwable) {

		applicationLogger.error(message, throwable);
		// エラーログにはトレース吐かない
		alertLogger.error(message + "(" + throwable.getClass().getName() + ")");
	}

	/**
	 * Errorレベルで出力可能かチェックします.
	 * 
	 * @return 出力可能 - true 出力不可 - false
	 */
	public final boolean isErrorEnabled() {

		return applicationLogger.isErrorEnabled();
	}

	/**
	 * ログを出力します.
	 * <p>
	 * リソースよりメッセージコードキーに紐づくメッセージを取得し そのメッセージのログカテゴリーレベルで出力します。
	 * このためメッセージコードキーには、カテゴリーを判別するための情報として
	 * 接頭辞に'D','I','W','E'を付与しておく必要があります。
	 * 
	 * @param key
	 *            メッセージコードキー
	 * @param args
	 *            メッセージ置換えパラメーター
	 */
	public void log(final String key, final Object[] args) {

		char keyType = key.charAt(0);

		String message = "[" + key + "]" + LogMessageUtils.getMessage(key, args, Locale.getDefault());

		switch (keyType) {
		case 'D':
			debug(message);
			break;
		case 'I':
			info(message);
			break;
		case 'W':
			warn(message);
			break;
		case 'E':
			error(message);
			break;
		default:
			error(message);
		}
	}

	/**
	 * ログを出力します.
	 * <p>
	 * リソースよりメッセージコードキーに紐づくメッセージを取得し そのメッセージのログカテゴリーレベルで出力します。
	 * このためメッセージコードキーには、カテゴリーを判別するための情報として
	 * 接頭辞に'D','I','W','E'を付与しておく必要があります。
	 * 
	 * @param key
	 *            メッセージコードキー
	 * @param args
	 *            メッセージ置換えパラメーター
	 * @param throwable
	 *            スタックトレース対象Throwableオブジェクト
	 */
	public final void log(final String key, final Object[] args, final Throwable throwable) {

		char keyType = key.charAt(0);

		String message = "[" + key + "]" + LogMessageUtils.getMessage(key, args, Locale.getDefault());

		switch (keyType) {
		case 'D':
			debug(message, throwable);
			break;
		case 'I':
			info(message, throwable);
			break;
		case 'W':
			warn(message, throwable);
			break;
		case 'E':
			error(message, throwable);
			break;
		default:
			error("Unsupport messageType '" + keyType + "' [" + message + "]", throwable);
		}
	}

	/**
	 * ログを出力します.
	 * <p>
	 * リソースよりメッセージコードキーに紐づくメッセージを取得し そのメッセージのログカテゴリーレベルで出力します。
	 * このためメッセージコードキーには、カテゴリーを判別するための情報として
	 * 接頭辞に'D','I','W','E'を付与しておく必要があります。
	 * 
	 * @param throwable
	 *            スタックトレース対象Throwableオブジェクト
	 */
	public final void log(Throwable throwable) {

		String key = "";
		Object[] args = new Object[] {};

		if (throwable instanceof BusinessException) {

			ResultMessages messages = ((BusinessException) throwable).getResultMessages();

			key = messages.getList().get(0).getCode();
			if (key == null) {
				warn(messages.getList().get(0).getText(), throwable);
				return;
			}
			args = messages.getList().get(0).getArgs();
			if (throwable.getCause() != null) {
				throwable = throwable.getCause();
			}
		} else if (throwable instanceof SystemException) {

			key = ((SystemException) throwable).getCode();
			args = ((SystemException) throwable).getArgs();
			if (throwable.getCause() != null) {
				throwable = throwable.getCause();
			}
		} else {
			error(throwable.getMessage(), throwable);
			return;
		}

		log(key, args, throwable);
	}

}
