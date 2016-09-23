/**
 * Project Name:kmnet-fw-common
 * File Name:JdbcCallRepository.java
 * Date:2015/04/16 14:14:36
 * Copyright (C) 2016 KnowledgeNet.
 */
package org.kmnet.com.fw.common.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.OracleConnection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.AbstractSqlTypeValue;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * JdbcCallRepository <br>
 * このインタフェースは、シンプルなPL/SQL操作を提供する。<br>
 * 
 * @author Liu.jun
 * @version 1.0 2015/04/16 新規作成
 * @author Ma.Yong
 * @version 1.3 2015/09/07 throws SQLExceptionの削除
 * @author Liu.jun
 * @version 1.4 2015/09/07 schemaName、catalogNameパラメータを追加しました。
 */
@Repository
public class JdbcCallRepository implements CallRepository {

	@Resource
	private JdbcTemplate jdbcTemplate;

	//@Resource
	//private SimpleJdbcCall simpleJdbcCall;

	private static final long serialVersionUID = -8599738753271661000L;

	/**
	 * Procedureを実行する.
	 * <p>
	 * 
     * @param schemaName
     *            schema名
     * @param catalogName
     *            catalog名
	 * @param procedureName
	 *            procedure名
	 * @param inParamValue
	 *            SQLにバインドするinパラメータ
	 * @param inParamType
	 *            SQLにバインドするinタイプ(Array型のみがある場合設定)
	 * @param outParamType
	 *            SQLにバインドするoutタイプ
	 * @return result 戻り値
	 */
	@Override
    public Map<String, Object> callProcedure(final String schemaName, final String catalogName,
            final String procedureName, final Map<String, Object> inParamValue, final Map<String, Object> inParamType,
            final Map<String, Object> outParamType) {

	    Assert.notNull(schemaName, "The schemaName must not be null");
		Assert.notNull(procedureName, "The procedureName must not be null");
		Assert.notNull(inParamValue, "The inParamValue must not be null");
		Assert.notNull(outParamType, "The outParamType must not be null");
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
		try {
		    
            simpleJdbcCall.withSchemaName(schemaName);
            if (!StringUtils.isEmpty(catalogName)) {
                simpleJdbcCall.withCatalogName(catalogName);
            }
			simpleJdbcCall.withProcedureName(procedureName);

			Map<String, Object> inParamMap = getParam(inParamValue, inParamType,simpleJdbcCall);

			for (Map.Entry<String, Object> entry : outParamType.entrySet()) {
				String key = entry.getKey();
				Object object = entry.getValue();

				// String型の場合
				if (object instanceof String) {
					simpleJdbcCall.declareParameters(new SqlOutParameter(key, Types.ARRAY, (String) object));
				}
				// int型の場合
				else if (object instanceof Integer) {
					// String型の場合
					if ((Integer) object == Types.VARCHAR) {
						simpleJdbcCall.declareParameters(new SqlOutParameter(key, Types.VARCHAR));
					}
					// int型の場合
					else if ((Integer) object == Types.INTEGER) {
						simpleJdbcCall.declareParameters(new SqlOutParameter(key, Types.INTEGER));
					}
					// double型の場合
					else if ((Integer) object == Types.DOUBLE) {
						simpleJdbcCall.declareParameters(new SqlOutParameter(key, Types.DOUBLE));
					}
					// java.sql.Date型の場合
					else if ((Integer) object == Types.DATE) {
						simpleJdbcCall.declareParameters(new SqlOutParameter(key, Types.DATE));
					}
					// java.sql.Timestamp型の場合
					else if ((Integer) object == Types.TIMESTAMP) {
						simpleJdbcCall.declareParameters(new SqlOutParameter(key, Types.TIMESTAMP));
					}
					// Byte型の場合
					else if ((Integer) object == Types.CHAR) {
						simpleJdbcCall.declareParameters(new SqlOutParameter(key, Types.CHAR));
					}
				}
				// NULLの場合
				else if (object == null) {
					// 例外スロー(NULLセット無効)
					throw new RuntimeException("Type is Nulll.");
				}
				// その他の場合
				else {
					String className = object.getClass().getName();
					// 例外スロー(サポートされない型)
					throw new RuntimeException("Type Class is not supported." + className);
				}
			}

			SqlParameterSource in = new MapSqlParameterSource().addValues(inParamMap);
			Map<String, Object> out = simpleJdbcCall.execute(in);

			if (out == null) {
				// 例外スロー(サポートされない型)
				throw new RuntimeException("result is Nulll.");
			}

			Map<String, Object> result = new HashMap<String, Object>();

			for (Map.Entry<String, Object> entry : outParamType.entrySet()) {
				String key = entry.getKey();
				Object object = entry.getValue();

				// String型の場合
				if (object instanceof String) {
					// Array型の場合
					Array array = (Array) out.get(key);
					if (array != null) {
						try {
							result.put(key, array.getArray());
						} catch (SQLException e) {
							new RuntimeException("array.getArray() is error.");
						}
					} else {
						result.put(key, null);
					}
				} else {
					result.put(key, out.get(key));
				}
			}

			return result;

		} finally {
			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
		}
	}

	/**
	 * Functionを実行する.
	 * <p>
	 * 
     * @param schemaName
     *            schema名
     * @param catalogName
     *            catalog名
	 * @param functionName
	 *            function名
	 * @param inParamValue
	 *            SQLにバインドするinパラメータ
	 * @param inParamType
	 *            SQLにバインドするinタイプ(Array型のみがある場合設定)
	 * @param outParamType
	 *            SQLにバインドするoutパラメータ
	 * @return result 戻り値
	 */
	@Override
    public <T> T callFunction(final String schemaName, final String catalogName, final String functionName,
            final Map<String, Object> inParamValue, final Map<String, Object> inParamType, Class<T> outParamType) {

	    Assert.notNull(schemaName, "The schemaName must not be null");
		Assert.notNull(functionName, "The functionName must not be null");
		Assert.notNull(inParamValue, "The inParamValue must not be null");
		Assert.notNull(outParamType, "The outParamType must not be null");
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
		try {
			
            simpleJdbcCall.withSchemaName(schemaName);
            if (!StringUtils.isEmpty(catalogName)) {
                simpleJdbcCall.withCatalogName(catalogName);
            }
			simpleJdbcCall.withFunctionName(functionName);

			Map<String, Object> inParamMap = getParam(inParamValue, inParamType,simpleJdbcCall);

			SqlParameterSource in = new MapSqlParameterSource().addValues(inParamMap);

			return (T) simpleJdbcCall.executeFunction(outParamType, in);
		} finally {
			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
		}
	}

	/**
	 * パラメータを設定する.
	 * <p>
	 * 
	 * @param inParamValue
	 *            SQLにバインドするinパラメータ
	 * @param inParamType
	 *            SQLにバインドするinタイプ
	 * @return result 戻り値
	 */
	private Map<String, Object> getParam(final Map<String, Object> inParamValue, final Map<String, Object> inParamType,SimpleJdbcCall simpleJdbcCall) {

		// 戻り値
		Map<String, Object> result = new HashMap<String, Object>();

		result.putAll(inParamValue);
		
		for (Map.Entry<String, Object> entry : inParamValue.entrySet()) {
			String key = entry.getKey();
			Object object = entry.getValue();

			// String型の場合
			if (object instanceof String) {
				simpleJdbcCall.declareParameters(new SqlParameter(key, Types.VARCHAR));
			}
			// int型の場合
			else if (object instanceof Integer) {
				simpleJdbcCall.declareParameters(new SqlParameter(key, Types.INTEGER));
			}
			// double型の場合
			else if (object instanceof Double) {
				simpleJdbcCall.declareParameters(new SqlParameter(key, Types.DOUBLE));
			}
			// java.sql.Date型の場合
			else if (object instanceof java.sql.Date) {
				simpleJdbcCall.declareParameters(new SqlParameter(key, Types.DATE));
			}
			// java.sql.Timestamp型の場合
			else if (object instanceof java.sql.Timestamp) {
				simpleJdbcCall.declareParameters(new SqlParameter(key, Types.TIMESTAMP));
			}
			// Byte型の場合
			else if (object instanceof Byte) {
				simpleJdbcCall.declareParameters(new SqlParameter(key, Types.CHAR));
			}
			// Array型の場合
			else if (object instanceof int[] || object instanceof Integer[] || object instanceof String[]) {
				simpleJdbcCall.declareParameters(new SqlParameter(key, Types.ARRAY, (String) inParamType.get(key)));
				result.put(key, getSqlTypeValue(object));
			}
			// NULLの場合
			else if (object == null) {
				// 例外スロー(NULLセット無効)
				throw new RuntimeException("Object is Null.");
			}
			// その他の場合
			else {
				String className = object.getClass().getName();
				// 例外スロー(サポートされない型)
				throw new RuntimeException("Param Class is not supported." + className);
			}
		}

		return result;
	}

	/**
	 * ARRAYタイプを変換する.
	 * <p>
	 * 
	 * @param object
	 *            変換対象
	 * @return sqlTypeValue 戻り値
	 */
	private SqlTypeValue getSqlTypeValue(final Object object) {

		SqlTypeValue sqlTypeValue = new AbstractSqlTypeValue() {

			protected Object createTypeValue(Connection conn, int sqlType, String typeName) throws SQLException {

				OracleConnection oracleConnection = conn.unwrap(OracleConnection.class);
				Array item = oracleConnection.createOracleArray(typeName, object);

				return item;
			}
		};
		return sqlTypeValue;
	}
}
