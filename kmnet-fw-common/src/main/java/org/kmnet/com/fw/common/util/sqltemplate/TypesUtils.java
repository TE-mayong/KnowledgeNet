package org.kmnet.com.fw.common.util.sqltemplate;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * java.sql.Typesで定義されているint値と文字列名を相互変換するためのクラス。
 * </p>
 * <p>
 * このクラスはstaticブロックでinit()メソッドが呼ばれて初期化するようになっている。
 * </p>
 * <p>
 * 利用方法としては、staticメソッドのgetName(int)またはgetValue(String)をたんに呼ぶだけである。
 * 対応している値は次のとおりである。
 * </p>
 * <ul>
 * <li>ARRAY</li>
 * <li>BIGINT</li>
 * <li>BINARY</li>
 * <li>BIT</li>
 * <li>BLOB</li>
 * <li>CHAR</li>
 * <li>CLOB</li>
 * <li>DATE</li>
 * <li>DECIMAL</li>
 * <li>DISTINCT</li>
 * <li>DOUBLE</li>
 * <li>FLOAT</li>
 * <li>INTEGER</li>
 * <li>JAVA_OBJECT</li>
 * <li>LONGVARBINARY</li>
 * <li>LONGVARCHAR</li>
 * <li>NULL</li>
 * <li>NUMERIC</li>
 * <li>OTHER</li>
 * <li>REAL</li>
 * <li>REF</li>
 * <li>SMALLINT</li>
 * <li>STRUCT</li>
 * <li>TIME</li>
 * <li>TIMESTAMP</li>
 * <li>TINYINT</li>
 * <li>VARBINARY</li>
 * <li>VARCHAR</li>
 * </ul>
 * @version 1.0.0, 01 April, 2003
 * @since   1.0.0
 */
@SuppressWarnings("all")
public class TypesUtils {

	/**
	 * java.sql.Typesで定義されているint値をIntegerでラップしたものの配列
	 */
	protected Integer[] values = { new Integer(Types.ARRAY), new Integer(Types.BIGINT), new Integer(Types.BINARY),
			new Integer(Types.BIT), new Integer(Types.BLOB), new Integer(Types.CHAR), new Integer(Types.CLOB),
			new Integer(Types.DATE), new Integer(Types.DECIMAL), new Integer(Types.DISTINCT),
			new Integer(Types.DOUBLE), new Integer(Types.FLOAT), new Integer(Types.INTEGER),
			new Integer(Types.JAVA_OBJECT), new Integer(Types.LONGVARBINARY), new Integer(Types.LONGVARCHAR),
			new Integer(Types.NULL), new Integer(Types.NUMERIC), new Integer(Types.OTHER), new Integer(Types.REAL),
			new Integer(Types.REF), new Integer(Types.SMALLINT), new Integer(Types.STRUCT), new Integer(Types.TIME),
			new Integer(Types.TIMESTAMP), new Integer(Types.TINYINT), new Integer(Types.VARBINARY),
			new Integer(Types.VARCHAR) };

	/**
	 * java.sql.Typesの値に対応する文字列の配列
	 */
	protected String[] names = { "ARRAY", "BIGINT", "BINARY", "BIT", "BLOB", "CHAR", "CLOB", "DATE", "DECIMAL",
			"DISTINCT", "DOUBLE", "FLOAT", "INTEGER", "JAVA_OBJECT", "LONGVARBINARY", "LONGVARCHAR", "NULL", "NUMERIC",
			"OTHER", "REAL", "REF", "SMALLINT", "STRUCT", "TIME", "TIMESTAMP", "TINYINT", "VARBINARY", "VARCHAR" };

	/**
	 * 文字列値からInteger値へのマップ
	 */
	protected Map nameToValueMap = new HashMap();

	/**
	 * Integer値から文字列値へのマップ
	 */
	protected Map valueToNameMap = new HashMap();

	/**
	 * デフォルトコンストラクタ
	 * @since   1.0.0
	 */
	protected TypesUtils() {

		init();
	}

	/**
	 * 初期化を実行する。
	 * @since   1.0.0
	 */
	protected void init() {

		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			Integer value = values[i];
			nameToValueMap.put(name, value);
			valueToNameMap.put(value, name);
		}
	}

	/**
	 * java.sql.Typesのint値に対応する文字列を取得する
	 * （例：java.sql.Types.VARCHAR -&gt; "VARCHAR"）。
	 * 対応する文字列が見つからない場合はIllegalArgumentExceptionを投げる。
     * @param value java.sql.Typesのint値
	 * @return java.sql.Typesのint値に対応する文字列。
	 * @exception IllegalArgumentException 対応する文字列が見つからない場合
	 * @see java.sql.Types java.sql.Types
	 * @since   1.0.0
	 */
	protected String getNameFromValue(int value) {

		String name = (String) valueToNameMap.get(new Integer(value));
		if (name == null) {
			throw new IllegalArgumentException("Illegal value for TypesUtil.");
		}
		return name;
	}

	/**
	 * 文字列に対応するjava.sql.Typesのint値を取得する
	 * （例："VARCHAR" -&gt; java.sql.Types.VARCHAR）。
	 * 対応するint値が見つからない場合はIllegalArgumentExceptionを投げる。
     * @param name java.sql.Typesの値に対応する文字列
	 * @return 文字列に対応するjava.sql.Typesのint値。
	 * @exception IllegalArgumentException 対応するint値が見つからない場合
	 * @see java.sql.Types java.sql.Types
	 * @since   1.0.0
	 */
	protected int getValueFromName(String name) {

		Integer value = (Integer) nameToValueMap.get(name);
		if (value == null) {
			throw new IllegalArgumentException("Illegal name for TypesUtil.");
		}
		return value.intValue();
	}

	/**
	 * シングルトンインスタンス
	 */
	private static final TypesUtils INSTANCE = new TypesUtils();

	/**
	 * java.sql.Typesのint値に対応する文字列を取得する
	 * （例：java.sql.Types.VARCHAR -&gt; "VARCHAR"）。
	 * 対応する文字列が見つからない場合はIllegalArgumentExceptionを投げる。
     * @param value java.sql.Typesのint値
	 * @return java.sql.Typesのint値に対応する文字列。
	 * @exception IllegalArgumentException 対応する文字列が見つからない場合
	 * @see java.sql.Types java.sql.Types
	 * @since   1.0.0
	 */
	public static String getName(int value) {

		return INSTANCE.getNameFromValue(value);
	}

	/**
	 * 文字列に対応するjava.sql.Typesのint値を取得する
	 * （例："VARCHAR" -&gt; java.sql.Types.VARCHAR）。
	 * 対応するint値が見つからない場合はIllegalArgumentExceptionを投げる。
     * @param name java.sql.Typesの値に対応する文字列
	 * @return 文字列に対応するjava.sql.Typesのint値。
	 * @exception IllegalArgumentException 対応するint値が見つからない場合
	 * @see java.sql.Types java.sql.Types
	 * @since   1.0.0
	 */
	public static int getValue(String name) {

		return INSTANCE.getValueFromName(name);
	}
}
