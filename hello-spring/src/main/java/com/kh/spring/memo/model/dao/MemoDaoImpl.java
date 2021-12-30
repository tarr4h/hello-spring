package com.kh.spring.memo.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.memo.model.vo.Memo;

@Repository
public class MemoDaoImpl implements MemoDao {

	@Autowired
	private SqlSession session;

	@Override
	public int insertMemo(Memo memo) {
		return session.insert("memo.insertMemo", memo);
	}

	@Override
	public List<Memo> getMemo() {
		return session.selectList("memo.getMemo");
	}

	@Override
	public int deleteMemo(int memoNo) {
		return session.delete("memo.deleteMemo", memoNo);
	}

	@Override
	public Memo selectOneMemo(int no) {
		return session.selectOne("memo.selectOneMemo", no);
	}
	
	
}
