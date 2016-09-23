package org.kmnet.com.fw.common.util.sqltemplate;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



import org.springframework.security.crypto.codec.Base64;




public class LogEncryptionParameter {

	
	/** 暗号化用Key **/
	public static final String ENCRYPT_KEY = "jaielsj39ie82749";
	
	/** 暗号化status **/
	public static final String ENCRYPT_IV = "kiwujdkfnvmdfkps";
	
	/** マスキング　**/
	public static final String ENCRYPT_MA = "################";
	
	/** マスキングPar **/
	public static final String MASKING_PAR = "cardNo";
	
	
	/**
	 * logEncryptionSelect:(Log暗号化選定). <br>
	 * @author Kai.Yosuke
	 * @param keyName
	 * @return
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
//	public String logEncryptionSelect (String logKeyNameValue) throws InvalidKeyException, 
//		UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, 
//		InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
//
//		HashMap<String,String> logEncryptionMapParameter = LogEncryptionConst.LogEncryptionMap();
//		
//		// 暗号化用パラメータの中にkey名があれば暗号化
//		if (logEncryptionMapParameter.get(logKeyNameValue) != null) {
//			logKeyNameValue = logEncryptChange(logKeyNameValue);
//		}
//		return logKeyNameValue;
//	}
//	
	/**
	 * 暗号化します
	 */
	public String logEncryptChange(final String text) throws UnsupportedEncodingException, NoSuchAlgorithmException,
		NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, 
		IllegalBlockSizeException, BadPaddingException {
		// 暗号化テキスト格納場所
		String strResult = null;
		
		// byte取得処理を行う
		byte[] byteText = text.getBytes("UTF-8");
		byte[] byteKey = ENCRYPT_KEY.getBytes("UTF-8");
		byte[] byteIv = ENCRYPT_IV.getBytes("UTF-8");
		
		// 暗号化キーと初期化ベクトルのオブジェクト生成
		SecretKeySpec key = new SecretKeySpec(byteKey, "AES");
		IvParameterSpec ivs = new IvParameterSpec(byteIv);
		
		// Cipherオブジェクト生成
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, ivs);
		
		// 暗号化の結果格納
		byte[] byteResult = cipher.doFinal(byteText);
		strResult = new String(Base64.encode(byteResult));
		// 暗号化文字列を返却
		// 暗号化文字列を返却
		if (strResult.equals(MASKING_PAR)) {
			return ENCRYPT_MA;
		} else {
			return strResult;
		}
		
	}
	
	public void masking(final String text) {

	}
	
	
//	public static String decrypt(String text) {
//		// 変数初期化
//		String strResult = null;
//		byte[] strByte = null;
//		
//		try {
//			// Base64をデコード
//			strByte = text.getBytes("UTF-8");
//			byte[] byteText = Base64.decode(strByte);
//			byte[] byteKey = ENCRYPT_KEY.getBytes("UTF-8");
//			byte[] byteIv = ENCRYPT_IV.getBytes("UTF-8");
// 
//			// 復号化キーと初期化ベクトルのオブジェクト生成
//			SecretKeySpec key = new SecretKeySpec(byteKey, "AES");
//			IvParameterSpec iv = new IvParameterSpec(byteIv);
//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//			cipher.init(Cipher.DECRYPT_MODE, key, iv);
//			byte[] byteResult = cipher.doFinal(byteText);
//			// バイト配列を文字列へ変換
//			strResult = new String(byteResult, "UTF-8");
//		} catch (Exception e) {
//		}
//		// 復号化文字列を返却
//		return strResult;
//	}
}