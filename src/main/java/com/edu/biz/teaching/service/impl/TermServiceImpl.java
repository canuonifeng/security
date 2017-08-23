package com.edu.biz.teaching.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.teaching.dao.TermDao;
import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.service.TermService;
import com.edu.biz.teaching.specification.TermSpecification;
import com.edu.core.exception.NotFoundException;
import com.edu.core.util.BeanUtils;

@Service
public class TermServiceImpl extends BaseService implements TermService {
	@Autowired
	private TermDao termDao;
	
	@Override
	public Term createTerm(Term term) {
		return termDao.save(term);
	}

	@Override
	public Term updateTerm(Term term) {
		Term saveTerm = termDao.findOne(term.getId());
		if (null == saveTerm) {
			throw new NotFoundException("该学期不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(term, saveTerm, "title", "code", "long_code", "start_date", "end_date");

		return termDao.save(term);
	}
	
	public Boolean checkCode(String code, Long termId) {
		Term term = termDao.getByCode(code);
		if(null == term) {
			return true;
		}
		if(term.getId().equals(termId)) {
			return true;
		}
		return false;
	}
	
	@Override
	public Term changeTermCurrent(Long id, int current) {
		Term saveTerm = termDao.findOne(id);
		if (null == saveTerm) {
			throw new NotFoundException("该学期不存在");
		}
		if (current == 1) {
			List<Term> terms = termDao.findByCurrent(current);
			for(Term term : terms) {
				term.setCurrent(0);
				termDao.save(term);
			}
		}
		saveTerm.setCurrent(current);
		return termDao.save(saveTerm);
	}

	@Override
	public Boolean deleteTerm(Long id) {
		termDao.delete(id);
		return null == termDao.findOne(id);
	}

	@Override
	public Term getTerm(Long id) {
		return termDao.findOne(id);
	}

	@Override
	public Page<Term> searchTerms(Map<String, Object> conditions, Pageable pageable) {
		return termDao.findAll(new TermSpecification(conditions), pageable);
	}
}