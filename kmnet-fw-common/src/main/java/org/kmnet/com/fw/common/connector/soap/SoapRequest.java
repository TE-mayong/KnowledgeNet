package org.kmnet.com.fw.common.connector.soap;

import java.io.Serializable;

import org.springframework.util.Assert;

/**
 * SOAP APIを呼び出す用のリクエストBean。
 */
public class SoapRequest<T> implements Serializable {

	/**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -3232433886661318680L;
    /**
	 * リクエストデータ
	 */
    private final T requestData;

	/**
	 * インスタンス
	 * 
	 * @param requestData
	 *            リクエストデータ
	 */
	public SoapRequest(T requestData) {

		super();
		Assert.notNull(requestData, "The requestData must not be null");
		this.requestData = requestData;
	}

	/**
	 * リクエストデータを取得します。
	 * 
	 * @return リクエストデータ
	 */
	public T getRequestData() {

		return requestData;
	}
}
