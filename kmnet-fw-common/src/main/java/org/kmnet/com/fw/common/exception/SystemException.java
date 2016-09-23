package org.kmnet.com.fw.common.exception;

import java.util.Locale;

import org.kmnet.com.fw.common.log.LogMessageUtils;

/**
 * Exception to indicate that it has detected a condition that should not occur
 * when the system is running normally.<br>
 * <p>
 * System exception is to be thrown if something that must exist, is not there
 * (like file, directory, master data etc)
 * </p>
 */
public class SystemException extends RuntimeException implements
		ExceptionCodeProvider {

	private static final long serialVersionUID = 1L;

	/**
	 * exception code.
	 */
	private final String code;

	private Object[] args;

	/**
	 * Constructor<br>
	 * <p>
	 * {@link ExceptionCodeProvider}, message to be displayed and underlying
	 * cause of exception can be specified.
	 * </p>
	 * 
	 * @param code
	 *            ExceptionCode {@link ExceptionCodeProvider}
	 * @param message
	 *            message to be displayed
	 * @param cause
	 *            underlying cause of exception
	 */
	public SystemException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	/**
	 * Constructor<br>
	 * <p>
	 * {@link ExceptionCodeProvider}, message to be displayed can be specified.
	 * </p>
	 * 
	 * @param code
	 *            ExceptionCode {@link ExceptionCodeProvider}
	 * @param message
	 *            message to be displayed
	 */
	public SystemException(String code, String message) {
		super(message);
		this.code = code;
	}

	/**
	 * システム不備ランタイム例外クラスを生成します.
	 * <p>
	 * 147
	 * @param code
	 *            この例外のメッセージコードキー
	 */
	public SystemException(String code) {
		super(LogMessageUtils.getMessage(code, null, Locale.getDefault()));
		this.code = code;
	}

	/**
	 * Constructor<br>
	 * <p>
	 * {@link ExceptionCodeProvider} and underlying cause of exception can be
	 * specified.
	 * </p>
	 * 
	 * @param code
	 *            ExceptionCode {@link ExceptionCodeProvider}
	 * @param cause
	 *            underlying cause of exception
	 */
	public SystemException(String code, Throwable cause) {
		super(LogMessageUtils.getMessage(code, null, Locale.getDefault()), cause);
		this.code = code;
	}

	/**
	 * システム不備ランタイム例外クラスを生成します.
	 * <p>
	 * 
	 * @param code
	 *            この例外のメッセージコードキー
	 * @param args
	 *            この例外のメッセージ置換えパラメーター
	 */
	public SystemException(String code, Object[] args) {
		super(LogMessageUtils.getMessage(code, args, Locale.getDefault()));
		this.code = code;
		this.args = (args == null ? null : args.clone());
	}

	/**
	 * システム不備ランタイム例外クラスを生成します.
	 * <p>
	 * 
	 * @param code
	 *            この例外のメッセージコードキー
	 * @param args
	 *            この例外のメッセージ置換えパラメーター
	 * @param cause
	 *            原因となった例外
	 */
	public SystemException(String code, Object[] args, Throwable cause) {
		super(LogMessageUtils.getMessage(code, args, Locale.getDefault()),
				cause);
		this.code = code;
		this.args = (args == null ? null : args.clone());
	}

	/**
	 * Returns the {@link ExceptionCodeProvider}
	 * 
	 * @see org.kmnet.com.fw.common.exception.ExceptionCodeProvider#getCode()
	 */
	public String getCode() {
		return code;
	}

	/**
	 * この例外のメッセージ置換えパラメーターを取得します.
	 * <p>
	 * 
	 * @return この例外のメッセージ置換えパラメーター
	 */
	public Object[] getArgs() {
		return (args == null ? null : args.clone());
	}

}
