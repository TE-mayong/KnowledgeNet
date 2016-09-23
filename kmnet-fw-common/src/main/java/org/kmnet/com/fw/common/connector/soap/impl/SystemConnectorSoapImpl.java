package org.kmnet.com.fw.common.connector.soap.impl;

import java.text.MessageFormat;

import javax.annotation.Resource;

import org.kmnet.com.fw.common.connector.soap.SoapRequest;
import org.kmnet.com.fw.common.connector.soap.SoapResponse;
import org.kmnet.com.fw.common.connector.soap.SystemConnectorSoap;
import org.kmnet.com.fw.common.exception.RemoteResourceAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.oxm.XmlMappingException;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.core.WebServiceOperations;

/**
 * SOAP APIを呼び出す共通クラス。
 * 
 * @author Ma.Yong
 * @version 1.0 2015/09/02 新規作成
 */
public class SystemConnectorSoapImpl implements SystemConnectorSoap{
	/**
	 * エラーメッセージ
	 */
	private static final String ERR_MSG = "SOAP API calling failed. [URL={0};errorMsg={1}]";

	/**
	 * Soapテンプレート
	 * 
	 * @see org.springframework.ws.client.core.WebServiceOperations
	 */
	@Resource
	WebServiceOperations webServiceTemplate;

	/**
	 * Soapテンプレートを設定します。
	 * 
	 * @param webServiceTemplate
	 *            Soapテンプレート
	 */
	public void setWebServiceTemplate(WebServiceOperations webServiceTemplate) {

		this.webServiceTemplate = webServiceTemplate;
	}

	/**
	 * Webサービスを実行し、返却された結果をレスポンスデータの型で返します。
	 * 
	 * @param url 通信先Url
	 * @param soapRequest 送信用Bean
	 * @param <T> 通信処理結果のレスポンスデータ
	 * @param <R> 送信用Beanのリクエストデータ
	 * @return 通信処理結果Bean
	 */
	@SuppressWarnings("unchecked")
	public <T, R> SoapResponse<T> sendAndReceive(final String url, final SoapRequest<R> soapRequest) {
		SoapResponse<T> soapResponse = null;
		try {
			// 通信実行
			Object responseEntity = webServiceTemplate.marshalSendAndReceive(url, soapRequest.getRequestData());
			// 取得結果の設定
			soapResponse = new SoapResponse<T>((T) responseEntity, HttpStatus.OK);
		} catch (WebServiceClientException e) {
			// WebServiceClientの例外が発生の場合
			String msg = MessageFormat.format(ERR_MSG, url, e.getMessage());
			throw new RemoteResourceAccessException(msg, e);
		} catch (XmlMappingException e) {
			// XmlMappingの例外が発生の場合
			String msg = MessageFormat.format(ERR_MSG, url, e.getMessage());
			throw new RemoteResourceAccessException(msg, e);
		} catch (Exception e) {
			e.printStackTrace();
			// そのた例外発生の場合
			String msg = MessageFormat.format(ERR_MSG, url, e.getMessage());
			throw new RemoteResourceAccessException(msg, e);
		}
		return soapResponse;
	}
}
