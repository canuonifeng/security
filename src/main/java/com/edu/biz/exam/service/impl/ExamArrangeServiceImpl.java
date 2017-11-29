package com.edu.biz.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.exam.dao.ExamArrangeDao;
import com.edu.biz.exam.entity.ExamArrange;
import com.edu.biz.exam.service.ExamArrangeService;

@Service
public class ExamArrangeServiceImpl extends BaseService implements ExamArrangeService {
	@Autowired
	ExamArrangeDao examArrangeDao;
	
	public void createExamArrange(ExamArrange examArrange) {
		examArrangeDao.save(examArrange);
	}
}
