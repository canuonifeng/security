CREATE TABLE IF NOT EXISTS `exam_arrange` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '安排考试ID',
  `course_id` int(10) UNSIGNED COMMENT '课程ID',
  `exam_time` varchar(32) COMMENT '考试时间',
  `faculty_id` int(10) UNSIGNED COMMENT '课程ID',
  `grade` varchar(24) COMMENT '年级',
  `term_code` varchar(32) COMMENT '学期代码',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `exam_arrange_room` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '排考关联考场ID',
  `exam_arrange_id` int(10) UNSIGNED COMMENT '排考ID',
  `building_room_id` int(10) UNSIGNED COMMENT '考场ID',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `exam_arrange_room_teacher` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '排考考场关联教师ID',
  `exam_arrange_room_id` int(10) UNSIGNED COMMENT '考场ID',
  `teacher_id` int(10) UNSIGNED COMMENT '教师ID',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `exam_arrange_room_member` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '安排考试和教师关联ID',
  `exam_arrange_room_id` int(10) UNSIGNED COMMENT '排考关联考场ID',
  `student_id` int(10) UNSIGNED COMMENT '考试学员ID',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;