package com.edu.biz.schoolroll.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.schoolroll.entity.Major;
import com.edu.biz.schoolroll.entity.MajorStatus;

public interface MajorService {
	
	public Major createMajor(Major major);
	
	public Major updateMajor(Major major);
	
	public Major changeMajorStatus(Long id, MajorStatus status);
	
	public Boolean deleteMajor(Long id);
	
	public Major getMajor(Long id);
	
	public Boolean checkCode(String code,Long majorId);
	
	public Major getMajorByCode(String code);
	
	public Page<Major> searchMajor(Map<String, Object> conditions, Pageable pageable);
	
	public List<Major> findMajors(Map<String, Object> conditions);
	
	public Long countMajor(Map<String, Object> conditions);
}
