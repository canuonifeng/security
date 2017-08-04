package com.edu.biz.schoolroll.dao;

import com.edu.biz.base.BaseDao;
import com.edu.biz.schoolroll.entity.Major;

public interface MajorDao extends BaseDao<Major> {
	public Major getByCode(String code);
}
