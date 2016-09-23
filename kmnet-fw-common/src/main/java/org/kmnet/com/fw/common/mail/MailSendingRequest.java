package org.kmnet.com.fw.common.mail;

import java.util.HashMap;
import java.util.Map;

/**
 * メール送信情報を保持するBean.
 * 
 * @author Li.Yufei
 * @version 1.0 2015/05/13 新規作成
 */
public class MailSendingRequest {

	/** メール送信タイプ */
	public static enum MESSAGE_TYPE {
		TEXT, HTML
	};

	/** 　送信形式タイプ(Simple、Html) */
	private MESSAGE_TYPE messageType;

	/** メール送信情報 */
	private MailSendingInfo mailSendingInfo;

	/** メールテンプレートの業務データマップ */
	private Map<String, Object> templateModel;

	/**
	 * メール送信情報を保持するBeanコンストラクタ。
	 * 
	 * @param messageType 送信形式タイプ
	 * @param mailSendingInfo メール送信情報
	 */
	public MailSendingRequest(MESSAGE_TYPE messageType, MailSendingInfo mailSendingInfo) {

		this.messageType = messageType;
		this.mailSendingInfo = mailSendingInfo;
		this.templateModel = new HashMap<>();
	}

	/**
	 * 送信形式タイプを取得します。
	 * 
	 * @return 送信形式タイプ
	 */
	MESSAGE_TYPE getMessageType() {

		return messageType;
	}

	/**
	 * メール送信情報を取得します。
	 * 
	 * @return メール送信情報
	 */
	MailSendingInfo getMailSendingInfo() {

		return mailSendingInfo;
	}

	/**
	 * メールテンプレートの業務データマップを取得します。
	 * 
	 * @param templateDataBeanName メールテンプレートの業務データBean名前
	 * @param templateDataBean メールテンプレートの業務データBean
	 */
	public void addTemplateModel(String templateDataBeanName, Object templateDataBean) {

		templateModel.put(templateDataBeanName, templateDataBean);
	}

	/**
	 * メールテンプレートの業務データマップを取得します。
	 * 
	 * @return メールテンプレートの業務データマップ
	 */
	Map<String, Object> getTemplateModel() {

		return templateModel;
	}
}