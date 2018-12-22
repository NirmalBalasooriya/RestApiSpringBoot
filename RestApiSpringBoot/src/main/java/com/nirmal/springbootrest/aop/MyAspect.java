package com.nirmal.springbootrest.aop;

import org.slf4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class MyAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Before execution on
	 * 
	 * @param joinPoint
	 */
	@Before("execution(* com.nirmal.springbootrest.service.*.*(..))")
	public void before(JoinPoint joinPoint) {
		logger.info("MyAspect Before : execution for {}", joinPoint);
	}

	@AfterReturning(value = "execution(* com.nirmal.springbootrest.service.*.saveOrUpdateBook(..))", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		logger.info("MyAspect AfterReturning : returned with value {}", joinPoint, result);
	}

	@After(value = "execution(* com.nirmal.springbootrest.service.*.deleteBook(..))")
	public void after(JoinPoint joinPoint) {
		logger.info("MyAspect After : execution for {}", joinPoint);
	}
	
	@Around("@annotation(com.nirmal.springbootrest.aop.TimeTrack)")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("MyAspect Around : execution for {}", joinPoint, timeTaken);
    }
}
