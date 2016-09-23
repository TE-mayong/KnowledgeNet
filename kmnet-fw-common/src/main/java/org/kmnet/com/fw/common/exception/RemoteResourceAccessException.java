package org.kmnet.com.fw.common.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

/**
 * RemoteResourceAccessException <br>
 * RemoteService runtime exception. <br>
 *
 * @author Yang.Ye
 * @version 1.0 2015/05/13 新規作成
 */
public class RemoteResourceAccessException extends DataAccessException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -841760025543152029L;

	/**
	 * 通信状態.
	 */
	private HttpStatus status = null;
	
	/**
	 * RESTエラーメッセージ
	 */
	private String restErrMessage = null;

	/**
	 * 
	 * RemoteResourceAccessExceptionのインスタンスを作成する.
	 *
	 * @param msg  RESTエラーメッセージ
	 * @param stat 通信状態
	 * @param cause {@link Throwable} instance
	 */
	public RemoteResourceAccessException(String msg, HttpStatus stat, Throwable cause) {

		super(msg, cause);
		this.status = stat;
		this.restErrMessage = msg;
	}

	/**
	 * 
	 * RemoteResourceAccessExceptionのインスタンスを作成する.
	 *
	 * @param msg  RESTエラーメッセージ
	 * @param cause {@link Throwable} instance
	 */
	public RemoteResourceAccessException(String msg, Throwable cause) {

		super(msg, cause);
		this.restErrMessage = msg;
	}

	/**
	 * 通信状態を取得する。
	 * @return 通信状態.
	 */
	public HttpStatus getStatus() {

		return status;
	}

	/**
	 * RESTエラーメッセージを取得する。
	 * @return RESTエラーメッセージ
	 */
	public String getRestErrMessage() {
	    return restErrMessage;
	}
}
