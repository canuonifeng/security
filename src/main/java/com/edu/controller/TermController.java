package com.edu.controller;

import java.util.List;
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

import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.service.TermService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/term")
@Api("学期")
public class TermController extends BaseController<Term> {
	@Autowired
	private TermService termService;
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('term', 'add')")
	public Term add(@RequestBody Term term) {
		return termService.createTerm(term);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('term', 'edit')")
	public Term edit(@PathVariable Long id, @RequestBody Term term) {
		term.setId(id);
		return termService.updateTerm(term);
	}
	
	@RequestMapping(path = "/{id}/current", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('term', 'edit')")
	@ApiOperation(value = "修改教师状态")
	@ApiResponses({ @ApiResponse(code = 401, message = "没有登录"), @ApiResponse(code = 403, message = "没有权限"), })
	public Term changeTermCurrent(@PathVariable @ApiParam(name = "id", value = "学期ID", required = true) Long id,
			@RequestBody @ApiParam(name = "current", value = "0(取消当前学期)，1(设定为当前学期)", required = true) Map<String, Integer> params) {
		return termService.changeTermCurrent(id, params.get("current"));
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('term', 'delete')")
	public boolean delete(@PathVariable Long id) {
		return termService.deleteTerm(id);
	}
	
	@RequestMapping(path = "/checkcode",method = RequestMethod. GET)
	@ApiOperation(value = "检查代码是否重复", notes = "根据学期代码检查是否重复")
	public Boolean checkCode(String code,  Long termId){
		 return termService.checkCode(code, termId);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('term', 'get')")
	public Term get(@PathVariable Long id) {
		Term term = new Term();
		term.setId(id);
		return termService.getTerm(term.getId());
	}
	
	@RequestMapping(path = "/current", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('term', 'get')")
	public Term getCurrent() {
		return termService.getTermByCurrent(1);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('term', 'get')")
	public Page<Term> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "longCode" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return termService.searchTerms(conditions, pageable);
	}
	
	@RequestMapping(path="/all", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('term', 'get')")
	public List<Term> findAllTerms(@RequestParam Map<String, Object> conditions) {
		return termService.findTerms(conditions);
	}
}
