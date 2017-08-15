CREATE TABLE IF NOT EXISTS `teacher` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '教师ID',
  `no` varchar(64) NOT NULL COMMENT '工号',
  `name` varchar(64) NOT NULL COMMENT '姓名',
  `gender` ENUM('male','female','secret') NOT NULL DEFAULT 'secret' COMMENT '性别',
  `start_work_time` varchar(32) COMMENT '从教时间',
  `status` ENUM('enable','disable','dimission') NOT NULL DEFAULT 'enable' COMMENT '状态，正常，禁用，离职',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `teacher_no` (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;