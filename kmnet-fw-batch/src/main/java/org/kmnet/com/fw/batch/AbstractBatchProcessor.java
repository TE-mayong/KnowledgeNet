/**
 * Project Name:sony-fw-batch
 * File Name:AbstractBatchProcessor.java
 * Date:2015/05/07 09:10:09
 * Copyright (c) 2016, KnowledgeNet. All rights reserved.
 */
package org.kmnet.com.fw.batch;

import org.kmnet.com.fw.common.exception.SystemException;
import org.kmnet.com.fw.common.log.Logger;

import org.springframework.dao.DataAccessException;

/**
 * Processorインタフェースの実装基底クラス。<br>
 *
 * @author Liu.Zhaokun
 * @version 1.0 2015/05/07 新規作成
 * @author Ma.Yong
 * @version 1.0 2015/09/11 更新
 */
public abstract class AbstractBatchProcessor implements BatchProcessor {

	/** このクラスで利用するLoggerインスタンス. */
	private static final Logger LOG = Logger.getLogger(AbstractBatchProcessor.class);

	/** JVM 終了コード 正常終了. */
	protected static final int RETURN_OK = 0;

	/** JVM 終了コード 異常終了. */
	protected static final int RETURN_ABEND = 1;

	/**
	 * バッチ実行メソッド。<br>
	 * 
	 * @param args 引数
	 * 
	 * @return 実行結果
	 */
	public int processBatch(final String[] args) {

		int status = RETURN_ABEND;

		try {
			// バッチ処理実行
			status = processAction(args);
		}
		// その他の例外が発生した場合
		catch (DataAccessException e) {
			// 例外をログに出力
			LOG.log(e);
		}
		// その他の例外が発生した場合
		catch (SystemException e) {
			// 例外をログに出力
			LOG.log(e);
		}
		// その他の例外が発生した場合
		catch (Exception e) {
			// 例外をログに出力
			LOG.log(e);
		}
		// 深刻なエラー
		catch (Throwable t) {
			// 例外をログに出力
			LOG.log(t);
		} finally {

		}
		return status;
	}

	/**
	 * バッチ処理テンプレートを定義するメソッド。<br>
	 * 
	 * @param args 引数
	 * 
	 * @return バッチ処理結果
	 * 
	 * @throws DataAccessException
	 *             データアクセス（RDBMS、OtherSystemへの）例外が発生した場合
	 * @throws SystemException
	 *             システム例外が発生した場合
	 */
	private int processAction(final String[] args) throws DataAccessException, SystemException {

		int status = RETURN_ABEND;
		boolean commited = false;

		try {

			// トランザクション開始
			BatchUtils.startTransaction();

			// 業務処理の実行とOutputの作成
			status = execute(args);

			// トランザクションコミット
			BatchUtils.commitTransaction();
			commited = true;

			// 正常終了
			return status;

		} finally {
			if (!commited) {
				// トランザクションロールバック
				BatchUtils.rollbackTransaction();
			}
		}
	}

	/**
	 * 業務処理メソッド。<br>
	 * 
	 * @param args 引数
	 * 
	 * @return 業務処理結果
	 * 
	 * @throws DataAccessException
	 *             データアクセス（RDBMS、OtherSystemへの）例外が発生した場合
	 * @throws SystemException
	 *             システム例外が発生した場合
	 */
	protected abstract int execute(final String[] args) throws DataAccessException, SystemException;
}
