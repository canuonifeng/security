package com.edu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.service.StudentService;
import com.edu.biz.teaching.entity.GradedSubject;
import com.edu.biz.teaching.entity.GradedSubjectResult;
import com.edu.biz.teaching.entity.SubjectStatus;
import com.edu.biz.teaching.service.GradedSubjectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/gradesubject")
@Api("分层学科")
public class GradedSubjectController extends BaseController<GradedSubject> {
	@Autowired
	private GradedSubjectService gradedSubjectService;
	@Autowired
	private StudentService studentService;

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('gradedSubject', 'add')")
	public GradedSubject create(@RequestBody GradedSubject gradedSubject) {
		return gradedSubjectService.createGradedSubject(gradedSubject);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('gradedSubject', 'edit')")
	public GradedSubject edit(@PathVariable Long id, @RequestBody GradedSubject gradedSubject) {
		gradedSubject.setId(id);
		return gradedSubjectService.updateGradedSubject(gradedSubject);
	}
	
	@RequestMapping(path = "/{id}/status", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('gradedSubject', 'edit')")
	@ApiOperation(value = "修改科目状态")
	@ApiResponses({ @ApiResponse(code = 401, message = "没有登录"), @ApiResponse(code = 403, message = "没有权限"), })
	public GradedSubject changeSubjectStatus(@PathVariable @ApiParam(name = "id", value = "科目ID", required = true) Long id,
			@RequestBody @ApiParam(name = "status", value = "enable(启用)，disable(禁用)", required = true) Map<String, String> params) {
		SubjectStatus status = SubjectStatus.valueOf(params.get("status"));
		return gradedSubjectService.changeSubjectStatus(id, status);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('gradedSubject', 'delete')")
	public boolean delete(@PathVariable Long id) {
		return gradedSubjectService.deleteGradedSubject(id);
	}

	@RequestMapping(path = "/all", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('gradedSubject', 'get')")
	public List<GradedSubject> get(@RequestParam Map<String, Object> conditions) {
		return gradedSubjectService.findGradedSubjects(conditions);
	}

	@RequestMapping(path = "/result", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('gradedSubject', 'add')")
	public GradedSubjectResult modifyResult(@RequestBody GradedSubjectResult gradedSubjectResult) {
		Map<String, Object> map = new HashMap<>();
		map.put("studentId", gradedSubjectResult.getStudent().getId());
		map.put("gradedSubjectId", gradedSubjectResult.getGradedSubject().getId());
		GradedSubjectResult result = gradedSubjectService.getGradedSubjectResult(map);
		if (result == null) {
			Student student = studentService.getStudent(gradedSubjectResult.getStudent().getId());
			gradedSubjectResult.setStudent(student);
			return gradedSubjectService.createResult(gradedSubjectResult);
		}
		gradedSubjectResult.setId(result.getId());
		return gradedSubjectService.updateResult(gradedSubjectResult);
	}

	@RequestMapping(path = "/result/{resultId}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('classroom', 'edit')")
	public GradedSubjectResult edit(@PathVariable Long resultId, @RequestBody GradedSubjectResult gradedSubjectResult) {
		gradedSubjectResult.setId(resultId);
		return gradedSubjectService.updateResult(gradedSubjectResult);
	}
}
