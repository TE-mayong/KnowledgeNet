package org.kmnet.com.fw.common.util.sqltemplate;

import java.io.Serializable;
import java.util.Map;

/**
 * SQL文生成テンプレートのインタフェース。
 * @version 1.1.1_02, 01 March, 2004
 * @since   1.0.0
 */
@SuppressWarnings("all")
public interface SQLTemplate extends Serializable {

	/**
	 * SQLインスタンスを生成する。
	 * @param id どのSQLかを特定するID
	 * @param params バインド変数のパラメータ群
	 * @return SQLインスタンス
	 * @since   1.0.0
	 */
	public SQL createSQL(String id, Map params);
}
