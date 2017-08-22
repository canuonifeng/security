package com.edu.biz.teachingres.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.teachingres.dao.RoomTypeDao;
import com.edu.biz.teachingres.entity.RoomType;
import com.edu.biz.teachingres.service.RoomTypeService;
import com.edu.biz.teachingres.specification.RoomTypeSpecification;
import com.edu.core.exception.NotFoundException;
import com.edu.core.util.BeanUtils;

@Service
public class RoomTypeServiceImpl extends BaseService implements RoomTypeService {
	@Autowired
	private RoomTypeDao roomTypeDao;
	
	@Override
	public RoomType createRoomType(RoomType roomType) {
		return roomTypeDao.save(roomType);
	}

	@Override
	public RoomType updateRoomType(RoomType roomType) {
		RoomType saveRoomType = roomTypeDao.getByDictKey(roomType.getDictKey());
		if (null == saveRoomType) {
			throw new NotFoundException("该课程不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(roomType, saveRoomType, "dict_value");

		return roomTypeDao.save(roomType);
	}

	@Override
	public Boolean deleteRoomType(Long id) {
		roomTypeDao.delete(id);
		return null == roomTypeDao.findOne(id);
	}

	@Override
	public RoomType getRoomType(Long id) {
		return roomTypeDao.findOne(id);
	}

	@Override
	public List<RoomType> findRoomTypes(Map<String, Object> conditions) {
		return roomTypeDao.findAll(new RoomTypeSpecification(conditions));
	}

}
