/*
 * Copyright (C) 2016 KnowledgeNet.
 */

package org.kmnet.com.fw.common.util.crypt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.codec.Base64;

/**
 * 暗号化・複合化をおこなうクラス. <br>
 * 鍵ID単位で内部に鍵をキャッシュします。
 */
public final class Crypter {

	/**
	 * このCrypterの識別ID
	 */
	private String keyId = null;

	/**
	 * 共通鍵
	 */
	private byte[] key = null;

	/**
	 * アルゴリズム
	 */
	private String algorithm = null;

	/**
	 * アルゴリズムのスラッシュ前の部分
	 */
	private String baseAlgorithm = null;

	/**
	 * Crypterインスタンスキャッシュ用のMap
	 */
	private static Map<String, Crypter> crypterCache = new HashMap<String, Crypter>();

	/**
	 * コンストラクター.
	 * @param name このCrypterを一意に識別する為のキー
	 * @param algorithm 暗号アルゴリズム
	 * @param base64EncodedKey Base64Encodeされた共通鍵
	 */
	private Crypter(String keyId, String algorithm, String base64EncodedKey) {

		this.keyId = keyId;
		byte[] bytes = base64EncodedKey.getBytes();
		key = Base64.decode(bytes);
		this.algorithm = algorithm;
		this.baseAlgorithm = algorithm;
		int offset = algorithm.indexOf('/');
		// 暗号化アルゴリズムに'/'が含まれている場合
		if (offset >= 0) {
			// '/'より前の部分を取得
			baseAlgorithm = algorithm.substring(0, offset);
		}
	}

	/**
	 * 識別名、アルゴリズム、共通鍵を元に、Crypterを生成しキャッシュします。
	 * @param keyId Crypterの識別ID(getする時に使う)
	 * @param algorithm アルゴリズム
	 * @param base64EncodedKey 共通鍵
	 */
	public static void setCryptKey(String keyId, String algorithm, String base64EncodedKey) {

		Crypter crypter = new Crypter(keyId, algorithm, base64EncodedKey);
		crypterCache.put(keyId, crypter);
	}

	/**
	 * キャッシュされたCrypterを返します。
	 * @param keyId Crypterの識別ID
	 * @return アルゴリズム、共通鍵設定済みのCrypter
	 */
	public static Crypter getCrypter(String keyId) {

		return crypterCache.get(keyId);
	}

	/**
	 * 識別名を返します。
	 * @return 識別名
	 */
	public String getKeyId() {

		return keyId;
	}

	/**
	 * アルゴリズムを返します。
	 * @return アルゴリズム
	 */
	public String getAlgorithm() {

		return algorithm;
	}

	/**
	 * 共通鍵を返します。
	 * @return 共通鍵
	 */
	public byte[] getKey() {

		return key;

	}

	/**
	 * 暗号化します.
	 *
	 * @param text 暗号化対象となる文字列
	 *
	 * @return 暗号化された文字列(BASE64でエンコード化される)
	 *
	 * @throws IllegalBlockSizeException 暗号のブロックサイズと一致しない場合にスローされます
	 * @throws InvalidKeyException 無効な符号化、長さの誤り、未初期化などの無効な鍵に対する例外です
	 * @throws NoSuchAlgorithmException
	 *             ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合にスローされます
	 * @throws BadPaddingException
	 *             特定のパディング機構が入力データに対して予期されているのにデータが適切にパディングされない場合にスローされます
	 * @throws NoSuchPaddingException
	 *             あるパディング機構が要求されたにもかかわらず、現在の環境では使用可能でない場合にスローされます
	 * @throws UnsupportedEncodingException 文字のエンコーディングがサポートされていません
	 * @throws InvalidAlgorithmParameterException 無効または不適切なアルゴリズムパラメータの例外です
	 * @throws InvalidKeySpecException 無効な鍵仕様の例外です
	 */
	public String encrypt(final String text) throws IllegalBlockSizeException, InvalidKeyException,
			NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, InvalidKeySpecException {

		return encrypt(text, null);
	}

	/**
	 * 暗号化します.
	 *
	 * @param text 暗号化対象となる文字列
	 * @param iv イニシャルベクター
	 *
	 * @return 暗号化された文字列(BASE64でエンコード化される)
	 *
	 * @throws IllegalBlockSizeException 暗号のブロックサイズと一致しない場合にスローされます
	 * @throws InvalidKeyException 無効な符号化、長さの誤り、未初期化などの無効な鍵に対する例外です
	 * @throws NoSuchAlgorithmException
	 *             ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合にスローされます
	 * @throws BadPaddingException
	 *             特定のパディング機構が入力データに対して予期されているのにデータが適切にパディングされない場合にスローされます
	 * @throws NoSuchPaddingException
	 *             あるパディング機構が要求されたにもかかわらず、現在の環境では使用可能でない場合にスローされます
	 * @throws UnsupportedEncodingException 文字のエンコーディングがサポートされていません
	 * @throws InvalidAlgorithmParameterException 無効または不適切なアルゴリズムパラメータの例外です
	 * @throws InvalidKeySpecException 無効な鍵仕様の例外です
	 */
	public String encrypt(final String text, final byte[] iv) throws IllegalBlockSizeException, InvalidKeyException,
			NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, InvalidKeySpecException {

		Cipher cipher = Cipher.getInstance(algorithm);
		if (iv != null) {
			// イニシャルベクターをパラメータとして生成
			IvParameterSpec dps = new IvParameterSpec(iv);
			// 暗号化準備
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, baseAlgorithm), dps);
		} else {
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, baseAlgorithm));
		}

		// 暗号化実行
		byte[] encrypted = cipher.doFinal(text.getBytes());

		// Base64Encode
		return new String(Base64.encode(encrypted));
	}

	/**
	 * 複合化します.
	 *
	 * @param text 複合化対象文字列(BASE64でエンコード済みであること)
	 *
	 * @return encrypted BASE64でデコードされた複合化対象文字列
	 *
	 * @throws IllegalBlockSizeException 暗号のブロックサイズと一致しない場合にスローされます
	 * @throws InvalidKeyException 無効な符号化、長さの誤り、未初期化などの無効な鍵に対する例外です
	 * @throws NoSuchAlgorithmException
	 *             ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合にスローされます
	 * @throws BadPaddingException
	 *             特定のパディング機構が入力データに対して予期されているのにデータが適切にパディングされない場合にスローされます
	 * @throws NoSuchPaddingException
	 *             あるパディング機構が要求されたにもかかわらず、現在の環境では使用可能でない場合にスローされます
	 * @throws IOException 入出力処理の失敗、または割り込みの発生によって作成される例外
	 * @throws InvalidAlgorithmParameterException 無効または不適切なアルゴリズムパラメータの例外です
	 */
	public String decrypt(final String text) throws IllegalBlockSizeException, InvalidKeyException,
			NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IOException,
			InvalidAlgorithmParameterException {

		return decrypt(text, null);
	}

	/**
	 * 複合化します.
	 *
	 * @param text 複合化対象文字列(BASE64でエンコード済みであること)
	 * @param iv イニシャルベクター
	 *
	 * @return encrypted BASE64でデコードされた複合化対象文字列
	 *
	 * @throws IllegalBlockSizeException 暗号のブロックサイズと一致しない場合にスローされます
	 * @throws InvalidKeyException 無効な符号化、長さの誤り、未初期化などの無効な鍵に対する例外です
	 * @throws NoSuchAlgorithmException
	 *             ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合にスローされます
	 * @throws BadPaddingException
	 *             特定のパディング機構が入力データに対して予期されているのにデータが適切にパディングされない場合にスローされます
	 * @throws NoSuchPaddingException
	 *             あるパディング機構が要求されたにもかかわらず、現在の環境では使用可能でない場合にスローされます
	 * @throws IOException 入出力処理の失敗、または割り込みの発生によって作成される例外
	 * @throws InvalidAlgorithmParameterException 無効または不適切なアルゴリズムパラメータの例外です
	 */
	public String decrypt(final String text, final byte[] iv) throws IllegalBlockSizeException, InvalidKeyException,
			NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IOException,
			InvalidAlgorithmParameterException {

		Cipher cipher = Cipher.getInstance(algorithm);

		if (iv != null) {
			// イニシャルベクターをパラメータとして生成
			IvParameterSpec ips = new IvParameterSpec(iv);
			// 復号化準備
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, baseAlgorithm), ips);
		} else {
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, baseAlgorithm));
		}

		// Base64デコード
		byte[] decodedBytes = Base64.decode(text.getBytes());

		// 複合化実行
		byte[] decrypted = cipher.doFinal(decodedBytes);
		return new String(decrypted);

	}
}
