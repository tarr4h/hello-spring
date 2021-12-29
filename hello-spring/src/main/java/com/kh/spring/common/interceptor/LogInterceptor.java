package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogInterceptor extends HandlerInterceptorAdapter {
	
	/**
	 * 핸들러 호출 전
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		log.debug("==============================={}===========================", request.getRequestURI());
		
		return super.preHandle(request, response, handler); // 얘는 무조건 true를 반환함.
	}

	/**
	 * 핸들러 호출 후
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
				 
		
		log.debug("=============================================================");
		log.debug("modelAndView = {}", modelAndView);
		log.debug("=============================================================");
	}

	/**
	 * view단 처리 후
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		super.afterCompletion(request, response, handler, ex);
		
		log.debug("_____________________________________________________________");
		System.out.println();
	}
	
}
