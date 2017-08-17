package com.edu.biz.teaching.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.teaching.entity.Program;

public interface ProgramService {
	public Program createProgram(Program program);
	
	public Program updateProgram(Program program);
	
	public Boolean deleteProgram(Long id);
	
	public Program getProgram(Long id);
	
	public Page<Program> searchPrograms(Map<String, Object> conditions, Pageable pageable);

}
