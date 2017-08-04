package com.edu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.org.entity.Faculty;
import com.edu.biz.org.entity.pojo.FacultyVo;
import com.edu.biz.org.service.FacultyService;
import com.edu.biz.schoolroll.service.MajorService;
import com.edu.core.util.BeanUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/faculty")
@Api("院系")
public class FacultyController extends BaseController<Faculty> {
	@Autowired
	private FacultyService facultyService;
	
	@Autowired
	private MajorService majorService;

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('faculty', 'add')")
	public Faculty add(@RequestBody Faculty faculty) {
		return facultyService.createFaculty(faculty);
	}
	
	@RequestMapping(path = "/check_code",method = RequestMethod. GET)
	@ApiOperation(value = "检查院系编号是否重复", notes = "根据院系编号检查是否重复")
	public Boolean checkCode(String code,  Long facultyId){
		 return facultyService.checkCode(code, facultyId);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('faculty', 'edit')")
	public Faculty edit(@PathVariable Long id, @RequestBody Faculty faculty) {
		faculty.setId(id);
		return facultyService.updateFaculty(faculty);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('faculty', 'delete')")
	public boolean delete(@PathVariable Long id) {
		return facultyService.deleteFaculty(id);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('faculty', 'get')")
	public Faculty get(@PathVariable Long id) {
		Faculty faculty = new Faculty();
		faculty.setId(id);
		return facultyService.getFaculty(faculty.getId());
	}

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('faculty', 'get')")
	public Page<FacultyVo> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Faculty> page = facultyService.searchFaculty(conditions, pageable);
		
		List<FacultyVo> facultyVos = new ArrayList<FacultyVo>();
		for (Faculty faculty: page.getContent()) {
			FacultyVo facultyVo = new FacultyVo();
			BeanUtils.copyPropertiesWithIgnoreProperties(faculty, facultyVo);
			HashMap<String,Object> map=new HashMap<String,Object>();
			map.put("facultyId", faculty.getId());
			Long majorNum = majorService.countMajor(map);
			facultyVo.setMajorNum(majorNum.intValue());
			facultyVos.add(facultyVo);
		}
//		Pageable pageableVo = new PageRequest(page.get, size, sort);
		Page<FacultyVo> facultyVoPage = new PageImpl<>(facultyVos, pageable, page.getTotalElements());
		return facultyVoPage;
	}
}
