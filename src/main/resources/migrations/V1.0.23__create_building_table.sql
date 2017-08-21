CREATE TABLE IF NOT EXISTS `building` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '教学楼ID',
  `name` varchar(255) COMMENT '教学楼名称',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `building_room` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '教室ID',
  `floor` int(8) COMMENT '楼层',
  `name` varchar(255) COMMENT '教室名称',
  `room_type` varchar(255) COMMENT '教室类型，普通，多媒体，实验室，计算机',
  `seat_num` int(10) UNSIGNED COMMENT '坐位数',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;