package org.kmnet.com.fw.common.util.validator;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CharacterValidator {

	/** 文字判定ValidatorのMap. */
	private static final Map<Class<?>, CharValidator> CHAR_VALIDATOR_MAP = new HashMap<Class<?>, CharValidator>();

	static {
		CHAR_VALIDATOR_MAP.put(HalfSpaceValidator.class, new HalfSpaceValidator());
		CHAR_VALIDATOR_MAP.put(FullSpaceValidator.class, new FullSpaceValidator());
		CHAR_VALIDATOR_MAP.put(HalfAlphaValidator.class, new HalfAlphaValidator());
		CHAR_VALIDATOR_MAP.put(FullAlphaValidator.class, new FullAlphaValidator());
		CHAR_VALIDATOR_MAP.put(HalfIntValidator.class, new HalfIntValidator());
		CHAR_VALIDATOR_MAP.put(FullIntValidator.class, new FullIntValidator());
		CHAR_VALIDATOR_MAP.put(HalfNumValidator.class, new HalfNumValidator());
		CHAR_VALIDATOR_MAP.put(FullNumValidator.class, new FullNumValidator());
		CHAR_VALIDATOR_MAP.put(HiraganaValidator.class, new HiraganaValidator());
		CHAR_VALIDATOR_MAP.put(HalfKanaValidator.class, new HalfKanaValidator());
		CHAR_VALIDATOR_MAP.put(FullKanaValidator.class, new FullKanaValidator());
		CHAR_VALIDATOR_MAP.put(KanjiValidator.class, new KanjiValidator());
		CHAR_VALIDATOR_MAP.put(HalfSymbolValidator.class, new HalfSymbolValidator());
		CHAR_VALIDATOR_MAP.put(FullSymbolValidator.class, new FullSymbolValidator());
		CHAR_VALIDATOR_MAP.put(MS932CharValidator.class, new MS932CharValidator());
	}

	public static boolean characterValid(String value, String[] checkType) {

		List<CharValidator> charValidatorList = new ArrayList<CharValidator>();

		List<String> tempList = Arrays.asList(checkType);

		/*
		 * 各バリデーションの実施順番によってパフォーマンスが大きく変わる ヒット率の高い文字の種別をバリデーションの先頭に配置すべき
		 */

		// MS932文字
		if (tempList.contains("MS932")) {
			charValidatorList.add(CHAR_VALIDATOR_MAP.get(MS932CharValidator.class));
		}

		// 半角英字
		if (tempList.contains("HalfAlphabet")) {
			charValidatorList.add(CHAR_VALIDATOR_MAP.get(HalfAlphaValidator.class));
		}

		// 半角正数
		if (tempList.contains("HalfInteger")) {
			charValidatorList.add(CHAR_VALIDATOR_MAP.get(HalfIntValidator.class));
		}

		// 半角数字
		if (tempList.contains("HalfNumber")) {
			charValidatorList.add(CHAR_VALIDATOR_MAP.get(HalfNumValidator.class));
		}

		// 半角記号
		if (tempList.contains("HalfSymbol")) {
			charValidatorList.add(CHAR_VALIDATOR_MAP.get(HalfSymbolValidator.class));
		}

		// 漢字
		if (tempList.contains("Kanji")) {
			charValidatorList.add(CHAR_VALIDATOR_MAP.get(KanjiValidator.class));
		}

		// ひらがな
		if (tempList.contains("Hiragana")) {
			charValidatorList.add(CHAR_VALIDATOR_MAP.get(HiraganaValidator.class));
		}

		// 全角カナ
		if (tempList.contains("FullKatakana")) {
			charValidatorList.add(CHAR_VALIDATOR_MAP.get(FullKanaValidator.class));
		}

		// 全角英字
		if (tempList.contains("FullAlphabet")) {
			charValidatorList.add(CHAR_VALIDATOR_MAP.get(FullAlphaValidator.class));
		}

		// 半角カナ
		if (tempList.contains("HalfKatakana")) {
			charValidatorList.add(CHAR_VALIDATOR_MAP.get(HalfKanaValidator.class));
		}

		// 半角SP
		if (tempList.contains("HalfSpace")) {
			charValidatorList.add(CHAR_VALIDATOR_MAP.get(HalfSpaceValidator.class));
		}

		// 全角正数
		if (tempList.contains("FullInteger")) {
			charValidatorList.add(CHAR_VALIDATOR_MAP.get(FullIntValidator.class));
		}

		// 全角数字
		if (tempList.contains("FullNumber")) {
			charValidatorList.add(CHAR_VALIDATOR_MAP.get(FullNumValidator.class));
		}

		// 全角記号
		if (tempList.contains("FullSymbol")) {
			charValidatorList.add(CHAR_VALIDATOR_MAP.get(FullSymbolValidator.class));
		}

		// 全角SP
		if (tempList.contains("FullSpace")) {
			charValidatorList.add(CHAR_VALIDATOR_MAP.get(FullSpaceValidator.class));
		}

		// 許可する文字条件が0個の場合
		if (charValidatorList.size() == 0) {
			return false;
		}

		CharValidator[] charValidators = charValidatorList.toArray(new CharValidator[charValidatorList.size()]);

		// 一文字毎に文字条件を満たしているか判定
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			boolean flg = false;

			// 指定されている文字条件を一つでも満たしているか判定
			for (int j = 0; j < charValidators.length; j++) {
				if (charValidators[j].validate(c)) {
					flg = true;
					break;
				}
			}

			// 指定された文字条件を一つも満たしていない場合はエラー
			if (!flg) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 文字Validatorインターフェース.
	 * <p>
	 */
	private static interface CharValidator {

		/**
		 * 文字をチェックします.
		 * <p>
		 * 
		 * @param c
		 *            検証対象文字
		 * @return NGの場合:false
		 */
		public boolean validate(final char c);

	}

	/**
	 * 半角SP 判定Validator.
	 * <p>
	 */
	private static class HalfSpaceValidator implements CharValidator {

		/**
		 * 半角SP判定を行います.
		 * <p>
		 * 対応文字（Unicode文字ブロック名またはUnicode値）<br>
		 * 半角スペース文字(0x0020)<br>
		 * 
		 * @param c
		 *            検証対象文字
		 * @return 検証対象文字が 半角SP の場合:true
		 */
		public boolean validate(final char c) {

			return (c == '\u0020');
		}

	}

	/**
	 * 全角SP 判定Validator.
	 * <p>
	 */
	private static class FullSpaceValidator implements CharValidator {

		/**
		 * 全角SP判定を行います.
		 * <p>
		 * 対応文字（Unicode文字ブロック名またはUnicode値）<br>
		 * 全角スペース文字(0x3000)<br>
		 * 
		 * @param c
		 *            検証対象文字
		 * @return 検証対象文字が 全角SP の場合:true
		 */
		public boolean validate(final char c) {

			return (c == '\u3000');
		}

	}

	/**
	 * 半角英字 判定Validator.
	 * <p>
	 */
	private static class HalfAlphaValidator implements CharValidator {

		/**
		 * 半角英字判定を行います.
		 * <p>
		 * 対応文字（Unicode文字ブロック名またはUnicode値）<br>
		 * BASIC_LATIN（Unicode文字ブロック名）内のアルファベット文字<br>
		 * 
		 * @param c
		 *            検証対象文字
		 * @return 検証対象文字が 半角英字 の場合:true
		 */
		public boolean validate(final char c) {

			return ('\u0041' <= c && c <= '\u005A') || ('\u0061' <= c && c <= '\u007A');
		}

	}

	/**
	 * 全角英字 判定Validator.
	 * <p>
	 */
	private static class FullAlphaValidator implements CharValidator {

		/**
		 * 全角英字判定を行います.
		 * <p>
		 * 対応文字（Unicode文字ブロック名またはUnicode値）<br>
		 * 0xFF21～0xFF3a，0xFF41～0xFF5a<br>
		 * 
		 * @param c
		 *            検証対象文字
		 * @return 検証対象文字が 全角英字 の場合:true
		 */
		public boolean validate(final char c) {

			return ('\uFF21' <= c && c <= '\uFF3A') || ('\uFF41' <= c && c <= '\uFF5A');
		}

	}

	/**
	 * 半角正数 判定Validator.
	 * <p>
	 */
	private static class HalfIntValidator implements CharValidator {

		/**
		 * 半角正数判定を行います.
		 * <p>
		 * 対応文字（Unicode文字ブロック名またはUnicode値）<br>
		 * BASIC_LATIN（Unicode文字ブロック名）内の数字<br>
		 * 
		 * @param c
		 *            検証対象文字
		 * @return 検証対象文字が 半角正数 の場合:true
		 */
		public boolean validate(final char c) {

			return ('\u0030' <= c && c <= '\u0039');
		}

	}

	/**
	 * 全角正数 判定Validator.
	 * <p>
	 */
	private static class FullIntValidator implements CharValidator {

		/**
		 * 全角正数判定を行います.
		 * <p>
		 * 対応文字（Unicode文字ブロック名またはUnicode値）<br>
		 * 0xFF10～0xFF19<br>
		 * 
		 * @param c
		 *            検証対象文字
		 * @return 検証対象文字が 全角正数 の場合:true
		 */
		public boolean validate(final char c) {

			return ('\uFF10' <= c && c <= '\uFF19');
		}

	}

	/**
	 * 半角数字 判定Validator.
	 * <p>
	 */
	private static class HalfNumValidator implements CharValidator {

		/**
		 * 半角数字判定を行います.
		 * <p>
		 * 対応文字（Unicode文字ブロック名またはUnicode値）<br>
		 * 半角正数，半角マイナス記号，半角小数点記号<br>
		 * 
		 * @param c
		 *            検証対象文字
		 * @return 検証対象文字が 半角数字 の場合:true
		 */
		public boolean validate(final char c) {

			return ('\u0030' <= c && c <= '\u0039') || ('\u002D' == c) || ('\u002E' == c);
		}

	}

	/**
	 * 全角数字 判定Validator.
	 * <p>
	 */
	private static class FullNumValidator implements CharValidator {

		/**
		 * 全角数字判定を行います.
		 * <p>
		 * 対応文字（Unicode文字ブロック名またはUnicode値）<br>
		 * 全角正数，全角マイナス記号，全角小数点記号<br>
		 * 
		 * @param c
		 *            検証対象文字
		 * @return 検証対象文字が 全角数字 の場合:true
		 */
		public boolean validate(final char c) {

			return ('\uFF10' <= c && c <= '\uFF19') || ('\uFF0D' == c) || ('\uFF0E' == c);
		}

	}

	/**
	 * ひらがな 判定Validator.
	 * <p>
	 */
	private static class HiraganaValidator implements CharValidator {

		/**
		 * ひらがな判定を行います.
		 * <p>
		 * 対応文字（Unicode文字ブロック名またはUnicode値）<br>
		 * HIRAGANA（Unicode文字ブロック名），ー<br>
		 * 
		 * @param c
		 *            検証対象文字
		 * @return 検証対象文字が ひらがな の場合:true
		 */
		public boolean validate(final char c) {

			return ('\u3041' <= c && c <= '\u309F') || ('\u30FC' == c);
		}

	}

	/**
	 * 半角カナ 判定Validator.
	 * <p>
	 */
	private static class HalfKanaValidator implements CharValidator {

		/**
		 * 半角カナ判定を行います.
		 * <p>
		 * 対応文字（Unicode文字ブロック名またはUnicode値）<br>
		 * 0xFF65～0xFF9F<br>
		 * 
		 * @param c
		 *            検証対象文字
		 * @return 検証対象文字が 半角カナ の場合:true
		 */
		public boolean validate(final char c) {

			return ('\uFF65' <= c && c <= '\uFF9F');
		}

	}

	/**
	 * 全角カナ 判定Validator.
	 * <p>
	 */
	private static class FullKanaValidator implements CharValidator {

		/**
		 * 全角カナ判定を行います.
		 * <p>
		 * 対応文字（Unicode文字ブロック名またはUnicode値）<br>
		 * KATAKANA（Unicode文字ブロック名）<br>
		 * 
		 * @param c
		 *            検証対象文字
		 * @return 検証対象文字が 全角カナ の場合:true
		 */
		public boolean validate(final char c) {

			return ('\u30A0' <= c && c <= '\u30FF');
		}

	}

	/**
	 * 漢字 判定Validator.
	 * <p>
	 */
	private static class KanjiValidator implements CharValidator {

		/**
		 * 漢字判定を行います.
		 * <p>
		 * 対応文字（Unicode文字ブロック名またはUnicode値）<br>
		 * CJK_UNIFIED_IDEOGRAPHS（CJK統合漢字），々，ヵ，ヶ，ッ<br>
		 * 
		 * @param c
		 *            検証対象文字
		 * @return 検証対象文字が 漢字 の場合:true
		 */
		public boolean validate(final char c) {

			return ('\u4E00' <= c && c <= '\u9FC3') || ('\u3005' == c) || ('\u30F5' == c) || ('\u30F6' == c)
					|| ('\u30C3' == c);
		}

	}

	/**
	 * 半角記号 判定Validator.
	 * <p>
	 */
	private static class HalfSymbolValidator implements CharValidator {

		/**
		 * 半角記号判定を行います.
		 * <p>
		 * 対応文字（Unicode文字ブロック名またはUnicode値）<br>
		 * !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~<br>
		 * 
		 * @param c
		 *            検証対象文字
		 * @return 検証対象文字が 半角記号 の場合:true
		 */
		public boolean validate(final char c) {

			return ('\u0021' <= c && c <= '\u002F') || ('\u003A' <= c && c <= '\u0040')
					|| ('\u005B' <= c && c <= '\u0060') || ('\u007B' <= c && c <= '\u007E');
		}

	}

	/**
	 * 全角記号 判定Validator.
	 * <p>
	 */
	private static class FullSymbolValidator implements CharValidator {

		/**
		 * 全角記号判定を行います.
		 * <p>
		 * 対応文字（Unicode文字ブロック名またはUnicode値）<br>
		 * ！＂＃＄％＆＇（）＊＋，－．／：；＜＝＞？＠［￥］＾＿｀｛｜｝～<br>
		 * 
		 * @param c
		 *            検証対象文字
		 * @return 検証対象文字が 全角記号 の場合:true
		 */
		public boolean validate(final char c) {

			return ('\uFF01' <= c && c <= '\uFF0F') || ('\uFF1A' <= c && c <= '\uFF20') || ('\uFF3B' == c)
					|| ('\uFFE5' == c) || ('\uFF3D' <= c && c <= '\uFF40') || ('\uFF5B' <= c && c <= '\uFF5E');
		}

	}

	/**
	 * MS932文字 判定Validator.
	 * <p>
	 */
	private static class MS932CharValidator implements CharValidator {

		/**
		 * MS932文字判定を行います.
		 * <p>
		 * 
		 * @param c
		 *            検証対象文字
		 * @return 検証対象文字が MS932文字 の場合:true
		 */
		public boolean validate(final char c) {

			try {
				if ((new String(String.valueOf(c).getBytes("MS932"), "MS932").charAt(0)) == c) {
					return true;
				}
			} catch (UnsupportedEncodingException e) {
				// 無視
				return false;
			}

			return false;

		}
	}

	/**
	 * フィールド値の文字列可変byte長チェックをおこないます.
	 * <p>
	 * 
	 * @param value
	 *            チェック内容
	 * @param encode
	 *            エンコード
	 * @param rangeMin
	 *            最小
	 * @param rangeMax
	 *            最大
	 * @return 指定の文字列長を満たしていれば true
	 */
	public static boolean validateFlexibleByteNumber(final String value, final String encode, final int rangeMin,
			final int rangeMax) {

		try {
			// ----------------------------
			// 長さの取得
			// ----------------------------
			byte[] byteStr = value.getBytes(encode);
			int length = byteStr.length;

			// ----------------------------
			// 判定
			// ----------------------------
			if (rangeMin > rangeMax) {
				throw new IllegalArgumentException("Minimum value " + rangeMin + "is greater than maximum value "
						+ rangeMax + ".");
			}

			if ((length >= rangeMin) && (length <= rangeMax)) {
				return true;
			}

			return false;
		} catch (UnsupportedEncodingException e) {
			return false;
		}

	}

	/**
	 * フィールド値の文字列固定byte長チェックをおこないます.
	 * <p>
	 * 
	 * @param value
	 *            チェック内容
	 * @param encode
	 *            エンコード
	 * @param fixLength
	 *            固定
	 * @return 指定の文字列長を満たしていれば true
	 */
	public static boolean validateFixByteNumber(final String value, final String encode, final int fixLength) {

		try {
			// ----------------------------
			// 長さの取得
			// ----------------------------
			byte[] byteStr = value.getBytes(encode);
			int length = byteStr.length;

			// ----------------------------
			// 判定
			// ----------------------------
			if (length == fixLength) {
				return true;
			}
			return false;
		} catch (UnsupportedEncodingException e) {
			return false;
		}
	}

	/**
	 * フィールド値の文字列固定長チェックをおこないます.
	 * <p>
	 * 
	 * @param value
	 *            チェック内容
	 * @param fixLength
	 *            固定
	 * @return 指定の文字列長を満たしていれば true
	 */
	public static boolean validateFixNumber(final String value, final int fixLength) {

		// ----------------------------
		// 長さの取得
		// ----------------------------
		int length = value.length();

		// ----------------------------
		// 判定
		// ----------------------------
		if (length == fixLength) {
			return true;
		}
		return false;

	}

}
