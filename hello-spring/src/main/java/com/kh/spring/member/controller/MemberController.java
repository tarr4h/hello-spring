package com.kh.spring.member.controller;

import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/member")
@SessionAttributes({"loginMember", "next"})
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	/* @RequestMapping(value="/memberLogin.do", method=RequestMethod.GET) */
	@GetMapping("/memberLogin.do")
	public String memberLogin(@RequestHeader String referer, Model model, @SessionAttribute(required=false) String next) {
		log.info("referer = {}", referer);
		
		if(next == null)
			model.addAttribute("next", referer);
		
		return "member/memberLogin";
	}

	@PostMapping("/memberLogin.do")
	public String memberLogin(@RequestParam String id, @RequestParam String password, RedirectAttributes redirectAttr, Model model, @SessionAttribute(required=false) String next) {
		// 인증과정
		Member member = memberService.selectOneMember(id);
		log.info("member = {}", member);
		
		String location = "/";
		if(member != null && bcryptPasswordEncoder.matches(password, member.getPassword())) {
			// 로그인 성공 시
			model.addAttribute("loginMember", member);
			
			// next값을 location을 등록
			log.info("next = {}", next);
			location = next;
			
			redirectAttr.addFlashAttribute("msg", "로그인 성공");
		} else {
			// 로그인 실패 시
			redirectAttr.addFlashAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		
		return "redirect:"+location;
	}
	
	@GetMapping("/memberLogout.do")
	public String memberLogout(SessionStatus sessionStatus, ModelMap model) {
		
		model.clear(); // model 내부 map이 비워짐
		
		if(!sessionStatus.isComplete())
			sessionStatus.setComplete();
		return "redirect:/";
	}
	
	
	
	@GetMapping("/memberEnroll.do")
	public String memberEnroll() {
		return "member/memberEnroll";
	}

	@PostMapping("/memberEnroll.do")
	public String memberEnroll(Member member, RedirectAttributes redirectAttr) {
		log.info("member = {}", member);
		
		// 비밀번호 암호화 처리
		String rawPassword = member.getPassword();
		String encodedPassword = bcryptPasswordEncoder.encode(rawPassword);
		member.setPassword(encodedPassword);
		
		int result = memberService.insertMember(member);
		log.info("result = {}", result);
		
		// redirect 후에 session의 속성을 참조할 수 있도록 한다.
		redirectAttr.addFlashAttribute("msg", result > 0 ? "회원 가입 성공" : "회원가입 실패"); // flashAttribute를 써야 한다. -> session의 msg로 등록
		return "redirect:/";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		PropertyEditor editor = new CustomDateEditor(sdf, true);
		binder.registerCustomEditor(Date.class, editor);
	}
	
}
