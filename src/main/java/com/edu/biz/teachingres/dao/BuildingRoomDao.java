package com.edu.biz.teachingres.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.edu.biz.base.BaseDao;
import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.entity.CountRoomType;

public interface BuildingRoomDao extends BaseDao<BuildingRoom> {
	@Query(value = "select new com.edu.biz.teachingres.entity.CountRoomType(t.roomType, count(t)) from BuildingRoom t group by t.roomType")
	public List<CountRoomType> countGroupByRoomType();
}
