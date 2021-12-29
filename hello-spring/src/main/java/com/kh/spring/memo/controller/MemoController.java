package com.kh.spring.memo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		List<Memo> memoList = memoService.getMemo();
		log.info("memoList = {}", memoList);
		model.addAttribute("memoList", memoList);
	}
	
	@PostMapping("/insertMemo.do")
	public String insertMemo(Memo memo) {
		log.info("memo = {}", memo);
		
		int result = memoService.insertMemo(memo);
		log.info("result = {}", result);
		
		return "redirect:/memo/memo.do";
	}

	@PostMapping("/deleteMemo.do")
	public String deleteMemo(@RequestParam("memoNo") String memoNo) {
		log.info("delMemoNo = {}", memoNo);
		
		int result = memoService.deleteMemo(memoNo);
		log.info("delMemoResult = {}", result);

		return "redirect:/memo/memo.do";
	}
}
