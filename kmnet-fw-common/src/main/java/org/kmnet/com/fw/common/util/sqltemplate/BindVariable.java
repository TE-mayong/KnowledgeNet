package org.kmnet.com.fw.common.util.sqltemplate;

import java.io.Serializable;

/**
 * SQLまたはストアドプロシージャのバインド変数。
 * @version 1.0.0, 01 April, 2003
 * @since   1.0.0
 */
@SuppressWarnings("all")
public class BindVariable implements Serializable {

	/**
	 * デフォルトコンストラクタ
	 * @since   1.0.0
	 */
	public BindVariable() {

	}

	/**
	 * コンストラクタ
	 * @param name 変数名
	 * @param value 変数値
	 * @since   1.0.0
	 */
	public BindVariable(String name, Object value) {

		setName(name);
		setValue(value);
	}

	/**
	 * 変数値を取得する。
	 * @return 変数値（SQLのNULL値の場合はnullになる）
	 * @since   1.0.0
	 */
	public Object getValue() {

		return value;
	}

	/**
	 * 変数値を設定する。
	 * @param value 変数値（nullはSQLのNULL値として扱う）
	 * @since   1.0.0
	 */
	public void setValue(Object value) {

		this.value = value;
	}

	/**
	 * 変数値（nullはSQLのNULL値として扱う）
	 * @since   1.0.0
	 */
	protected Object value;

	/**
	 * 変数名を取得する。
	 * @return 変数名
	 * @since   1.0.0
	 */
	public String getName() {

		return name;
	}

	/**
	 * 変数名を設定する。
	 * JDBCのPreparedStatementやCallableStatementの変数としては、変数名
	 * は不要だが、SQLTemplateクラスでバインド変数集合をマップ形式で
	 * 指定したときにマップのキーとこの変数名を対応付けるようになっている。
	 * @param name 変数名
	 * @since   1.0.0
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * 変数名
	 */
	protected String name;

	/**
	 * 変数の入出力モードで入力のみの値(=1)
	 */
	public static final int IN = 1;

	/**
	 * 変数の入出力モードで入出力の値(=2)
	 */
	public static final int OUT = 2;

	/**
	 * 変数の入出力モードで出力の値(=3)
	 */
	public static final int IN_OUT = 3;

	/**
	 * 変数の入出力モードの値を取得する。
	 * @return 変数の入出力モード
	 * @see #IN
	 * @see #IN_OUT
	 * @see #OUT
	 * @since   1.0.0
	 */
	public int getMode() {

		return mode;
	}

	/**
	 * 変数の入出力モードの値を設定する。
	 * @param mode 変数の入出力モード
	 * @see #IN
	 * @see #IN_OUT
	 * @see #OUT
	 * @since   1.0.0
	 */
	public void setMode(int mode) {

		this.mode = mode;
	}

	/**
	 * 変数の入出力モード
	 * （デフォルト値はINである）。
	 * ストアドプロシージャ用のバインド変数でのみ使用される。
	 * @see #IN
	 * @see #IN_OUT
	 * @see #OUT
	 * @since   1.0.0
	 */
	protected int mode = IN;

	/**
	 * JDBCのデータ型を取得する。
	 * @return JDBCのデータ型
	 * @see java.sql.Types java.sql.Types
	 * @since   1.0.0
	 */
	public int getJdbcType() {

		return jdbcType;
	}

	/**
	 * JDBCのデータ型を設定する。
	 * @param jdbcType JDBCのデータ型
	 * @see java.sql.Types java.sql.Types
	 * @since   1.0.0
	 */
	public void setJdbcType(int jdbcType) {

		this.jdbcType = jdbcType;
		this.jdbcTypeUnset = false;
	}

	/**
	 * JDBCのデータ型を未設定に戻す。
	 * @since   1.0.0
	 */
	public void unsetJdbcType() {

		this.jdbcTypeUnset = true;
	}

	/**
	 * JDBCのデータ型
	 * （デフォルト値は0である）。
	 * @see java.sql.Types java.sql.Types
	 * @since   1.0.0
	 */
	protected int jdbcType = 0;

	/**
	 * JDBCのデータ型が未設定かどうかを取得する。
	 * @return JDBCのデータ型が未設定かどうか。
	 * @since   1.0.0
	 */
	public boolean isJdbcTypeUnset() {

		return jdbcTypeUnset;
	}

	/**
	 * JDBCのデータ型が未設定かどうか
	 * @since   1.0.0
	 */
	protected boolean jdbcTypeUnset = true;

	/**
	 * 数値変数のスケールを取得する。
	 * @return 数値変数のスケール
	 * @since   1.0.0
	 */
	public int getScale() {

		return scale;
	}

	/**
	 * 数値変数のスケールを設定する。
	 * @param scale 数値変数のスケール
	 * @since   1.0.0
	 */
	public void setScale(int scale) {

		this.scale = scale;
	}

	/**
	 * 数値変数のスケールを設定する
	 * （デフォルト値は-1である）。
	 * ストアドプロシージャ用でモードが入出力か出力のバインド変数でのみ使用される。
	 * @since   1.0.0
	 */
	protected int scale = -1;

	/**
	 * ユーザ定義型の名前を取得する
	 * （デフォルト値は-1である）。
	 * @return ユーザ定義型の名前
	 * @since   1.0.0
	 */
	public String getUserTypeName() {

		return userTypeName;
	}

	/**
	 * ユーザ定義型の名前を設定する。
	 * @param userTypeName ユーザ定義型の名前
	 * @since   1.0.0
	 */
	public void setUserTypeName(String userTypeName) {

		this.userTypeName = userTypeName;
	}

	/**
	 * ユーザ定義型の名前を取得する
	 * （デフォルト値は-1である）。
	 * ストアドプロシージャ用でモードが入出力か出力で型がREFのバインド変数でのみ使用される。
	 * @since   1.0.0
	 */
	protected String userTypeName;

	/**
	 * 指定のオブジェクトと等値かどうかを返す。
	 * メンバ変数の内容が全て等しければtrue、それ以外はfalseを返す。
	 * @param obj 比較対象のオブジェクト
	 * @return 指定のオブジェクトと等値かどうか。
	 * @since   1.0.0
	 */
	public boolean equals(Object obj) {

		if (!(obj instanceof BindVariable)) {
			return false;
		}
		BindVariable var = (BindVariable) obj;
		return equals(value, var.getValue()) && equals(name, var.getName()) && mode == var.getMode()
				&& jdbcType == var.getJdbcType() && scale == var.getScale()
				&& equals(userTypeName, var.getUserTypeName());
	}

	/**
	 * 2つのインスタンスが等値かどうかを返す。
	 * 共にnullであるかo1.equals(o2)がtrueならtrue、それ以外はfalseを返す。
	 * @param o1 比較対象のオブジェクトその1
	 * @param o2 比較対象のオブジェクトその2
	 * @return 2つのインスタンスが等値かどうか。
	 * @since   1.0.0
	 */
	protected boolean equals(Object o1, Object o2) {

		return o1 == null ? o2 == null : o1.equals(o2);
	}

	/**
	 * デバッグ用の文字列表現を返す。
	 * mode, jdbcType, scale, userTypeNameについては
	 * デフォルト値の場合は省略され、文字列に含めない。
	 * @return デバッグ用の文字列表現。
	 * @since   1.0.0
	 */
	public String toString() {

		StringBuffer buf = new StringBuffer();
		buf.append("BindVariable[");
		buf.append("name=").append(name);
		buf.append(", value=").append(value);
		if (value != null) {
			buf.append("(").append(value.getClass().getName()).append(")");
		}
		if (mode != IN) {
			buf.append(", mode=");
			if (mode == IN_OUT) {
				buf.append("IN_OUT");
			} else {
				buf.append("OUT");
			}
		}
		if (!isJdbcTypeUnset()) {
			buf.append(", jdbcType=");
			buf.append(TypesUtils.getName(jdbcType));
		}
		if (scale != -1) {
			buf.append(", scale=");
			buf.append(scale);
		}
		if (userTypeName != null) {
			buf.append(", userTypeName=");
			buf.append(userTypeName);
		}
		buf.append("]");
		return new String(buf);
	}
}
