package org.kmnet.com.fw.common.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.kmnet.com.fw.common.log.Logger;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

/**
 * システム設定を保持する基底クラス.
 * 
 */
public class BaseSystemConfig {

	/** ServerConfigKeyのリスト. */
	private static final List<SystemConfigKey> KEY_LIST = new LinkedList<SystemConfigKey>();

	/** プロパティファイルから読み込んだパラメータをオブジェクト化したMap. */
	private static final Map<String, Object> VALUE_MAP = new HashMap<String, Object>();

	/** このクラスで利用するLoggerインスタンス. */
	private static final Logger LOG = Logger.getLogger(BaseSystemConfig.class);

	/** メッセージ */
	private static MessageSource msgSource;

	/**
	 * コンストラクター.
	 */
	protected BaseSystemConfig() {

	}

	/**
	 * 初期化を行います.
	 *
	 */
	@PostConstruct
	public final void setup() {

		Iterator<SystemConfigKey> keys = KEY_LIST.iterator();
		while (keys.hasNext()) {
			SystemConfigKey key = (SystemConfigKey) (keys.next());

			String name = key.getName();
			String text = null;

			text = StringUtils.trimWhitespace(msgSource.getMessage(name, null, null, Locale.getDefault()));

			LOG.debug(name + " = " + text);

			Object value = key.setup(text);
			VALUE_MAP.put(name, value);
		}

	}

	/**
	 * ユーザが指定した属性を意味するクラス.
	 * <p>
	 */
	private abstract static class SystemConfigKey {

		/**
		 * 属性.
		 */
		private final String keyName;

		/**
		 * ユーザ定義コンストラクター.
		 * 
		 * @param name
		 *            属性
		 */
		protected SystemConfigKey(final String name) {

			keyName = name;

			BaseSystemConfig.KEY_LIST.add(this);
		}

		/**
		 * 属性を取得します.
		 * 
		 * @return 属性を意味する String
		 */
		public final String getName() {

			return keyName;
		}

		/**
		 * このクラスのインスタンスが意味する属性の値をオブジェクト化に変換します.
		 * 
		 * @param string
		 *            クラスのインスタンスが意味する属性のプロパティ値
		 * @return オブジェクト化されたプロパティ値
		 */
		public abstract Object setup(final String string);

	}

	/**
	 * このクラスのインスタンスが意味する属性の値をBooleanとして保持するクラス.
	 */
	public static class BooleanKey extends SystemConfigKey {

		/**
		 * ユーザ定義コンストラクター.
		 * 
		 * @param name
		 *            属性
		 */
		public BooleanKey(final String name) {

			super(name);

		}

		/**
		 * 指定された属性の値を boolean として返します.
		 * 
		 * 指定された属性とは、このクラスのインスタンスが意味する属性のことです.
		 * 
		 * @return 指定された属性の値 boolean
		 */
		public final boolean getValue() {

			Object value = VALUE_MAP.get(getName());
			return ((Boolean) value).booleanValue();

		}

		/**
		 * 属性を設定し、属性の値を格納します.
		 * 
		 * 属性の値は、要求ごとにリセットされます. 指定された属性とは、このクラスのインスタンスが意味する属性のことです.
		 * 
		 * @param value
		 *            属性の値
		 */
		public final void setValue(final boolean value) {

			VALUE_MAP.put(getName(), Boolean.valueOf(value));

		}

		/**
		 * このクラスのインスタンスが意味する属性の値をオブジェクト化に変換します.
		 * 
		 * @param string
		 *            クラスのインスタンスが意味する属性のプロパティ値
		 * @return オブジェクト化されたプロパティ値 Object
		 */
		public final Object setup(final String string) {

			return Boolean.valueOf(string);

		}
	}

	/**
	 * このクラスのインスタンスが意味する属性の値をIntegerとして保持するクラス.
	 */
	public static class IntKey extends SystemConfigKey {

		/**
		 * ユーザ定義コンストラクター.
		 * 
		 * @param name
		 *            属性
		 */
		public IntKey(final String name) {

			super(name);

		}

		/**
		 * 指定された属性の値を int として返します.
		 * 
		 * 指定された属性とは、このクラスのインスタンスが意味する属性のことです.
		 * 
		 * @return 指定された属性の値 int
		 */
		public final int getValue() {

			Object value = VALUE_MAP.get(getName());
			return ((Integer) value).intValue();

		}

		/**
		 * 属性を設定し、属性の値を格納します.
		 * 
		 * 属性の値は、要求ごとにリセットされます. 指定された属性とは、このクラスのインスタンスが意味する属性のことです.
		 * 
		 * @param value
		 *            属性の値
		 */
		public final void setValue(final int value) {

			VALUE_MAP.put(getName(), Integer.valueOf(value));

		}

		/**
		 * このクラスのインスタンスが意味する属性の値をオブジェクト化に変換します.
		 * 
		 * @param string
		 *            クラスのインスタンスが意味する属性のプロパティ値
		 * @return オブジェクト化されたプロパティ値 Object
		 */
		public final Object setup(final String string) {
			if (string == null) {
				return null;
			}
			return Integer.valueOf(string);
		}
	}

	/**
	 * このクラスのインスタンスが意味する属性の値をLongとして保持するクラス.
	 */
	public static class LongKey extends SystemConfigKey {

		/**
		 * ユーザ定義コンストラクター.
		 * 
		 * @param name
		 *            属性
		 */
		public LongKey(final String name) {

			super(name);

		}

		/**
		 * 指定された属性の値を long として返します.
		 * 
		 * 指定された属性とは、このクラスのインスタンスが意味する属性のことです.
		 * 
		 * @return 指定された属性の値 long
		 */
		public final long getValue() {

			Object value = VALUE_MAP.get(getName());
			return ((Long) value).longValue();

		}

		/**
		 * 属性を設定し、属性の値を格納します.
		 * 
		 * 属性の値は、要求ごとにリセットされます. 指定された属性とは、このクラスのインスタンスが意味する属性のことです.
		 * 
		 * @param value
		 *            属性の値
		 */
		public final void setValue(final long value) {

			VALUE_MAP.put(getName(), Long.valueOf(value));

		}

		/**
		 * このクラスのインスタンスが意味する属性の値をオブジェクト化に変換します.
		 * 
		 * @param string
		 *            クラスのインスタンスが意味する属性のプロパティ値
		 * @return オブジェクト化されたプロパティ値 Object
		 */
		public final Object setup(final String string) {
			if (string == null) {
				return null;
			}
			return Long.valueOf(string);

		}
	}

	/**
	 * このクラスのインスタンスが意味する属性の値をDoubleとして保持するクラス.
	 */
	public static class DoubleKey extends SystemConfigKey {

		/**
		 * ユーザ定義コンストラクター.
		 * 
		 * @param name
		 *            属性
		 */
		public DoubleKey(final String name) {

			super(name);

		}

		/**
		 * 指定された属性の値を double として返します.
		 * 
		 * 指定された属性とは、このクラスのインスタンスが意味する属性のことです.
		 * 
		 * @return 指定された属性の値 double
		 */
		public final double getValue() {

			Object value = VALUE_MAP.get(getName());
			return ((Double) value).doubleValue();

		}

		/**
		 * 属性を設定し、属性の値を格納します.
		 * 
		 * 属性の値は、要求ごとにリセットされます. 指定された属性とは、このクラスのインスタンスが意味する属性のことです.
		 * 
		 * @param value
		 *            属性の値
		 */
		public final void setValue(final double value) {

			VALUE_MAP.put(getName(), new Double(value));

		}

		/**
		 * このクラスのインスタンスが意味する属性の値をオブジェクト化に変換します.
		 * 
		 * @param string
		 *            クラスのインスタンスが意味する属性のプロパティ値
		 * @return オブジェクト化されたプロパティ値 Object
		 */
		public final Object setup(final String string) {
			if (string == null) {
				return null;
			}
			return Double.valueOf(string);
		}
	}

	/**
	 * このクラスのインスタンスが意味する属性の値をStringとして保持するクラス.
	 */
	public static class StringKey extends SystemConfigKey {

		/**
		 * ユーザ定義コンストラクター.
		 * 
		 * @param name
		 *            属性
		 */
		public StringKey(final String name) {

			super(name);

		}

		/**
		 * 指定された属性の値を String として返します.
		 * 
		 * 指定された属性とは、このクラスのインスタンスが意味する属性のことです.
		 * 
		 * @return 指定された属性の値 String
		 */
		public final String getValue() {

			Object value = VALUE_MAP.get(getName());
			return (String) value;

		}

		/**
		 * 属性を設定し、属性の値を格納します.
		 * 
		 * 属性の値は、要求ごとにリセットされます. 指定された属性とは、このクラスのインスタンスが意味する属性のことです.
		 * 
		 * @param value
		 *            属性の値
		 */
		public final void setValue(final String value) {

			VALUE_MAP.put(getName(), value);

		}

		/**
		 * このクラスのインスタンスが意味する属性の値をオブジェクト化に変換します.
		 * 
		 * @param string
		 *            クラスのインスタンスが意味する属性のプロパティ値
		 * @return オブジェクト化されたプロパティ値 Object
		 */
		public final Object setup(final String string) {

			return string;

		}
	}

	/**
	 * msgSourceを設定します。
	 * 
	 * @param msgSource メッセージリソース
	 * 
	 */
	public void setMsgSource(MessageSource msgSource) {

		BaseSystemConfig.msgSource = msgSource;
	}
}
