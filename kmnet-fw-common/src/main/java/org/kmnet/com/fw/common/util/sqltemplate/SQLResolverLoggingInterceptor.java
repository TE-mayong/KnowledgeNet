package org.kmnet.com.fw.common.util.sqltemplate;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * SQL文のログ出力
 */
public class SQLResolverLoggingInterceptor implements MethodInterceptor, InitializingBean {

	/** このクラスで利用するLoggerインスタンス. */
	private static Logger logger = LoggerFactory.getLogger(SQLResolverLoggingInterceptor.class);

	/**
	 * Constructor
	 */
	public SQLResolverLoggingInterceptor() {

	}
	

	/**
	 * クラス「SQLManager.java」の「getSqlById()」メソッドを実行する場合、SQL文のログ出力
	 * 
	 * @param invocation
	 *            {@link MethodInvocation}
	 * @return Object returned by target method of interceptor
	 * @throws Throwable
	 *             If error occurred in target method of interceptor
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		String sqlID = (String) invocation.getArguments()[0];
		SQL sql = (SQL) invocation.proceed();
		LogEncryptionParameter LogEncryption = new LogEncryptionParameter();
		String SqlText = null;
		
		if (sql == null) {
			if (logger.isInfoEnabled()) {
				logger.info("###JdbcSqlTemplate-sqlId= {}{}", new Object[] { sqlID, ": can not be find!" });
			}
			return sql;
		}
		if (logger.isInfoEnabled()) {
			SqlText = sql.toString();
			SqlText = LogEncryption.logEncryptChange(SqlText);
			logger.info("SQLID={} {}", new Object[] { sqlID, SqlText });
		}
		return sql;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}
}