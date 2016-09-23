package org.kmnet.com.fw.common.connector.rest;

import java.io.Serializable;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

/**
 * REST APIを呼び出す用のリクエストBean。
 * 
 * @param <T> リクエストデータのクラスタイプ
 * @author Yang.Ye
 * @version 1.0 2015/04/10 新規作成
 */
public class RestRequest<T> implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6070024909952172363L;

	/**
	 * リクエストデータ
	 */
	private final T requestData;

	/**
	 * リクエスト方法
	 */
	private final HttpMethod httpMethod;

	/**
	 * リクエストヘッダー
	 */
	private final HttpHeaders httpHeader;

	/**
	 * インスタンス
	 * 
	 * @param requestData
	 *            リクエストデータ
	 * @param httpMethod httpMethod
	 * @param httpHeader httpHeader
	 */
	public RestRequest(T requestData, HttpMethod httpMethod, HttpHeaders httpHeader) {

		super();
		Assert.notNull(httpMethod, "The httpMethod must not be null");
		Assert.notNull(httpHeader, "The httpHeader must not be null");
		Assert.notEmpty(httpHeader.getAccept(), "HttpHeader's accept must be set");
		Assert.notNull(httpHeader.getContentType(), "HttpHeader's contentType must be set");
		this.requestData = requestData;
		this.httpMethod = httpMethod;
		this.httpHeader = httpHeader;
	}

	/**
	 * リクエストデータを取得します。
	 * 
	 * @return リクエストデータ
	 */
	public T getRequestData() {

		return requestData;
	}

	/**
	 * リクエスト方法を取得します。
	 * 
	 * @return リクエスト方法
	 */
	public HttpMethod getHttpMethod() {

		return httpMethod;
	}

	/**
	 * リクエストヘッダーを取得します。
	 * 
	 * @return リクエストヘッダー
	 */
	public HttpHeaders getHttpHeader() {

		return httpHeader;
	}
}
