package org.kmnet.com.fw.common.connector.rest.impl;

import java.text.MessageFormat;

import org.kmnet.com.fw.common.connector.rest.RestRequest;
import org.kmnet.com.fw.common.connector.rest.RestResponse;
import org.kmnet.com.fw.common.connector.rest.SystemConnectorRest;
import org.kmnet.com.fw.common.exception.RemoteResourceAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * Rest APIを呼び出す共通クラス。
 * 
 * @author Ma.Yong
 * @version 1.0 2015/09/02 新規作成
 */
public class SystemConnectorRestImpl implements SystemConnectorRest{
	/**
	 * エラーメッセージ
	 */
	private static final String ERR_MSG = "REST API calling failed. [StatusCode={0};URL={1}]";

	/**
	 * Restテンプレート
	 * 
	 * @see org.springframework.web.client.RestTemplate
	 */
	protected RestTemplate restTemplate;

	/**
	 * Restテンプレートを設定します。
	 * 
	 * @param restTemplate
	 *            Restテンプレート
	 */
	public void setRestTemplate(RestTemplate restTemplate) {

		this.restTemplate = restTemplate;
	}

    /**
     * Webサービスを実行し、返却された結果をレスポンスデータの型で返します。
     * 
     * @param url
     *            通信先Url
     * @param restRequest
     *            送信用Bean
     * @param responseType
     *            通信処理結果Bean中レスポンスデータの型
     * @param uriVariables Uri変数
     * @param <T> リクエストデータのクラスタイプ
     * @param <R> レスポンスデータのクラスタイプ
     * @return 通信処理結果Bean
     * @throws RemoteResourceAccessException リモート通信例外
     */
	public <T, R> RestResponse<T> exchange(final String url, final RestRequest<R> restRequest, Class<T> responseType, Object... uriVariables)
			throws RemoteResourceAccessException {

		RestResponse<T> restResponse = null;
		HttpMethod httpMethod = restRequest.getHttpMethod();
		HttpHeaders headers = restRequest.getHttpHeader();

		// メソッド設定しないの場合、POST方法を指定する。
		if (httpMethod == null) {
			httpMethod = HttpMethod.POST;
		}

		try {
			// 送信対象の設定
			HttpEntity<R> requestEntity = new HttpEntity<R>(restRequest.getRequestData(), headers);
			// 通信実行
			ResponseEntity<T> responseEntity = restTemplate.exchange(url, httpMethod, requestEntity, responseType, uriVariables);
			// 取得結果の設定
			restResponse = new RestResponse<T>(responseEntity.getBody(), responseEntity.getStatusCode());
		} catch (HttpStatusCodeException e) {
			// HttpStatus取得可能の例外が発生の場合
			String msg = MessageFormat.format(ERR_MSG, e.getStatusCode().value(), url) ;
			throw new RemoteResourceAccessException(msg, e.getStatusCode(), e);
		} catch (Exception e) {
			// そのた例外発生の場合
			String msg = MessageFormat.format(ERR_MSG, null, url);
			throw new RemoteResourceAccessException(msg, e);
		}
		return restResponse;
	}
}
