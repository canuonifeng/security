package com.edu.biz.schoolroll.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.schoolroll.entity.Major;

public interface MajorService {
	
	public Major createMajor(Major major);
	
	public Major updateMajor(Major major);
	
	public Boolean deleteMajor(Long id);
	
	public Major getMajor(Long id);
	
	public Boolean checkCode(String code,Long majorId);
	
	public Major getMajorByCode(String code);
	
	public Page<Major> searchMajor(Map<String, Object> conditions, Pageable pageable);
	
	public Long countMajor(Map<String, Object> conditions);
}
