package com.edu.biz.teaching.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.teaching.dao.GradedTeachingDao;
import com.edu.biz.teaching.entity.GradedTeaching;
import com.edu.biz.teaching.service.GradedTeachingService;
import com.edu.biz.teaching.specification.GradedSpecification;

@Service
public class GradedTeachingServiceImpl extends BaseService implements GradedTeachingService {
	@Autowired
	private GradedTeachingDao gradedTeachingDao;
	
	@Override
	public List<GradedTeaching> findGradedTeachings(Map<String, Object> conditions) {
		return gradedTeachingDao.findAll(new GradedSpecification(conditions));
	}

}
