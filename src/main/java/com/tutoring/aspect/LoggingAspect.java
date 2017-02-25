package com.tutoring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tutoring.exception.AppException;

@Aspect
@Component
public class LoggingAspect {

	private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	/**
	 * Log the exception occurred in any controller.
	 * @param joinPoint - actual execution of method 
	 * @param exception - caught exception
	 */
	@AfterThrowing(pointcut = "within(com.tutoring.controller.*)", throwing= "exception")
	public void exceptionAdvice(JoinPoint joinPoint, AppException exception) {
		logger.debug(joinPoint.toString(), exception);
	}
	
}
