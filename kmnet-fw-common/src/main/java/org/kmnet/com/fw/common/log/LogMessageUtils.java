package org.kmnet.com.fw.common.log;

import java.util.Locale;

import org.springframework.context.MessageSource;

public final class LogMessageUtils {

	/** メッセージ */
	private static MessageSource msgSource;

	/**
	 * インスタンス.
	 */
	public LogMessageUtils() {

	}

	/**
	 * コード付きメッセージを取得します. <br>
	 * メッセージコードに紐づくメッセージフォーマットを取得し 置き換える文字列を入れ込んでメッセージを返します. <br>
	 * 出力例：<br>
	 * 
	 * @param messageCode メッセージコード
	 * @param args 置き換える文字列
	 * @return メッセージ
	 */
	public static String getMessage(final String messageCode, final Object[] args) {

		return getMessage(messageCode, args, Locale.getDefault());
	}

	/**
	 * コード付きメッセージを取得します. <br>
	 * メッセージコードに紐づくメッセージフォーマットを取得し 置き換える文字列を入れ込んでメッセージを返します. <br>
	 * 出力例：<br>
	 * 
	 * @param messageCode メッセージコード
	 * @param args 置き換える文字列
	 * @param locale 地域情報
	 * @return メッセージ
	 */
	public static String getMessage(final String messageCode, final Object[] args, final Locale locale) {

		if (messageCode == null) {

			return "[?] Code is Null. " + msgSource.getMessage("", args, null, locale);

		} else {

			return msgSource.getMessage(messageCode, args, null, locale);

		}

	}

	/**
	 * msgSourceを設定します。
	 * 
	 * @param msgSource メッセージ情報
	 *
	 */
	public void setMsgSource(MessageSource msgSource) {

		LogMessageUtils.msgSource = msgSource;
	}

}
