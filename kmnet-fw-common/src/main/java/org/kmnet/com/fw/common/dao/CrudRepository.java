/**
 * Project Name:kmnet-fw-common
 * File Name:CrudRepository.java
 * Date:2015/03/27 13:13:36
 * Copyright (C) 2016 KnowledgeNet.
 */
package org.kmnet.com.fw.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * CrudRepository.<br>
 * このインタフェースは、シンプルなCRUD 操作を提供する。<br>
 * 
 * @author Li.yunlong
 * @version 1.0 2015/03/27 新規作成
 */
public interface CrudRepository extends Serializable {

	<T> T selectOne(String sqlId, Map<String, Object> params, Class<T> clazz);

	Map<String, Object> selectOne(String sqlId, Map<String, Object> params);

	<T> List<T> selectList(String sqlId, Map<String, Object> params, Class<T> clazz);

	List<Map<String, Object>> selectList(String sqlId, Map<String, Object> params);

	void selectList(String sqlId, Map<String, Object> params, final MapRowCallBackHandler rch);

	int count(String sqlId, Map<String, Object> params);

	int insert(String sqlId, Map<String, Object> params);

	int update(String sqlId, Map<String, Object> params);

	int delete(String sqlId, Map<String, Object> params);

}
