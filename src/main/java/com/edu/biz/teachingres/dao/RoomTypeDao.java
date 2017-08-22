package com.edu.biz.teachingres.dao;

import com.edu.biz.base.BaseDao;
import com.edu.biz.teachingres.entity.RoomType;

public interface RoomTypeDao extends BaseDao<RoomType> {
	public RoomType getByDictKey(String dictKey);
}
