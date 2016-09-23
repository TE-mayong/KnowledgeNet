package org.kmnet.com.fw.common.mail;

import java.util.Map;

/**
 * メール送信情報を保持するBean.
 * 
 * @author Li.Yufei
 * @version 1.0 2015/05/13 新規作成
 */
public class MailSendingInfo {

	/** 差出人アドレス */
	private String from;

	/** 差出人名前 */
	private String fromName;

	/** 宛先アドレス */
	private String[] to;

	/** CCアドレス */
	private String[] cc;

	/** ディフォルト件名 */
	private String defaultSubject;

	/** 件名 */
	private String subject;

	/** メールのディフォルトテンプレート */
	private String defaultTemplateId;

	/** メールのテンプレート */
	private String templateId;

	/** 埋め込みファイルのコンテントID、フルパス */
	private Map<String, String> embedFile;

	/** 添付ファイルのフルパス */
	private String[] attachmentFullPath;

	/** メールのエンコード */
	private String mailContentEncoding;

	/** テンプレートのエンコード */
	private String templateEncoding;

	/**
	 * デフォールトはCCを使用しない trueを設定とき、CCを使用する
	 */
	private boolean ccEnabled = false;

	/**
	 * 差出人アドレスを取得します。
	 * 
	 * @return 差出人アドレス
	 */
	public String getFrom() {

		return from;
	}

	/**
	 * 差出人アドレスを設定します。
	 * 
	 * @param from
	 *            差出人アドレス
	 */
	public void setFrom(String from) {

		this.from = from;
	}

	/**
	 * 差出人名前を取得します。
	 * 
	 * @return 差出人名前
	 */
	public String getFromName() {

		return fromName;
	}

	/**
	 * 差出人名前を設定します。
	 * 
	 * @param fromName
	 *            差出人名前
	 */
	public void setFromName(String fromName) {

		this.fromName = fromName;
	}

	/**
	 * 宛先アドレスを取得します。
	 * 
	 * @return 宛先アドレス
	 */
	public String[] getTo() {

		return to;
	}

	/**
	 * 宛先アドレスを設定します。
	 * 
	 * @param to
	 *            宛先アドレス
	 */
	public void setTo(String[] to) {

		this.to = to;
	}

	/**
	 * CCアドレスを取得します。
	 * 
	 * @return cc CCアドレス
	 */
	public String[] getCc() {

		return cc;
	}

	/**
	 * CCアドレスを設定します。
	 * 
	 * @param cc
	 *            CCアドレス
	 */
	public void setCc(String[] cc) {

		this.cc = cc;
	}

	/**
	 * ディフォルト件名を取得します。
	 * 
	 * @return ディフォルト件名
	 */
	public String getDefaultSubject() {

		return defaultSubject;
	}

	/**
	 * ディフォルト件名を設定します。
	 * 
	 * @param defaultSubject
	 *            ディフォルト件名
	 */
	public void setDefaultSubject(String defaultSubject) {

		this.defaultSubject = defaultSubject;
	}

	/**
	 * 件名を取得します。
	 * 
	 * @return 件名
	 */
	public String getSubject() {

		return subject;
	}

	/**
	 * 件名を設定します。
	 * 
	 * @param subject
	 *            件名
	 */
	public void setSubject(String subject) {

		this.subject = subject;
	}

	/**
	 * メールのディフォルトテンプレートを取得します。
	 * 
	 * @return メールのディフォルトテンプレート
	 */
	public String getDefaultTemplateId() {

		return defaultTemplateId;
	}

	/**
	 * メールのディフォルトテンプレートを設定します。
	 * 
	 * @param defaultTemplateId
	 *            メールのディフォルトテンプレート
	 */
	public void setDefaultTemplateId(String defaultTemplateId) {

		this.defaultTemplateId = defaultTemplateId;
	}

	/**
	 * メールのテンプレートを取得します。
	 * 
	 * @return メールのテンプレート
	 */
	public String getTemplateId() {

		return templateId;
	}

	/**
	 * メールのテンプレートを設定します。
	 * 
	 * @param templateId
	 *            メールのテンプレート
	 */
	public void setTemplateId(String templateId) {

		this.templateId = templateId;
	}

	/**
	 * 埋め込みファイルのコンテントID、フルパスを取得します。
	 * 
	 * @return 埋め込みファイルのコンテントID、フルパス
	 */
	public Map<String, String> getEmbedFile() {

		return embedFile;
	}

	/**
	 * 埋め込みファイルのコンテントID、フルパスを設定します。
	 * 
	 * @param embedFile
	 *            埋め込みファイルのコンテントID、フルパス
	 */
	public void setEmbedFile(Map<String, String> embedFile) {

		this.embedFile = embedFile;
	}

	/**
	 * メールの添付ファイルのフルパスを取得します。
	 * 
	 * @return メールの添付ファイルのフルパス
	 */
	public String[] getAttachmentFullPath() {

		return attachmentFullPath;
	}

	/**
	 * メールの添付ファイルのフルパスを設定します。
	 * 
	 * @param attachmentFullPath
	 *            メールの添付ファイルのフルパス
	 */
	public void setAttachmentFullPath(String[] attachmentFullPath) {

		this.attachmentFullPath = attachmentFullPath;
	}

	/**
	 * メールのエンコードを取得します。
	 * 
	 * @return mailContentEncoding メールのエンコード
	 */
	public String getMailContentEncoding() {

		return mailContentEncoding;
	}

	/**
	 * 
	 * メールのエンコードを設定します。
	 * 
	 * @param mailContentEncoding
	 *            メールのエンコード
	 */
	public void setMailContentEncoding(String mailContentEncoding) {

		this.mailContentEncoding = mailContentEncoding;
	}

	/**
	 * テンプレートのエンコードを取得します。
	 * 
	 * @return templateEncoding テンプレートのエンコード
	 */
	public String getTemplateEncoding() {

		return templateEncoding;
	}

	/**
	 * 
	 * テンプレートのエンコードを設定します。
	 * 
	 * @param templateEncoding
	 *            テンプレートのエンコード
	 */
	public void setTemplateEncoding(String templateEncoding) {

		this.templateEncoding = templateEncoding;
	}

	/**
	 * cc使用可否フラグを取得します。
	 * 
	 * @return cc使用可否フラグ
	 */
	public boolean isCcEnabled() {

		return ccEnabled;
	}

	/**
	 * cc使用可否フラグを設定します。
	 * 
	 * @param ccEnabled
	 *            cc使用可否フラグ
	 */
	public void setCcEnabled(boolean ccEnabled) {

		this.ccEnabled = ccEnabled;
	}
}