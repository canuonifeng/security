package com.edu.biz.teachingres.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.biz.base.BaseDao;
import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.entity.CountRoomType;

public interface BuildingRoomDao extends BaseDao<BuildingRoom> {
	@Query(value = "select new com.edu.biz.teachingres.entity.CountRoomType(t.roomType, count(t)) from BuildingRoom t group by t.roomType")
	public List<CountRoomType> countGroupByRoomType();
	
	@Query(value = "select count(distinct floor) from BuildingRoom where building.id=:buildingId")
	public Long countDistinctFloor(@Param("buildingId") Long id);
	
	@Query(value = "select new com.edu.biz.teachingres.entity.CountRoomType(t.roomType, count(t)) from BuildingRoom t where t.building.id=:buildingId group by t.roomType")
	public List<CountRoomType> countGroupByRoomTypeByBuildingId(@Param("buildingId") Long buildingId);
	
	@Modifying
	@Query(value="delete from BuildingRoom b where b.floor=:floor")
	public void deleteByFloor(@Param("floor") Long floor);
}
