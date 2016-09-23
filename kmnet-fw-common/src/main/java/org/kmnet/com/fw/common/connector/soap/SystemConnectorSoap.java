package org.kmnet.com.fw.common.connector.soap;

import org.springframework.ws.client.core.WebServiceOperations;

/**
 * SOAP APIを呼び出す共通クラス。
 * 
 * @author Yang.Ye
 * @version 1.0 2015/04/10 新規作成
 */
public interface SystemConnectorSoap {

	/**
	 * Soapテンプレートを設定します。
	 * 
	 * @param webServiceTemplate
	 *            Soapテンプレート
	 */
	public void setWebServiceTemplate(WebServiceOperations webServiceTemplate) ;

	/**
	 * Webサービスを実行し、返却された結果をレスポンスデータの型で返します。
	 * 
	 * @param url 通信先Url
	 * @param soapRequest 送信用Bean
	 * @param <T> 通信処理結果のレスポンスデータ
	 * @param <R> 送信用Beanのリクエストデータ
	 * @return 通信処理結果Bean
	 */
	public <T, R> SoapResponse<T> sendAndReceive(final String url, final SoapRequest<R> soapRequest);
}
