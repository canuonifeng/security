package com.edu.biz.teaching.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.common.util.TermCodeUtil;
import com.edu.biz.teaching.dao.ProgramCourseDao;
import com.edu.biz.teaching.dao.ProgramDao;
import com.edu.biz.teaching.entity.CountProgramCourseCategory;
import com.edu.biz.teaching.entity.Program;
import com.edu.biz.teaching.entity.ProgramCourse;
import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.service.ProgramService;
import com.edu.biz.teaching.service.TermService;
import com.edu.biz.teaching.specification.ProgramCourseSpecification;
import com.edu.biz.teaching.specification.ProgramSpecification;
import com.edu.biz.teachingres.dao.CourseDao;
import com.edu.biz.teachingres.entity.Course;
import com.edu.biz.teachingres.specification.CourseSpecification;
import com.edu.core.exception.InvalidParameterException;
import com.edu.core.exception.NotFoundException;
import com.edu.core.exception.ServiceException;
import com.edu.core.util.BeanUtils;

@Service
public class ProgramServiceImpl extends BaseService implements ProgramService {
	@Autowired
	private ProgramDao programDao;
	@Autowired
	private ProgramCourseDao programCourseDao;
	@Autowired
	private TermService termService;
	@Autowired
	private CourseDao courseDao;
	@Override
	public Program createProgram(Program program) {
		Program savedProgram = getProgram(program.getGrade(), program.getMajor().getId());
		if(savedProgram != null) {
			throw new InvalidParameterException("该教学计划已被创建");
		}
		return programDao.save(program);
	}
	
	@Override
	public ProgramCourse createProgramCourse(ProgramCourse programCourse) {
		return programCourseDao.save(programCourse);
	}

	@Override
	public Program updateProgram(Program program) {
		Program saveProgram = programDao.findOne(program.getId());
		if (null == saveProgram) {
			throw new NotFoundException("该教学计划不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(program, saveProgram, "grade", "major");

		return programDao.save(saveProgram);
	}

	@Override
	public ProgramCourse updateProgramCourse(ProgramCourse programCourse) {
		ProgramCourse saveProgramCourse = programCourseDao.findOne(programCourse.getId());
		if (null == saveProgramCourse) {
			throw new NotFoundException("该教学计划课程不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(programCourse, saveProgramCourse, "category", "nature", "testWay", "weekPeriod", "termNum", "termCode", "practicePeriod", "theoryPeriod", "credit");

		return programCourseDao.save(saveProgramCourse);
	}
	
	@Override
	public Boolean deleteProgram(Long id) {
		programDao.delete(id);
		return null == programDao.findOne(id);
	}
	
	@Override
	public Boolean deleteProgramCourse(Long programCourseId) {
		programCourseDao.delete(programCourseId);
		return null == programCourseDao.findOne(programCourseId);
	}
	
	@Override
	public Program getProgram(Long id) {
		return programDao.findOne(id);
	}
	
	@Override
	public Program getProgram(String grade, Long majorId) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("majorId", majorId);
		conditions.put("grade", grade);
		return programDao.findOne(new ProgramSpecification(conditions));
	}
	
	@Override
	public Page<Program> searchPrograms(Map<String, Object> conditions, Pageable pageable) {
		return programDao.findAll(new ProgramSpecification(conditions), pageable);
	}

	@Override
	public Page<ProgramCourse> searchProgramCourse(Map<String, Object> conditions, Pageable pageable) {
		return programCourseDao.findAll(new ProgramCourseSpecification(conditions), pageable);
	}

	@Override
	public Page<Course> searchCoursesNotInProgram(Long programId, Map<String, Object> conditions, Pageable pageable) {
		List<ProgramCourse> existCourses = new ArrayList<ProgramCourse>();
		conditions.put("programId", programId);
		existCourses  = programCourseDao.findAll(new ProgramCourseSpecification(conditions));
//		Long[] notCourseIds = new Long[existCourses.size()];
		List<Long> notCourseIds = new ArrayList<>();
		for (int i = 0; i < existCourses.size(); i++) {
			notCourseIds.add(existCourses.get(i).getCourse().getId()) ;
		}
		conditions.put("notCourseIds", notCourseIds);
		return courseDao.findAll(new CourseSpecification(conditions), pageable);
	}
	@Override
	public Boolean joinProgram(Course course, Program program) {
		Boolean result = canJoinProgram(course, program);
		if (!result) {
			throw new ServiceException("403", "该课程不能加入该教学计划");
		}
		ProgramCourse programCourse = new ProgramCourse();
		programCourse.setCourse(course);
		programCourse.setProgram(program);
		programCourse.setCredit(course.getCredit());
		programCourse.setPracticePeriod(course.getPracticePeriod());
		programCourse.setTheoryPeriod(course.getTheoryPeriod());
		programCourse.setTermNum(1);
		programCourse.setWeekPeriod(course.getWeekPeriod());
		programCourse.setTermCode(TermCodeUtil.getTermCode(program.getGrade(), 1));
		createProgramCourse(programCourse);
		return true;
	}

	@Override
	public Map<String, Map<String, List<ProgramCourse>>> showCourseTable(Long id) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("programId", id);
		List<ProgramCourse> courses = programCourseDao.findAll(new ProgramCourseSpecification(map));
		return generateTable(courses);
	}
	
	private Map<String, Map<String, List<ProgramCourse>>> generateTable(List<ProgramCourse> courses) {
		HashMap<String, Map<String, List<ProgramCourse>>> map = new HashMap<>();

		for (ProgramCourse programCourse : courses) {
			if(map.containsKey(programCourse.getCategory())) {
				if(map.get(programCourse.getCategory()).containsKey(programCourse.getNature())) {
					map.get(programCourse.getCategory()).get(programCourse.getNature()).add(programCourse);
				} else {
					List<ProgramCourse> result = new ArrayList<ProgramCourse>();
					result.add(programCourse);
					map.get(programCourse.getCategory()).put(programCourse.getNature(), result);
				}
			} else {
				List<ProgramCourse> result = new ArrayList<ProgramCourse>();
				Map<String, List<ProgramCourse>> type =  new HashMap<>();
				result.add(programCourse);
				type.put(programCourse.getNature(), result);
				map.put(programCourse.getCategory(), type);
			}
		}
		return map;
	}
	
	@Override
	public List<ProgramCourse> searchAllProgramCourse(Map<String, Object> conditions) {
		return programCourseDao.findAll(new ProgramCourseSpecification(conditions));
	}
	
	@Override
	public List<Term> getProgramTerm(Long id) {
		Program program = getProgram(id);
		String prefix = program.getGrade().substring(program.getGrade().length() - 2);
		String firstCodePrefix = prefix+"-"+(Integer.parseInt(prefix)+1);
		Map<String, Object> conditions = new HashMap<>();
		List<String> codes = new ArrayList<>();
		for (int i = 1; i <= 2; i++) {
			codes.add(firstCodePrefix+"-"+i) ;
		}
		String secondCodePrefix = (Integer.parseInt(prefix)+1)+"-"+(Integer.parseInt(prefix)+2);
		for (int i = 1; i <= 2; i++) {
			codes.add(secondCodePrefix+"-"+i) ;
		}
		String thirdCodePrefix = (Integer.parseInt(prefix)+2)+"-"+(Integer.parseInt(prefix)+3);
		for (int i = 1; i <= 2; i++) {
			codes.add(thirdCodePrefix+"-"+i) ;
		}
		conditions.put("inCodes", codes);
		List<Term> terms = termService.findTerms(conditions);
		if(terms.size() == 6) {
			return terms;
		} else {
			dealTerms(terms, codes);
			return termService.findTerms(conditions);
		}
	}
	
	@Override
	public ProgramCourse getProgramCourse(long courseId, String term) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("courseId", courseId);
		map.put("term", term);
		return programCourseDao.findOne(new ProgramCourseSpecification(map));
	}
	
	@Override
	public List<CountProgramCourseCategory> countProgramCourseByProgramIdGroupByCategory(Long programId) {
		return programCourseDao.countProgramCourseByProgramIdGroupByCategory(programId);
	}
	
	private Boolean dealTerms(List<Term> terms, List<String> codes){
		for (int i = 0; i < codes.size(); i++) {
			for (int j = 0; j < terms.size(); j++) {
				if(terms.get(j).getCode().equals(codes.get(i))) {
					break;
				}
				if(terms.size()-1 == j) {
					createTerm(codes.get(i));
				}
			}
		}
		return true;
	}
	
	private Boolean createTerm(String code) {
		String[] codeArray = code.split("-");
		String longCode = "20"+codeArray[0]+"-"+"20"+codeArray[1]+"-"+codeArray[2];
		String title = "";
		if(codeArray[2].equals("1")) {
			title = "20"+codeArray[0]+"-"+"20"+codeArray[1]+"学年第一学期";
		} else {
			title = "20"+codeArray[0]+"-"+"20"+codeArray[1]+"学年第二学期";
		}
		Term term = new Term();
		term.setCode(code);
		term.setLongCode(longCode);
		term.setTitle(title);
		termService.createTerm(term);
		return true;
	}
	
	private Boolean canJoinProgram(Course course, Program program) {
		if (course == null) {
			return false;
		}
		if (program == null) {
			return false;
		}
		HashMap<String, Object> map = new HashMap<String ,Object>();
		map.put("programId", program.getId());
		map.put("courseId", course.getId());
		ProgramCourse programCourse = programCourseDao.getByProgramIdAndCourseId(program.getId(), course.getId());
		if(programCourse == null) {
			return true;
		}
		return false;
	}
}
