package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.memo.model.service.MemoService;
import com.kh.spring.memo.model.vo.Memo;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class MemoAspect {

	@Autowired
	private MemoService memoService;
	
	@Pointcut("execution(* com.kh.spring.memo.controller.MemoController.deleteMemo(..))")
	public void pointcut() {};
	
	@Around("pointcut()")
	public Object passwordChecker(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		log.debug("joinPointArgs = {}", args);
		
		int no = (int) args[0];
		String password = (String) args[1];
		RedirectAttributes redirectAttr = (RedirectAttributes) args[2];
		
		Memo memo = memoService.selectOneMemo(no);
		log.debug("memo = {}", memo);
		if(!password.equals(memo.getPassword())) {
			redirectAttr.addFlashAttribute("msg", "비밀번호가 일치하지 않습니다.");
			return "redirect:/memo/memo.do";
		}
		
		return joinPoint.proceed();
	}
}
