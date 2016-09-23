package org.kmnet.com.fw.common.connector.soap;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

/**
 * SOAP APIの処理結果Bean。
 * 
 * @author Yang.Ye
 * @version 1.0 2015/04/10 新規作成
 */
public class SoapResponse<T> implements Serializable {

	/**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 3178382352533241307L;

    /**
	 * レスポンスデータ
	 */
	private T data = null;

	/**
	 * 通信状態
	 */
	private HttpStatus status = null;

	/**
	 * 例外
	 */
	private Exception exception = null;

	/**
	 * エラーメッセージ
	 */
	private String errorMsg = null;

	/**
	 * インスタンス （通信成功）
	 * 
	 * @param data
	 *            レスポンスデータ
	 * @param status
	 *            通信状態(200:OK 404:NOT_FOUND...)
	 */
	public SoapResponse(T data, HttpStatus status) {

		this.data = data;
		this.status = status;
	}

	/**
	 * インスタンス HttpStatus存在(例外のときで利用)
	 * 
	 * @param ex
	 *            例外
	 * @param status
	 *            通信状態(200:OK 404:NOT_FOUND...)
	 */
	public SoapResponse(Exception ex, HttpStatus status) {

		this.status = status;
		this.exception = ex;
		this.errorMsg = ex.getMessage();
	}

	/**
	 * インスタンス HttpStatus存在しない(例外のときで利用)
	 * 
	 * @param ex
	 *            例外
	 */
	public SoapResponse(Exception ex) {

		this.exception = ex;
		this.errorMsg = ex.getMessage();
	}

	/**
	 * レスポンスデータを取得します。
	 * @return レスポンスデータ
	 */
	public T getData() {

		return data;
	}

	/**
	 * レスポンスデータを設定します。
	 * @param data レスポンスデータ
	 */
	public void setData(T data) {

		this.data = data;
	}

	/**
	 * 通信状態を取得します。
	 * @return 通信状態
	 */
	public HttpStatus getStatus() {

		return status;
	}

	/**
	 * 通信状態を設定します。
	 * @param status 通信状態
	 */
	public void setStatus(HttpStatus status) {

		this.status = status;
	}

	/**
	 * 例外を取得します。
	 * @return 例外
	 */
	public Exception getException() {

		return exception;
	}

	/**
	 * 例外を設定します。
	 * @param exception 例外
	 */
	public void setException(Exception exception) {

		this.exception = exception;
	}

	/**
	 * エラーメッセージを取得します。
	 * @return エラーメッセージ
	 */
	public String getErrorMsg() {

		return errorMsg;
	}

	/**
	 * エラーメッセージを設定します。
	 * @param errorMsg エラーメッセージ
	 */
	public void setErrorMsg(String errorMsg) {

		this.errorMsg = errorMsg;
	}
}
