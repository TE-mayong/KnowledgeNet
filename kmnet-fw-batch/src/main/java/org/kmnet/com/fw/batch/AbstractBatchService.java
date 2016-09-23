/**
 * Project Name:sony-fw-batch
 * File Name:AbstractBatchService.java
 * Date:2015/05/08 15:50:09
 * Copyright (c) 2016, KnowledgeNet. All rights reserved.
 */
package org.kmnet.com.fw.batch;

import javax.annotation.Resource;

import org.kmnet.com.fw.common.dao.JdbcCrudRepository;
import org.kmnet.com.fw.common.exception.SystemException;

import org.springframework.dao.DataAccessException;

/**
 * バッチ業務処理実装基底クラス。 <br>
 *
 * @author Li.Yunlong
 * @version 1.0 2015/05/08 新規作成
 */
public abstract class AbstractBatchService<I> {

	/**
	 * 共通DAO
	 */
	@Resource
	protected JdbcCrudRepository jdbcCrudRepository;

	public abstract void doExecute(I input) throws DataAccessException, SystemException;

}
