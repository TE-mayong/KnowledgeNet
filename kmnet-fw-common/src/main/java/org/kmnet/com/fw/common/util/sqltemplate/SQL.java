package org.kmnet.com.fw.common.util.sqltemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SQL文とバインド変数値を保持するクラス
 * @version 1.0.0, 01 April, 2003
 * @since   1.0.0
 */
@SuppressWarnings("all")
public class SQL implements Serializable {

	/**
	 * SQL文のテキストを取得する。
	 * @return SQL文のテキスト
	 * @since   1.0.0
	 */
	public String getText() {

		return text;
	}

	/**
	 * SQL文のテキストを設定する。
	 * テキスト中の?はバインド変数のプレースホルダと解釈される。
	 * @param text SQL文のテキスト
	 * @since   1.0.0
	 */
	public void setText(String text) {

		this.text = text;
	}

	/**
	 * SQL文のテキスト。
	 * テキスト中の?はバインド変数のプレースホルダと解釈される。
	 * @since   1.0.0
	 */
	protected String text;

	/**
	 * バインド変数の配列を取得する。
	 * @return バインド変数の配列。
	 * @since   1.0.0
	 */
	public BindVariable[] getVariables() {

		return variables;
	}

	/**
	 * バインド変数の配列を設定する。
	 * @param variables バインド変数の配列。
	 * @since   1.0.0
	 */
	public void setVariables(BindVariable[] variables) {

		this.variables = variables;
	}

	/**
	 * バインド変数の配列。
	 */
	protected BindVariable[] variables;

	/**
	 * バインド変数群の値をまとめて設定する。
	 * メンバ変数bindVariablesの各変数の変数名をキーとして指定のマップを
	 * 検索し対応する値をそのバインド変数の値として上書き設定する。
	 * キーが見つからない場合はその値については無視される。
	 * @param params バインド変数群の値を束ねたマップ。
	 * @since   1.0.0
	 */
	public void setVariableValues(Map params) {

		for (int i = 0; i < variables.length; i++) {
			BindVariable var = variables[i];
			String name = var.getName();
			Object value = params.get(name);
			var.setValue(value);
		}
	}

	/**
	 * バインド変数群の値をまとめて取得する。
	 * メンバ変数bindVariablesの各変数の変数名をキーとし変数値を値
	 * とするマップを返す。
	 * @return バインド変数群の値を束ねたマップ。
	 * @since   1.0.0
	 */
	public Map getVariableValues() {

		Map params = new HashMap();
		for (int i = 0; i < variables.length; i++) {
			BindVariable var = variables[i];
			String name = var.getName();
			Object value = var.getValue();
			params.put(name, value);
		}
		return params;
	}

	/**
	 * バインド変数群の値をまとめて取得する。
	 * メンバ変数bindVariablesの各変数の変数名をキーとし変数値を値
	 * とするマップを返す。
	 * @return バインド変数群の値を束ねた配列。
	 * @since   1.0.0
	 */
	public Object[] getVariableValuesArray() {

		List params = new ArrayList();
		for (int i = 0; i < variables.length; i++) {
			BindVariable var = variables[i];
			Object value = var.getValue();
			params.add(value);
		}
		return params.toArray();
	}

	/**
	 * デバッグ用の文字列表現を返す。
	 * <p>
	 * 例
	 * </p>
	 * <pre>
	 * SQL[
	 * text=[
	 *     select count(*) from CUSTOMER
	 *     
	 *       where KANJI_FAMILY_NAME=? and KANJI_GIVEN_NAME=?
	 *     
	 *   ]
	 * 0:BindVariable[name=KANJI_FAMILY_NAME, value=山田(java.lang.String)]
	 * 1:BindVariable[name=KANJI_GIVEN_NAME, value=太郎(java.lang.String)]
	 * ]
	 * </pre>
	 * @return デバッグ用の文字列表現。
	 * @since   1.0.0
	 */
	public String toString() {

		String nl = System.getProperty("line.separator");
		StringBuffer buf = new StringBuffer();
		buf.append("SQL[").append(nl);
		buf.append("text=[").append(text).append("]").append(nl);
		if (variables != null) {
			for (int i = 0; i < variables.length; i++) {
				buf.append(i).append(":").append(variables[i]).append(nl);
			}
		}
		buf.append("]");
		return new String(buf);
	}

	/**
	 * 指定のオブジェクトと等値かどうかを返す。
	 * メンバ変数の内容が全て等しければtrue、それ以外はfalseを返す。
	 * @param obj 比較対象のオブジェクト
	 * @return 指定のオブジェクトと等値かどうか。
	 * @since   1.0.0
	 */
	public boolean equals(Object obj) {

		if (!(obj instanceof SQL)) {
			return false;
		}
		SQL sql = (SQL) obj;
		return equals(text, sql.getText()) && equals(variables, sql.getVariables());
	}

	/**
	 * 2つのインスタンスが等値かどうかを返す。
	 * 判定ロジックは次のようになる。
	 * <pre>
	 * (1) o1がnullの場合、o2もnullならtrue、非nullならfalseを返す。
	 * (2) o1がObject配列の場合
	 *   (2-1) o2がオブジェクト配列でないか、要素数が違う場合はfalseを返す。
	 *   (2-2) o1とo2の各要素が等しければ(equals(Object,Object)がtrueなら)trueを返す。
	 * (4) o1がnullでもObject配列でもない場合、o1.equals(o2)の結果を返します。
	 * </pre>
	 * @param o1 比較対象のオブジェクトその1
	 * @param o2 比較対象のオブジェクトその2
	 * @return 2つのインスタンスが等値かどうか。
	 * @since   1.0.0
	 */
	protected boolean equals(Object o1, Object o2) {

		if (o1 == null) {
			return o2 == null;
		} else if (o1 instanceof Object[]) {
			if (!(o2 instanceof Object[])) {
				return false;
			}
			Object[] array1 = (Object[]) o1;
			Object[] array2 = (Object[]) o2;
			if (array1.length != array2.length) {
				return false;
			}
			for (int i = 0; i < array1.length; i++) {
				if (!equals(array1[i], array2[i])) {
					return false;
				}
			}
			return true;
		} else {
			return o1.equals(o2);
		}
	}
}
