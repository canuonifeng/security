CREATE TABLE IF NOT EXISTS `sys_setting` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(32) NOT NULL COMMENT '系统配置code',
  `value` text NOT NULL COMMENT '配置值',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_setting_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;