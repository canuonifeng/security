package com.edu.biz.teaching.dao;

import java.util.List;

import com.edu.biz.base.BaseDao;
import com.edu.biz.teaching.entity.Term;

public interface TermDao extends BaseDao<Term> {
	
	public List<Term> findByCurrent(int current);
	
	public Term getByCode(String code);
	
	public Term getByCurrent(int current);
}
