package com.edu.biz.teaching.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.teaching.dao.GradedSubjectDao;
import com.edu.biz.teaching.dao.GradedSubjectResultDao;
import com.edu.biz.teaching.entity.GradedSubject;
import com.edu.biz.teaching.entity.GradedSubjectResult;
import com.edu.biz.teaching.service.GradedSubjectService;
import com.edu.biz.teaching.specification.GradedSubjectResultSpecification;
import com.edu.biz.teaching.specification.GradedSubjectSpecification;
import com.edu.core.exception.NotFoundException;
import com.edu.core.util.BeanUtils;

@Service
public class GradeSubjectServiceImpl extends BaseService implements GradedSubjectService {
	@Autowired
	private GradedSubjectDao gradedSubjectDao;
	@Autowired
	private GradedSubjectResultDao gradedSubjectResultDao;

	@Override
	public GradedSubject createGradedSubject(GradedSubject gradedSubject) {
		return gradedSubjectDao.save(gradedSubject);
	}

	@Override
	public Boolean deleteGradedSubject(Long id) {
		gradedSubjectDao.delete(id);
		return null == gradedSubjectDao.findOne(id);
	}

	@Override
	public GradedSubjectResult getGradedSubjectResult(Map<String, Object> map) {
		return gradedSubjectResultDao.findOne(new GradedSubjectResultSpecification(map));
	}

	@Override
	public List<GradedSubject> findGradedSubjects(Map<String, Object> conditions) {
		return gradedSubjectDao.findAll(new GradedSubjectSpecification(conditions));
	}

	@Override
	public GradedSubjectResult createResult(GradedSubjectResult gradedSubjectResult) {
		return gradedSubjectResultDao.save(gradedSubjectResult);
	}

	@Override
	public List<GradedSubjectResult> findGradedSubjectResults(Map<String, Object> map) {
		return gradedSubjectResultDao.findAll(new GradedSubjectResultSpecification(map));
	}

	@Override
	public GradedSubjectResult updateResult(GradedSubjectResult gradedSubjectResult) {
		GradedSubjectResult savedResult = gradedSubjectResultDao.findOne(gradedSubjectResult.getId());
		if (null == savedResult) {
			throw new NotFoundException("成绩结果集不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(gradedSubjectResult, savedResult, "sorce");
		return gradedSubjectResultDao.save(gradedSubjectResult);
	}
}
