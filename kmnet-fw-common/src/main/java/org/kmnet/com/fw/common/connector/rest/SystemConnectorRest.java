package org.kmnet.com.fw.common.connector.rest;

import org.kmnet.com.fw.common.exception.RemoteResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * Rest APIを呼び出す共通クラス。
 * 
 * @author Yang.Ye
 * @version 1.0 2015/04/10 新規作成
 */
public interface SystemConnectorRest {

	
	/**
	 * Restテンプレートを設定します。
	 * 
	 * @param restTemplate
	 *            Restテンプレート
	 */
	public void setRestTemplate(RestTemplate restTemplate);

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
			throws RemoteResourceAccessException ;
}
