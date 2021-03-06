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
		// ????????????
		Member member = memberService.selectOneMember(id);
		log.info("member = {}", member);
		
		String location = "/";
		if(member != null && bcryptPasswordEncoder.matches(password, member.getPassword())) {
			// ????????? ?????? ???
			model.addAttribute("loginMember", member);
			
			// next?????? location??? ??????
			log.info("next = {}", next);
			location = next;
			
			redirectAttr.addFlashAttribute("msg", "????????? ??????");
		} else {
			// ????????? ?????? ???
			redirectAttr.addFlashAttribute("msg", "????????? ?????? ??????????????? ???????????? ????????????.");
		}
		
		return "redirect:"+location;
	}
	
	@GetMapping("/memberLogout.do")
	public String memberLogout(SessionStatus sessionStatus, ModelMap model) {
		
		model.clear(); // model ?????? map??? ?????????
		
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
		
		// ???????????? ????????? ??????
		String rawPassword = member.getPassword();
		String encodedPassword = bcryptPasswordEncoder.encode(rawPassword);
		member.setPassword(encodedPassword);
		
		int result = memberService.insertMember(member);
		log.info("result = {}", result);
		
		// redirect ?????? session??? ????????? ????????? ??? ????????? ??????.
		redirectAttr.addFlashAttribute("msg", result > 0 ? "?????? ?????? ??????" : "???????????? ??????"); // flashAttribute??? ?????? ??????. -> session??? msg??? ??????
		return "redirect:/";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		PropertyEditor editor = new CustomDateEditor(sdf, true);
		binder.registerCustomEditor(Date.class, editor);
	}
	
}
