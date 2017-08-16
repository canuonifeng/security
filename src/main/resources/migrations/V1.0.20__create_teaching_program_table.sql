CREATE TABLE IF NOT EXISTS `teaching_program` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '教学计划ID',
  `grade` varchar(4) NOT NULL COMMENT '年级',
  `major_id` int(10) UNSIGNED NOT NULL COMMENT '专业ID',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `teaching_program_course` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `teaching_program_id` int(10) UNSIGNED NOT NULL COMMENT '教学计划ID',
  `course_id` int(10) UNSIGNED NOT NULL COMMENT '课程ID',
  `category` varchar(32) COMMENT '课程类别',
  `nature` varchar(32) COMMENT '课程性质',
  `test_way` varchar(32) COMMENT '考试方式',
  `credit` float DEFAULT 0 COMMENT '学分',
  `period`int(10) UNSIGNED COMMENT '学时',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
