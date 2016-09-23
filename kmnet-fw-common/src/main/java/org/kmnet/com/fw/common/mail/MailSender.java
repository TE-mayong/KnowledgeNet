package org.kmnet.com.fw.common.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.kmnet.com.fw.common.log.Logger;
import org.kmnet.com.fw.common.mail.MailSendingRequest.MESSAGE_TYPE;
import org.kmnet.com.fw.common.mail.MailSendingResponse.MAIL_SENDING_STATUS;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.StringUtils;

/**
 * メール送信サービス呼び出し共通クラス.
 * 
 * @author Li.Yufei
 * @version 1.0 2015/05/13 新規作成
 */
public class MailSender {

	/** このクラスで利用するLoggerインスタンス. */
	private static final Logger LOG = Logger.getLogger(MailSender.class);

	/** メール送信クラス. */
	private JavaMailSender sender;

	/** メールテンプレートのエンジン. */
	private VelocityEngine velocityEngine;

	/** メールテンプレートのデフォルトエンコード. */
	private String defaultTemplateEncoding;

	/**
	 * メール送信クラスを設定する.
	 * 
	 * @param sender
	 *            メール送信クラス
	 */
	public void setSender(JavaMailSender sender) {

		this.sender = sender;
	}

	/**
	 * メールテンプレートのエンジンを設定する.
	 * 
	 * @param velocityEngine
	 *            メールテンプレートのエンジン
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {

		this.velocityEngine = velocityEngine;
	}

	/**
	 * メールテンプレートのデフォルトエンコードを設定する.
	 * 
	 * @param defaultTemplateEncoding
	 *            メールテンプレートのデフォルトエンコード
	 */
	public void setDefaultTemplateEncoding(String defaultTemplateEncoding) {

		this.defaultTemplateEncoding = defaultTemplateEncoding;

	}

	/**
	 * メール送信.
	 * 
	 * @param request
	 *            メール送信の情報
	 * 
	 * @return メール送信結果
	 */
	public MailSendingResponse sendMail(MailSendingRequest request) {

		// メールチェック
		if (!check(request.getMailSendingInfo())) {
			return new MailSendingResponse(MAIL_SENDING_STATUS.FAILURE);
		}

		try {
			if (MESSAGE_TYPE.TEXT == request.getMessageType()) {
				// テキスト形式で送信の場合
				sendTextMail(request);
			} else if (MESSAGE_TYPE.HTML == request.getMessageType()) {
				// HTML形式で送信の場合
				sendHtmlMail(request);
			} else {
				// テキストまたはHTML以外の形式を指定する場合
				LOG.error("Unknown message type is specified.");
				return new MailSendingResponse(MAIL_SENDING_STATUS.FAILURE);
			}
		} catch (Exception e) {
			LOG.error("Failed to send mail.", e);
			return new MailSendingResponse(MAIL_SENDING_STATUS.FAILURE);
		}

		return new MailSendingResponse(MAIL_SENDING_STATUS.SUCCESS);
	}

	/**
	 * テキスト形式で送信.
	 * 
	 * @param request
	 *            メール送信の情報
	 */
	private void sendTextMail(MailSendingRequest request) throws MessagingException, VelocityException {

		MimeMessage mailMessage = sender.createMimeMessage();
		MailSendingInfo sendInfo = request.getMailSendingInfo();
		MimeMessageHelper messageHelper;
		// メールのエンコードを指定する
		if (StringUtils.isEmpty(sendInfo.getMailContentEncoding())) {
			messageHelper = new MimeMessageHelper(mailMessage);
		} else {
			messageHelper = new MimeMessageHelper(mailMessage, sendInfo.getMailContentEncoding());
		}

		// 宛先
		messageHelper.setTo(sendInfo.getTo());
		// CC
		if (sendInfo.isCcEnabled() && sendInfo.getCc() != null) {
			messageHelper.setCc(sendInfo.getCc());
		}
		// 差出人
		messageHelper.setFrom(sendInfo.getFrom());

		String result;
		String templateEncoding;
		// メールテンプレートのエンコードを指定する
		if (StringUtils.isEmpty(sendInfo.getTemplateEncoding())) {
			templateEncoding = defaultTemplateEncoding;
		} else {
			templateEncoding = sendInfo.getTemplateEncoding();
		}
		try {
			// メールのテンプレート
			result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, sendInfo.getTemplateId(),
					templateEncoding, request.getTemplateModel());
			// 件名
			messageHelper.setSubject(sendInfo.getSubject());
		} catch (VelocityException e) {
			LOG.warn("Failed to merge template:" + sendInfo.getTemplateId(), e);
			// メールのテンプレート
			result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, sendInfo.getDefaultTemplateId(),
					templateEncoding, request.getTemplateModel());
			// 件名
			messageHelper.setSubject(sendInfo.getDefaultSubject());
		}
		// 本文
		messageHelper.setText(MailUtils.convMS932ToSjis(result));

		// 送信
		sender.send(mailMessage);
	}

	/**
	 * HTML形式で送信.
	 * 
	 * @param request
	 *            メール送信の情報
	 */
	private void sendHtmlMail(MailSendingRequest request) throws MessagingException, UnsupportedEncodingException,
			VelocityException {

		MimeMessage mailMessage = sender.createMimeMessage();
		MailSendingInfo sendInfo = request.getMailSendingInfo();
		MimeMessageHelper messageHelper;
		// メールのエンコードを指定する
		if (StringUtils.isEmpty(sendInfo.getMailContentEncoding())) {
			messageHelper = new MimeMessageHelper(mailMessage, true);
		} else {
			messageHelper = new MimeMessageHelper(mailMessage, true, sendInfo.getMailContentEncoding());
		}
		// 宛先
		messageHelper.setTo(sendInfo.getTo());
		// CC
		if (sendInfo.isCcEnabled() && sendInfo.getCc() != null) {
			messageHelper.setCc(sendInfo.getCc());
		}
		// 差出人
		messageHelper.setFrom(sendInfo.getFrom(), sendInfo.getFromName());

		String result;
		String templateEncoding;
		// メールテンプレートのエンコードを指定する
		if (StringUtils.isEmpty(sendInfo.getTemplateEncoding())) {
			templateEncoding = defaultTemplateEncoding;
		} else {
			templateEncoding = sendInfo.getTemplateEncoding();
		}
		try {
			// メールのテンプレート
			result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, sendInfo.getTemplateId(),
					templateEncoding, request.getTemplateModel());
			// 件名
			messageHelper.setSubject(sendInfo.getSubject());
		} catch (VelocityException e) {
			LOG.warn("Failed to merge template:" + sendInfo.getTemplateId(), e);
			// メールのテンプレート
			result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, sendInfo.getDefaultTemplateId(),
					templateEncoding, request.getTemplateModel());
			// 件名
			messageHelper.setSubject(sendInfo.getDefaultSubject());
		}
		// 本文
		messageHelper.setText(MailUtils.convMS932ToSjis(result), true);

		// 添付ファイル
		String[] attachementFullPath = sendInfo.getAttachmentFullPath();
		if (attachementFullPath != null) {
			for (String name : attachementFullPath) {
				if (!StringUtils.isEmpty(name)) {
					messageHelper.addAttachment(MimeUtility.encodeText(name.substring(name.lastIndexOf("/") + 1)),
							new File(name));
				}
			}
		}

		// 埋め込みファイル
		Map<String, String> embedFile = sendInfo.getEmbedFile();
		if (embedFile != null) {
			Iterator<String> contentIdIt = embedFile.keySet().iterator();
			while (contentIdIt.hasNext()) {
				String contentId = contentIdIt.next();
				messageHelper.addInline(contentId, new File(embedFile.get(contentId)));
			}
		}

		// 送信
		sender.send(mailMessage);
	}

	/**
	 * メール送信情報チェック.
	 * 
	 * @param info
	 *            メール送信情報
	 * 
	 * @return true:チェックエラー無 false:チェックエラー有
	 */
	private boolean check(MailSendingInfo info) {

		// 差出人アドレス
		if (!checkEmailAddresses(info.getFrom())) {
			return false;
		}

		// 宛先アドレス
		if (info.getTo() == null || info.getTo().length == 0) {
			LOG.error("To address must be set.");
			return false;
		}
		for (String to : info.getTo()) {
			if (!checkEmailAddresses(to)) {
				return false;
			}
		}

		// CCアドレス
		if (info.isCcEnabled() && (info.getCc() != null)) {
			for (String cc : info.getCc()) {
				if (!checkEmailAddresses(cc)) {
					return false;
				}
			}
		}

		// メールのテンプレート必須チェック
		if (StringUtils.isEmpty(info.getTemplateId())) {
			LOG.error("Email template must be specified.");
			return false;
		}

		return true;
	}

	/**
	 * メールアドレスチェック.
	 * 
	 * @param emailAddress
	 *            メールアドレス
	 * 
	 * @return true:チェックエラー無 false:チェックエラー有
	 */
	private boolean checkEmailAddresses(String emailAddress) {

		// メールアドレス必須チェック
		if (StringUtils.isEmpty(emailAddress)) {
			LOG.error("Email address is empty.");
			return false;
		}

		InternetAddress address = null;
		try {
			// メールアドレス有効チェック
			address = new InternetAddress(emailAddress);
			address.validate();
		} catch (AddressException e) {
			LOG.error("Invalid email address: " + emailAddress, e);
			return false;
		}

		return true;
	}
}
