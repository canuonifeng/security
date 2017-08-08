CREATE TABLE IF NOT EXISTS `major` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(64) NOT NULL COMMENT '专业编号',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `faculty_id` int(10) UNSIGNED NOT NULL COMMENT '院系ID',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `major_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `classroom` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(64) NOT NULL COMMENT '编号',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `major_id` int(10) UNSIGNED NOT NULL COMMENT '专业ID',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `classroom_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;