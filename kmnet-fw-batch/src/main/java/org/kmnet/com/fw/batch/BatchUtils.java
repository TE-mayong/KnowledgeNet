/**
 * Project Name:sony-fw-batch
 * File Name:BatchUtils.java
 * Date:2015/05/01 09:07:15
 * Copyright (c) 2016, KnowledgeNet. All rights reserved.
 */
package org.kmnet.com.fw.batch;

import java.text.DecimalFormat;

import org.kmnet.com.fw.common.log.Logger;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * バッチ実装用ユーティリティクラス。<br>
 * 各種バッチ実装にて使用するユーティリティメソッドを定義する。
 */
public class BatchUtils {

	/** このクラスで利用するLoggerインスタンス. */
	private static final Logger LOG = Logger.getLogger(BatchUtils.class);

	private static PlatformTransactionManager transactionManager;

	private static final ThreadLocal<TransactionStatus> statThread = new ThreadLocal<TransactionStatus>();

	/**
	 * コンストラクタ
	 */
	protected BatchUtils() {

	}

	/**
	 * 汎用文字列結合メソッド。
	 * @param args 任意の値
	 * @return 引数を結合した文字列
	 */
	public static String cat(Object... args) {

		StringBuilder str = new StringBuilder();

		if (args == null) {
			return null;
		}

		for (Object o : args) {
			if (o != null) {
				str.append(o);
			}
		}

		return str.toString();
	}

	/**
	 * インフォログの開始メッセージを取得する。
	 * @param jobCd ジョブ業務コード
	 * @return String メッセージ
	 */
	public static String getInfoLogStartMsg(String jobCd) {

		return BatchUtils.cat("[", jobCd, "] ", "Job Start");
	}

	/**
	 * インフォログの終了メッセージを取得する。
	 * @param jobCd ジョブ業務コード
	 * @return String メッセージ
	 */
	public static String getInfoLogEndMsg(String jobCd) {

		return BatchUtils.cat("[", jobCd, "] ", "Job End");
	}

	/**
	 * トランザクションマネジャを設定する。
	 * 
	 * @param transactionManager　トランザクションマネジャ
	 *
	 */
	public void setTransactionManager(PlatformTransactionManager transactionManager) {

		BatchUtils.transactionManager = transactionManager;
	}

	/**
	 * トランザクション状態を保存する。
	 * @param stat トランザクション状態
	 * 
	 */
	private static void setTransactionStatus(TransactionStatus stat) {

		statThread.set(stat);
	}

	/**
	 * トランザクション状態を取得する。
	 * @return トランザクション状態
	 * 
	 */
	private static TransactionStatus getTransactionStatus() {

		return statThread.get();
	}

	/**
	 * デフォルトのTransactionDefinitionを取得する。
	 * @return デフォルトのTransactionDefinition
	 */
	private static TransactionDefinition getTransactionDefinition() {

		return new DefaultTransactionDefinition();
	}

	/**
	 * デフォルトのTransactionDefinitionでトランザクションを開始させる。
	 * 
	 */
	protected static void startTransaction() {

		startTransaction(getTransactionDefinition());
	}

	/**
	 * 指定のTransactionDefinitionトランザクションを開始させる。
	 * @param def TransactionDefinition
	 * 
	 */
	private static void startTransaction(TransactionDefinition def) {

		if (transactionManager != null) {
			TransactionStatus stat = transactionManager.getTransaction(def);
			LOG.info("Transaction >>>> start " + def.toString());
			setTransactionStatus(stat);
		}
	}

	/**
	 * トランザクションをコミットさせる コネクションのコミットを行う。
	 * 
	 */
	protected static void commitTransaction() {

		TransactionStatus stat = getTransactionStatus();
		if (transactionManager != null && stat != null && !stat.isCompleted()) {
			transactionManager.commit(stat);
			LOG.info("Transaction <<<< commit ");
		}

	}

	/**
	 * トランザクション開始までロールバックする。
	 * 
	 */
	protected static void rollbackTransaction() {

		TransactionStatus stat = getTransactionStatus();
		if (transactionManager != null && stat != null && !stat.isCompleted()) {
			transactionManager.rollback(stat);
			LOG.info("Transaction <<<< rollback");
		}

	}

	/**
	 * セーブポイントを設定する。
	 * @return Object セーブポイント
	 */
	public static Object setSavepoint() {

		TransactionStatus stat = getTransactionStatus();
		Object savepoint = stat.createSavepoint();

		return savepoint;
	}

	/**
	 * セーブポイントをリリースする。
	 * @param savepoint セーブポイント
	 */
	public static void releaseSavepoint(Object savepoint) {

		TransactionStatus stat = getTransactionStatus();
		stat.releaseSavepoint(savepoint);
	}

	/**
	 * セーブポイントまでロールバックさせる
	 * @param savepoint セーブポイント
	 */
	public static void rollbackSavepoint(Object savepoint) {

		TransactionStatus stat = getTransactionStatus();
		stat.rollbackToSavepoint(savepoint);
	}

	/**
	 * トランザクションをコミットさせ、トランザクションを再度開始させる
	 * 
	 */
	public static void commit() {

		commitTransaction();
		startTransaction();
	}

	/**
	 * トランザクションをロールバックさせ、トランザクションを再度開始させる。
	 */
	public static void rollback() {

		rollbackTransaction();
		startTransaction();
	}

	/**
	 * Java 仮想マシンのメモリ総容量、使用量、 使用を試みる最大メモリ容量の情報を返す。
	 * @return Java 仮想マシンのメモリ情報
	 */
	public static String getMemoryInfo() {

		DecimalFormat f1 = new DecimalFormat("#,###KB");
		DecimalFormat f2 = new DecimalFormat("##.#");

		Runtime rt = Runtime.getRuntime();
		long free = rt.freeMemory() / 1024;
		long total = rt.totalMemory() / 1024;
		long max = rt.maxMemory() / 1024;
		long used = total - free;
		double ratio = (used * 100 / (double) total);

		StringBuilder sb = new StringBuilder();

		sb.append("Java memory info : ");
		sb.append("used=");
		sb.append(f1.format(used));
		sb.append(" (");
		sb.append(f2.format(ratio));
		sb.append("%), ");
		sb.append("total=");
		sb.append(f1.format(total));
		sb.append(", ");
		sb.append("max=");
		sb.append(f1.format(max));

		return sb.toString();
	}

}
