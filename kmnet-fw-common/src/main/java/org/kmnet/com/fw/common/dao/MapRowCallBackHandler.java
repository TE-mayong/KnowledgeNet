/**
 * Project Name:kmnet-fw-common
 * File Name:MapRowCallBackHandler.java
 * Date:2015/04/10 13:37:41
 * Copyright (C) 2016 KnowledgeNet.
 */
package org.kmnet.com.fw.common.dao;

import java.sql.SQLException;
import java.util.Map;

public interface MapRowCallBackHandler {

	/**
     * 問い合わせ結果に含まれるデータ一行ごとに通知されます。
     *
     * @param row 行データ
	 * @throws SQLException SQL例外
	 */
	void processMapRow(Map<String, Object> row) throws SQLException;

}
