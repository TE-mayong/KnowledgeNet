/**
 * Project Name:kmnet-fw-common
 * File Name:JdbcCrudRepository.java
 * Date:2015/03/27 13:37:41
 * Copyright (C) 2016 KnowledgeNet.
 */
package org.kmnet.com.fw.common.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.kmnet.com.fw.common.util.mapper.CustomBeanPropertyRowMapper;
import org.kmnet.com.fw.common.util.sqltemplate.SQL;
import org.kmnet.com.fw.common.util.sqltemplate.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedCaseInsensitiveMap;

/**
 * RDBMS向けのCRUD 操作を実装するクラス。<br>
 * 
 * @author Li.yunlong
 * @version 1.0 2015/03/27 新規作成
 */
@Repository
public class JdbcCrudRepository implements CrudRepository {

	@Autowired
	private SQLManager sqlManger;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final long serialVersionUID = -5450462945507329073L;

	/**
	 * 1件検索系のメソッド。
	 * 
	 */
	@Override
	public <T> T selectOne(String sqlId, Map<String, Object> params, Class<T> clazz) {

		// 設定ファイルからSQLを取得
		SQL sql = sqlManger.getSqlById(sqlId, params);
		return jdbcTemplate.queryForObject(sql.getText(), sql.getVariableValuesArray(),
				CustomBeanPropertyRowMapper.newInstance(clazz));
	}

	/**
	 * 1件検索系のメソッド。
	 * 
	 */
	@Override
	public Map<String, Object> selectOne(String sqlId, Map<String, Object> params) {

		// 設定ファイルからSQLを取得
		SQL sql = sqlManger.getSqlById(sqlId, params);
		return jdbcTemplate.queryForMap(sql.getText(), sql.getVariableValuesArray());
	}

	/**
	 * 複数件検索系のメソッド。
	 * 
	 * @param sqlId SQLのID
	 * @param params SQLにバインドするパラメータMap
	 * @param clazz 結果List内のBeanクラス
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> selectList(String sqlId, Map<String, Object> params, Class<T> clazz) {

		// 設定ファイルからSQLを取得
		SQL sql = sqlManger.getSqlById(sqlId, params);
		
		if(String.class.isAssignableFrom(clazz)) {
			
			return (List<T>) jdbcTemplate.query(sql.getText(), sql.getVariableValuesArray(), new RowMapper<String>() {

                public String mapRow(ResultSet rs, int rowNum) throws SQLException {

                        return rs.getString(1);
                }
        });

		}


		// 検索を行う
		return jdbcTemplate.query(sql.getText(), sql.getVariableValuesArray(),
				CustomBeanPropertyRowMapper.newInstance(clazz));
	}

	/**
	 * 複数件検索系のメソッド。
	 * 
	 * @param sqlId SQLのID
	 * @param params SQLにバインドするパラメータMap
	 * 
	 */
	@Override
	public List<Map<String, Object>> selectList(String sqlId, Map<String, Object> params) {

		// 設定ファイルからSQLを取得
		SQL sql = sqlManger.getSqlById(sqlId, params);

		// 検索を行う
		return jdbcTemplate.queryForList(sql.getText(), sql.getVariableValuesArray());
	}

	/**
	 * 件数のカウント系のメソッド。
	 * 
	 */
	@Override
	public int count(String sqlId, Map<String, Object> params) {

		// 設定ファイルからSQLを取得
		SQL sql = sqlManger.getSqlById(sqlId, params);

		// 検索を行う
		return jdbcTemplate.queryForObject(sql.getText(), sql.getVariableValuesArray(), Integer.class);
	}

	/**
	 * 登録系のメソッド。
	 * 
	 */
	@Override
	public int insert(String sqlId, Map<String, Object> params) {

		// 設定ファイルからSQLを取得
		SQL sql = sqlManger.getSqlById(sqlId, params);

		// 新規を行う
		return jdbcTemplate.update(sql.getText(), sql.getVariableValuesArray());
	}

	/**
	 * 更新系のメソッド。
	 * 
	 */
	@Override
	public int update(String sqlId, Map<String, Object> params) {

		// 設定ファイルからSQLを取得
		SQL sql = sqlManger.getSqlById(sqlId, params);

		// 更新を行う
		return jdbcTemplate.update(sql.getText(), sql.getVariableValuesArray());
	}

	/**
	 * 削除系のメソッド。
	 * 
	 */
	@Override
	public int delete(String sqlId, Map<String, Object> params) {

		// 設定ファイルからSQLを取得
		SQL sql = sqlManger.getSqlById(sqlId, params);

		// 削除を行う
		return jdbcTemplate.update(sql.getText(), sql.getVariableValuesArray());
	}

	/**
	 * 大量検索系のメソッド。
	 * 
	 */
	@Override
	public void selectList(String sqlId, Map<String, Object> params, final MapRowCallBackHandler rch) {

		// 設定ファイルからSQLを取得
		SQL sql = sqlManger.getSqlById(sqlId, params);

		jdbcTemplate.query(sql.getText(), sql.getVariableValuesArray(), new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {

				rch.processMapRow(getMapRow(rs));
			}
		});
	}

	/**
	 * ResultSetからMapに変換するメソッド。
	 */
	private Map<String, Object> getMapRow(ResultSet resultSet) throws SQLException {

		ResultSetMetaData rsmd = resultSet.getMetaData();
		Map<String, Object> row = new LinkedCaseInsensitiveMap<Object>();
		for (int i = 0; i < rsmd.getColumnCount(); i++) {
			Object value = resultSet.getObject(i + 1);
			row.put(rsmd.getColumnName(i + 1), value);
		}
		return row;
	}
}
