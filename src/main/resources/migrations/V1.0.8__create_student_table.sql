CREATE TABLE IF NOT EXISTS `student` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `no` varchar(64) NOT NULL COMMENT '学号',
  `name` varchar(64) NOT NULL COMMENT '姓名',
  `gender` ENUM('male','female','secret') NOT NULL DEFAULT 'secret' COMMENT '性别'，
  `year_month` varchar(12) NOT NULL COMMENT '入学年月',
  `grade` varchar(4) NOT NULL COMMENT '年级',
  `major_id` int(10) UNSIGNED NOT NULL COMMENT '专业ID',
  `classroom_id` int(10) UNSIGNED COMMENT '班级ID',
  `status` ENUM('enable','disable','delete','changing') NOT NULL DEFAULT 'enable' COMMENT '状态，正常，禁用，删除，异动中，退学，休学，开除，劝退'
  `from` ENUM('alone','unified','selftaught') NOT NULL DEFAULT 'higher' COMMENT '学生来源，单招、统考、自考'，
  `native_place` varchar(64) COMMENT '籍贯',
  `idcard` varchar(32) COMMENT '身份证号',
  `remark` text COMMENT '备注',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生学籍表';

CREATE TABLE IF NOT EXISTS `classroom_member` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `classroom_id` int(10) UNSIGNED NOT NULL COMMENT '班级ID',
  `student_id` int(10) UNSIGNED NOT NULL COMMENT '学员ID',
  `created_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生学籍表';

ALTER TABLE `classroom` ADD `grade` VARCHAR(4)  COMMENT '年级' AFTER `major_id`;
ALTER TABLE `classroom` ADD `teacher_id` int(10) UNSIGNED  COMMENT '班主任' AFTER `major_id`;
ALTER TABLE `classroom` ADD `room_id` int(10) UNSIGNED  COMMENT '教室' AFTER `major_id`;





