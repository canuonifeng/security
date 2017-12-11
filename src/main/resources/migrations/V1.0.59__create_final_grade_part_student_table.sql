CREATE TABLE IF NOT EXISTS `final_grade_part_student` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学生成绩组成id',
  `student_id` int(10) UNSIGNED NOT NULL COMMENT '学生id',
  `final_grade_part_course_id` int(10) UNSIGNED NOT NULL COMMENT '对应课程成绩id',
  `score` double NOT NULL DEFAULT 0.0 COMMENT '学生分数',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;