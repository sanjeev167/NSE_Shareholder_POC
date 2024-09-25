/**
 * 
 */
package com.nse.common.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author sanjeevkumar 
 * 19-Mar-2024 
 * 8:09:49 pm 
 * Objective :
 * 
 */
@Aspect
@Component
public class GlobalLogger {

	private static final Logger logger = LoggerFactory.getLogger(GlobalLogger.class);

	/**
	 * Pointcut that matches all repositories, services and Web REST endpoints.
	 */
	@Pointcut("within(@org.springframework.stereotype.Repository *)"
			+ " || within(@org.springframework.stereotype.Service *)"
			+ " || within(@org.springframework.web.bind.annotation.RestController *)")
	public void springBeanPointcut() {
		// Method is empty as this is just a Pointcut, the implementations are in the advice.
	}

	/**
	 * Pointcut that matches all Spring beans in the application's different packages.
	 */
	@Pointcut("within(com.nse.sec..*)"
			+ " || within(com.nse.pub..*)"
			+ " || within(com.nse.pvt..*)")
	public void applicationPackagePointcut() {
		// Method is empty as this is just a Point cut, the implementations are in the advices.
	}

	/**
	 * Advice that logs methods throwing exceptions.
	 *
	 * @param joinPoint join point for advice
	 * @param exception
	 */
	@AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
		logger.error("NSE => Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
	}

	/**
	 * Advice that logs when a method is entered and exited.
	 *
	 * @param joinPoint join point for advice
	 * @return result
	 * @throws Throwable throws IllegalArgumentException
	 */
	@Around("applicationPackagePointcut() && springBeanPointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		final StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		//[1] Corresponding to entering into a method
		if (logger.isInfoEnabled()) {
			logger.info("NSE => Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
		}
		//[2] Corresponding to existing from a method
		try {
			Object result = joinPoint.proceed();
			if (logger.isInfoEnabled()) {
				logger.info("NSE => Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
						joinPoint.getSignature().getName(), result);
			}
		// [3] Corresponding to calculating Method-Execution-Time
			MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
			// calculate method execution time
			stopWatch.stop();
			// Log method execution time
			logger.info("NSE => Execution time of "
					+ methodSignature.getDeclaringType().getSimpleName() // Class Name
					+ "." + methodSignature.getName() + " " // Method Name
					+ ":: " + stopWatch.getTotalTimeMillis() + " ms");

			return result;
			
		} catch (IllegalArgumentException e) {
			logger.error("NSE => Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
					joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
			throw e;
		}
	}
}// End of GlobalLogger
