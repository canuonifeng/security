CREATE TABLE `faculty` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '院系ID',
  `code` varchar(64) NOT NULL COMMENT '院系编号',
  `name` varchar(64) NOT NULL COMMENT '院系名称',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='院系表';