/**
 * Project Name:sony-fw-batch
 * File Name:BatchProcessor.java
 * Date:2015/05/07 09:05:10
 * Copyright (c) 2015, Sony Global Solutions Inc. All rights reserved.
 */
package org.kmnet.com.fw.batch;

/**
 * Processorインタフェース。<br>
 * 任意にトランザプロセスを管理したい場合のProcessorインタフェースを実装すること。<br>
 *
 * @author Liu.Zhaokun
 * @version 1.0 2015/05/07 新規作成
 */
public interface BatchProcessor {

	/**
	 * バッチ処理実行メソッド。
	 * @param args　引数
	 * @return 終了フラグ
	 */
	int processBatch(final String[] args);
}
