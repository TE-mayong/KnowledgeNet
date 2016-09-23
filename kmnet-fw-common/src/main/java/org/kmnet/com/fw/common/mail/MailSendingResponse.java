package org.kmnet.com.fw.common.mail;

/**
 * MailSender利用の送信処理結果Bean.
 * 
 * @author Li.Yufei
 * @version 1.0 2015/05/13 新規作成
 */
public class MailSendingResponse {

	/**
	 * メール送信結果
	 */
	public static enum MAIL_SENDING_STATUS {
		SUCCESS, FAILURE
	};

	/**
	 * 状態
	 */
	private MAIL_SENDING_STATUS status;

	/**
	 * インスタンス （通信成功）
	 * 
	 * @param status　通信状態
	 */
	public MailSendingResponse(MAIL_SENDING_STATUS status) {

		this.status = status;
	}

	/**
	 * 通信状態を取得します。
	 * 
	 * @return 通信状態
	 */
	public MAIL_SENDING_STATUS getStatus() {

		return status;
	}
}
