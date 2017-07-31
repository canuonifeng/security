package com.edu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.edu.biz.org.service.FacultyService;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController extends BaseController<Faculty> {
	@Autowired
	private FacultyService facultyService;

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('faculty', 'add')")
	public Faculty add(@RequestBody Faculty faculty) {
		return facultyService.createFaculty(faculty);
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
	public Page<Faculty> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return facultyService.searchFaculty(conditions, pageable);
	}
}
