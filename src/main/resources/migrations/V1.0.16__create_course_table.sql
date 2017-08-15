CREATE TABLE IF NOT EXISTS `course` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `code` varchar(64) NOT NULL COMMENT '课程编号',
  `name` varchar(64) NOT NULL COMMENT '课程名称',
  `credit` float DEFAULT 0 COMMENT '学分',
  `period`int(10) UNSIGNED COMMENT '学时',
  `status` ENUM('enable','disable','delete') NOT NULL DEFAULT 'enable' COMMENT '状态，正常，禁用，删除',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `course_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `teacher_course` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `teacher_id` int(10) UNSIGNED NOT NULL COMMENT '教师ID',
  `course_id`  int(10) UNSIGNED NOT NULL COMMENT '课程ID',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;