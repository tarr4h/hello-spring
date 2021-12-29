package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String referer = request.getHeader("referer");
		log.debug(referer);
		
		// login 여부 check
		if(request.getSession().getAttribute("loginMember") == null) {
			String url = request.getRequestURL().toString();
			String queryString = request.getQueryString();
			if(queryString != null) url += "?" + queryString;
			log.debug(url);
			request.getSession().setAttribute("next", url);
			
			response.sendRedirect(request.getContextPath()+"/member/memberLogin.do");
			return false;
		}
		
		return super.preHandle(request, response, handler);
	}

}
