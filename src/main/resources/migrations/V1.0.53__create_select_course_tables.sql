CREATE TABLE IF NOT EXISTS `select_course` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分层教学ID',
  `course_id` int(10) UNSIGNED NOT NULL COMMENT '课程ID',
  `term_code` varchar(32) NOT NULL COMMENT '所属学期',
  `schooltime` varchar(32) COMMENT '上课时间（周区间）',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `select_course_class` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '选课课堂ID',
  `select_course_id` int(10) UNSIGNED NOT NULL COMMENT '选课ID',
  `teacher_id` int(10) UNSIGNED NOT NULL COMMENT '教师ID',
  `student_number` int(10) UNSIGNED NOT NULL COMMENT '授课人数',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `select_course_schooltime` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '上课时间ID',
  `select_course_id` int(10) UNSIGNED NOT NULL COMMENT '选课ID',
  `week` TINYINT(1) UNSIGNED NOT NULL COMMENT '星期',
  `time_slot` TINYINT(1) UNSIGNED NOT NULL COMMENT '时段',
  `period` TINYINT(1) UNSIGNED NOT NULL COMMENT '课节',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `select_course_class_schooltime` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '关联上课时间和选课课堂ID',
  `schooltime_id` int(10) UNSIGNED NOT NULL COMMENT '上课时间ID',
  `select_course_class_id` int(10) UNSIGNED NOT NULL COMMENT '选课课堂ID',
  `building_room_id` int(10) UNSIGNED NOT NULL COMMENT '教室ID',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `select_course_classroom` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '关联选课和班级ID',
  `select_course_id` int(10) UNSIGNED NOT NULL COMMENT '选课ID',
  `classroom_id` int(10) UNSIGNED NOT NULL COMMENT '班级ID',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `select_course_classroom_member` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课堂成员ID',
  `select_course_class_id` int(10) UNSIGNED NOT NULL COMMENT '选课课堂ID',
  `student_id` int(10) UNSIGNED NOT NULL COMMENT '学生ID',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;