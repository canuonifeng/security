package com.edu.biz.teaching.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.teaching.entity.Term;

public interface TermService {
	
	public Term createTerm(Term term);
	
	public Term updateTerm(Term term);
	
	public Term changeTermCurrent(Long id, int current);
	
	public Boolean checkCode(String code, Long termId);
	
	public Boolean deleteTerm(Long id);
	
	public Term getTerm(Long id);
	
	public Page<Term> searchTerms(Map<String, Object> conditions, Pageable pageable);
	
	public List<Term> findTerms(Map<String, Object> conditions);
	
	public Term getTermByCode(String code);
}
