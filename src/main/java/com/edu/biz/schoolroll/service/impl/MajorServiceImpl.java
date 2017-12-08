package com.edu.biz.schoolroll.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.schoolroll.dao.MajorDao;
import com.edu.biz.schoolroll.entity.Major;
import com.edu.biz.schoolroll.entity.MajorStatus;
import com.edu.biz.schoolroll.service.MajorService;
import com.edu.biz.schoolroll.specification.MajorSpecification;
import com.edu.core.exception.NotFoundException;
import com.edu.core.exception.ServiceException;

@Service
public class MajorServiceImpl extends BaseService implements MajorService {
	@Autowired
	private MajorDao majorDao;

	@Override
	public Major createMajor(Major major) {
		if (!this.checkCode(major.getCode(), null)) {
			throw new ServiceException("406", "专业代码已被占用");
		}
		return majorDao.save(major);
	}

	@Override
	public Major updateMajor(Major major) {
		Major savedMajor = majorDao.findOne(major.getId());
		if (null == savedMajor) {
			throw new NotFoundException("专业不存在");
		}
		if (!this.checkCode(major.getCode(), major.getId())) {
			throw new ServiceException("406", "专业代码已被占用");
		}
		return majorDao.save(major);
	}

	@Override
	public Major changeMajorStatus(Long id, MajorStatus status) {
		Major savedMajor = majorDao.findOne(id);
		if (null == savedMajor) {
			throw new NotFoundException("专业不存在");
		}
		savedMajor.setStatus(status);
		return majorDao.save(savedMajor);
	}

	@Override
	public Boolean deleteMajor(Long id) {
		majorDao.delete(id);
		return null == majorDao.findOne(id);
	}

	@Override
	public Major getMajor(Long id) {
		return majorDao.findOne(id);
	}

	@Override
	public List<Major> findMajors(Map<String, Object> conditions) {
		return majorDao.findAll(new MajorSpecification(conditions), new Sort(Direction.DESC, "createdTime"));
	}

	@Override
	public Major getMajorByCode(String code) {
		return majorDao.getByCode(code);
	}

	@Override
	public Boolean checkCode(String code, Long majorId) {
		Major major = majorDao.getByCode(code);
		if (null == major) {
			return true;
		}
		if (major.getId().equals(majorId)) {
			return true;
		}
		return false;
	}

	@Override
	public Page<Major> searchMajor(Map<String, Object> conditions, Pageable pageable) {
		return majorDao.findAll(new MajorSpecification(conditions), pageable);
	}

	@Override
	public Long countMajor(Map<String, Object> conditions) {
		return majorDao.count(new MajorSpecification(conditions));
	}
}
