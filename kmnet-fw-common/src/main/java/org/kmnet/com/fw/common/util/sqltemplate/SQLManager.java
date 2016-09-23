package org.kmnet.com.fw.common.util.sqltemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQLManager {

	private static final Logger logger = LoggerFactory.getLogger(SQLManager.class);

	// sqlを定義するxmlファイル数ごとにSQLTemplateのインスタンスを格納
	private List<SQLTemplate> sqlTmpls = new ArrayList<SQLTemplate>();

	// sqlを定義するxmlファイルpathを格納
	private List<String> sqlFilePaths;

	/**
	 * SQL文の設定ファイルを初期化
	 * 
	 * @throws JDOMException
	 * @throws IOException
	 */
	@PostConstruct
	private void createTemplate() throws JDOMException, IOException {

		if (sqlFilePaths == null || sqlFilePaths.size() == 0) {
			return;
		}

		for (String filePath : sqlFilePaths) {

			InputStream in = SQLManager.class.getClassLoader().getResourceAsStream(filePath);

			SQLTemplate sqlTemplate = new SQLTemplateJDOMImpl(in, false);
			sqlTmpls.add(sqlTemplate);
			if (logger.isDebugEnabled()) {
				logger.debug("###JdbcSqlTemplate-filePath= " + filePath + " init success!");
			}
		}
	}

	/**
	 * クライアントが利用できるメソッドを定義
	 * 
	 * @param sqlId どのSQLかを特定するID
	 * @param params バインド変数のパラメータ群
	 * @return SQLインスタンス
	 */
	public SQL getSqlById(String sqlId, Map<String, Object> params) {

		for (SQLTemplate sqlTempl : sqlTmpls) {

			try {
				SQL sql = sqlTempl.createSQL(sqlId, params);
				if (sql != null) {
					// SQL文出力
					if (logger.isInfoEnabled()) {

						logger.debug("SQLID=" + sqlId + "\n" + sql.toString());
					}

					return sql;
				}
			} catch (Exception e) {

			}

		}

		// 設定ファイルに見つからない場合の情報を出力する
		if (logger.isInfoEnabled()) {
			logger.debug("###JdbcSqlTemplate-sqlId= " + sqlId + ": can not be find!");
		}

		// 設定ファイルに見つからない場合は、SQL実行するときにエラーが発生するので、こちらは処理しない

		return null;
	}

	/**
	 * injection用method
	 * 
	 * @param sqlFilePaths
	 *            　sqlを定義するxmlファイルpath
	 */
	public void setSqlFilePaths(List<String> sqlFilePaths) {

		this.sqlFilePaths = sqlFilePaths;
	}

}
