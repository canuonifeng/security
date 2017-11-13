CREATE TABLE IF NOT EXISTS `graded_subject` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255)  NOT NULL COMMENT '分层科目名字',
  `major_id` int(10) UNSIGNED NOT NULL COMMENT '所属专业id',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `student_graded_subject_result` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `graded_subject_id` int(10) UNSIGNED NOT NULL COMMENT '所属分层科目id',
  `student_id`  int(10) UNSIGNED NOT NULL COMMENT '学生id',
  `score` float(10,1)  NOT NULL DEFAULT 0 COMMENT '分数',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;