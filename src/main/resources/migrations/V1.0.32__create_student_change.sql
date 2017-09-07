CREATE TABLE IF NOT EXISTS `student_change` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '异动ID',
  `student_id` int(10) UNSIGNED NOT NULL COMMENT '学生id',
  `change_type` varchar(32) NOT NULL COMMENT '异动类型',
  `cause` text COMMENT '异动原因',
  `status` varchar(32) NOT NULL COMMENT '状态',
  `refuse_cause` text COMMENT '驳回原因',
  `old_student_status` varchar(32) COMMENT '异动前学生状态',
  `new_student_status` varchar(32) COMMENT '异动后学生状态',
  `old_major_id` int(10) UNSIGNED COMMENT '异动前学生专业',
  `new_major_id` int(10) UNSIGNED COMMENT '异动后学生专业',
  `old_classroom_id` int(10) UNSIGNED COMMENT '异动前学生班级',
  `new_classroom_id` int(10) UNSIGNED COMMENT '异动后学生班级',
  `audit_user_id` int(10) UNSIGNED COMMENT '最后审核人',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `student_change_log` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '异动日志ID',
  `change_id` int(10) UNSIGNED NOT NULL COMMENT '异动ID',
  `old_status` varchar(32) COMMENT '操作前状态',
  `new_status` varchar(32) COMMENT '操作状态',
  `cause` text COMMENT '回复原因',
  `remark` text COMMENT '备注',
  `opUser_id` int(10) UNSIGNED COMMENT '操作人',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;