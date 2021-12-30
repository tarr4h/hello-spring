package com.kh.spring.memo.model.dao;

import java.util.List;

import com.kh.spring.memo.model.vo.Memo;

public interface MemoDao {

	int insertMemo(Memo memo);

	List<Memo> getMemo();

	int deleteMemo(int memoNo);

	Memo selectOneMemo(int no);

}
