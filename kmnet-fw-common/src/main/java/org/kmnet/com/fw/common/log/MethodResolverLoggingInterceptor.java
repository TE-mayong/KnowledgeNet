package org.kmnet.com.fw.common.log;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jboss.logging.MDC;
import org.kmnet.com.fw.common.exception.ResultMessagesNotificationException;
import org.kmnet.com.fw.common.exception.SystemException;
import org.springframework.beans.factory.InitializingBean;

/**
 * <p>
 * メソッド開始終了ログ出力
 * 
 * </p>
 */
public class MethodResolverLoggingInterceptor implements MethodInterceptor, InitializingBean {

	/**
	 * Starting point of interception in thread.
	 */
	private final ThreadLocal<MethodInvocation> startingPoint = new ThreadLocal<MethodInvocation>();

	/**
	 * Ending point of interception in thread.
	 */
	private final ThreadLocal<MethodInvocation> endingPoint = new ThreadLocal<MethodInvocation>();

	/**
	 * Constructor
	 */
	public MethodResolverLoggingInterceptor() {

	}

	/**
	 * メソッドを実行する場合、メソッド開始と終了ログを出力する
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

		// クラス名の取得
		String className = invocation.getThis().getClass().getSimpleName();
		// メソッド名の取得
		String methodName = invocation.getMethod().getName();
		// fqcn取得
		String fqcn = invocation.getThis().getClass().getName();

		Logger logger = Logger.getLogger(fqcn);

		// set starting point.
		if (startingPoint.get() == null) {
			startingPoint.set(invocation);
		}

		// 開始ログ出力
		logger.log("I.IFW.00.1001", new Object[] { className, methodName });

		Object obj = null;
		try {
			obj = invocation.proceed();
		} catch (ResultMessagesNotificationException e) {
			// set ending point.
			if (endingPoint.get() == null) {
				endingPoint.set(invocation);
			}
			if (isEndingPoint(invocation)) {
				MDC.put("CLASS", fqcn);
				logger.log(e);
				MDC.remove("CLASS");
			}
			// 終了ログ出力
			logger.log("I.IFW.00.1002", new Object[] { className, methodName });

			throw e;
		} catch (SystemException e) {
			// set ending point.
			if (endingPoint.get() == null) {
				endingPoint.set(invocation);
			}
			if (isEndingPoint(invocation)) {
				MDC.put("CLASS", fqcn);
				logger.log(e);
				MDC.remove("CLASS");
			}
			throw e;
		} finally {
			if (isStartingPoint(invocation)) {
				startingPoint.remove();
				endingPoint.remove();
			}
		}

		// 終了ログ出力
		logger.log("I.IFW.00.1002", new Object[] { className, methodName });
		return obj;
	}

	/**
	 * Is the starting point of interception in thread ?
	 * @param invocation invocation object of intercepted target's method.
	 * @return if starting point of interception in thread, return true.
	 */
	protected boolean isStartingPoint(MethodInvocation invocation) {

		return startingPoint.get() == invocation;
	}

	/**
	 * Is the endting point of interception in thread ?
	 * @param invocation invocation object of intercepted target's method.
	 * @return if ending point of interception in thread, return true.
	 */
	protected boolean isEndingPoint(MethodInvocation invocation) {

		return endingPoint.get() == invocation;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}
}