CREATE TABLE IF NOT EXISTS `dict` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '字典ID',
  `dict_key` varchar(32) NOT NULL COMMENT '字典key',
  `dict_value` varchar(32) NOT NULL COMMENT '字典值',
  `group_code` varchar(32) COMMENT '分组',
  `seq` int(8) UNSIGNED DEFAULT 0 COMMENT '排序号',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `dict` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_code` varchar(32) NOT NULL COMMENT '分组code',
  `group_name` varchar(32) NOT NULL COMMENT '分组name',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
