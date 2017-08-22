package com.edu.biz.teachingres.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.edu.biz.common.entity.Dict;

@Entity
@DiscriminatorValue("room-type")
public class RoomType extends Dict {
	
}
