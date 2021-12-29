package com.kh.spring.memo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
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
			StopWatch stopWatch = new StopWatch();
			stopWatch.start("insertMemo");
			log.debug("memo = {}", memo);
			
			int result = memoService.insertMemo(memo);
			
			String msg = result > 0 ? "memo 등록 성공" : "memo 등록 실패";
			log.debug("msg = {}", msg);
			
			redirectAttr.addFlashAttribute("msg", msg);
			stopWatch.stop();
			log.debug("stopWatch = {} ", stopWatch.shortSummary());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
		
		return "redirect:/memo/memo.do";
	}

	@PostMapping("/deleteMemo.do")
	public String deleteMemo(@RequestParam("memoNo") String memoNo) {
		log.debug("delMemoNo = {}", memoNo);
		
		int result = memoService.deleteMemo(memoNo);
		log.debug("delMemoResult = {}", result);

		return "redirect:/memo/memo.do";
	}
}
