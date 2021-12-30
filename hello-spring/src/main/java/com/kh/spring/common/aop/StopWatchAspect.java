package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class StopWatchAspect {

//	@Pointcut("execution(* com.kh.spring.memo.controller.MemoController.insertMemo(..))")
//	public void pointcut() {};
	
	@Around("execution(* com.kh.spring.memo.controller.MemoController.insertMemo(..))")
	public Object stopWatchAdvice(ProceedingJoinPoint joinPoint) throws Throwable{
		log.debug("stopwatch : {}", "시작");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("memo insert time");
		
		Object retObj = joinPoint.proceed();
		
		stopWatch.stop();
		log.debug("stopWatch getTimeSecond = {}", stopWatch.getTotalTimeSeconds());
		log.debug("stopWatch shortSummary = {}", stopWatch.shortSummary());
		return retObj;
	}
}
