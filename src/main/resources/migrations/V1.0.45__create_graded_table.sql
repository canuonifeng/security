CREATE TABLE IF NOT EXISTS `graded_teaching` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分层教学ID',
  `course_id` int(10) UNSIGNED NOT NULL COMMENT '课程id',
  `schooltime` varchar(32) COMMENT '上课时间（周区间）',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `graded_rank` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分层等级ID',
  `graded_id` int(10) UNSIGNED NOT NULL COMMENT '分层教学id',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '等级名称',
  `exam_course_id` int(10) UNSIGNED NOT NULL COMMENT '考试科目ID',
  `min_score` int(10) UNSIGNED NOT NULL COMMENT '最低分数',
  `max_score` int(10) UNSIGNED NOT NULL COMMENT '最高分数',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `graded_course` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分层课程ID',
  `graded_id` int(10) UNSIGNED NOT NULL COMMENT '分层教学id',
  `rank_id` int(10) UNSIGNED NOT NULL COMMENT '分层等级id',
  `teacher_id` int(10) UNSIGNED NOT NULL COMMENT '教师id',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `graded_schooltime` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '上课时间id',
  `graded_id` int(10) UNSIGNED NOT NULL COMMENT '分层教学id',
  `week` TINYINT(1) UNSIGNED NOT NULL COMMENT '星期',
  `time_slot` TINYINT(1) UNSIGNED NOT NULL COMMENT '时段',
  `period` TINYINT(1) UNSIGNED NOT NULL COMMENT '课节',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `graded_course_schooltime` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '关联上课时间和分层课程id',
  `schooltime_id` int(10) UNSIGNED NOT NULL COMMENT '上课时间id',
  `graded_course_id` int(10) UNSIGNED NOT NULL COMMENT '分层课程id',
  `building_room_id` int(10) UNSIGNED NOT NULL COMMENT '教室id',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `graded_classroom` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分层等级ID',
  `graded_id` int(10) UNSIGNED NOT NULL COMMENT '分层教学id',
  `classroom_id` int(10) UNSIGNED NOT NULL COMMENT '班级id',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `graded_classroom_member` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分层等级ID',
  `graded_course_id` int(10) UNSIGNED NOT NULL COMMENT '分层课程id',
  `student_id` int(10) UNSIGNED NOT NULL COMMENT '学生id',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;