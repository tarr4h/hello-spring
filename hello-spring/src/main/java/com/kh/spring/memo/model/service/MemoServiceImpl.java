package com.kh.spring.memo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.memo.model.dao.MemoDao;
import com.kh.spring.memo.model.vo.Memo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemoServiceImpl implements MemoService {

	@Autowired
	private MemoDao memoDao;

	@Override
	public int insertMemo(Memo memo) {
		return memoDao.insertMemo(memo);
	}

	@Override
	public List<Memo> getMemo() {
		log.debug("service 주업무");
		return memoDao.getMemo();
	}

	@Override
	public int deleteMemo(int memoNo) {
		return memoDao.deleteMemo(memoNo);
	}

	@Override
	public Memo selectOneMemo(int no) {
		return memoDao.selectOneMemo(no);
	}
	
	
}
