CREATE TABLE IF NOT EXISTS `final_grade_part` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '成绩组成id',
  `name` varchar(64) NOT NULL COMMENT '成绩名称',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `final_grade_part` (`name`,`created_time`,`updated_time`) VALUES
('平时成绩', now(), now()),
('考试成绩', now(), now()),
('实践成绩', now(), now()),
('考勤', now(), now());

CREATE TABLE IF NOT EXISTS `final_grade_part_course` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课程成绩id',
  `faculty_id` int(10) UNSIGNED NOT NULL COMMENT '所属院系id',
  `term_code`  varchar(64) NOT NULL COMMENT '所属学期code',
  `score` double NOT NULL DEFAULT 0.0 COMMENT '分数',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;