CREATE TABLE IF NOT EXISTS `term` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学期ID',
  `title` varchar(255) COMMENT '标题',
  `code` varchar(16) COMMENT '短代码 17-18-1',
  `long_code` varchar(16) COMMENT '长代码 2017-2018-1',
  `start_date` varchar(16) COMMENT '开始日期',
  `end_date` varchar(16) COMMENT '结束日期',
  `current` tinyint(1) COMMENT '是否当前学期',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `term_code` (`code`),
  UNIQUE KEY `term_long_code` (`long_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `school_calendar` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `term_id` int(10) UNSIGNED NOT NULL COMMENT '学期',
  `mark_day` varchar(16) COMMENT '被标记的日期',
  `mark_type` varchar(16) COMMENT '类型',
  `remark` varchar(512) COMMENT '备注',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
