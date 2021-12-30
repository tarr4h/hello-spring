package com.kh.spring.memo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.memo.model.service.MemoService;
import com.kh.spring.memo.model.vo.Memo;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/memo")
@Slf4j
public class MemoController {
	
	@Autowired
	private MemoService memoService;
	
	
	
	@GetMapping("/memo.do")
	public void memo(Model model) {
		log.debug("controller 주업무");
		List<Memo> memoList = memoService.getMemo();

		model.addAttribute("memoList", memoList);
	}
	
	@PostMapping("/insertMemo.do")
	public String insertMemo(Memo memo, RedirectAttributes redirectAttr) {
		
		try {
			log.debug("memo = {}", memo);
			
			int result = memoService.insertMemo(memo);
			
			String msg = result > 0 ? "memo 등록 성공" : "memo 등록 실패";
			log.debug("msg = {}", msg);
			
			redirectAttr.addFlashAttribute("msg", msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
		
		return "redirect:/memo/memo.do";
	}

	@PostMapping("/deleteMemo.do")
	public String deleteMemo(@RequestParam("no") int no, @RequestParam("password") String password, RedirectAttributes redirectAttr) {
		log.debug("delMemoNo = {}", no);
		
		int result = memoService.deleteMemo(no);
		log.debug("delMemoResult = {}", result);
		
		String msg = result > 0 ? "메모 삭제 되었습니다." : "메모 삭제 실패";
		redirectAttr.addFlashAttribute("msg", msg);

		return "redirect:/memo/memo.do";
	}
}
