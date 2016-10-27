package com.tipico.livescore.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ProcessorProfiler {

	private static final Logger log = LoggerFactory.getLogger(ProcessorProfiler.class);

	@Pointcut("execution(* com.tipico.livescore.service.impl.DataProcessorImpl.*(..))")
	public void dataProcessingMethods() { }

	@Around("dataProcessingMethods()")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis() * 1000000;
		Object output = pjp.proceed();
		long elapsedTime = (System.currentTimeMillis() * 1000000) - start;
		log.info(pjp.getSignature() + " execution time: " + elapsedTime + " nanoSeconds.");
		return output;
	}

}