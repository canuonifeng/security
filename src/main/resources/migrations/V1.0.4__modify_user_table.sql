ALTER TABLE `user` CHANGE `nickname` `nickname` VARCHAR(64) NULL COMMENT '昵称';
ALTER TABLE `user` ADD `name` VARCHAR(64)  COMMENT '姓名' AFTER `id`;
ALTER TABLE `user` ADD `phone` VARCHAR(64)  COMMENT '联系电话' AFTER `name`;
ALTER TABLE `user` ADD `gender` ENUM('male','female','secret') NOT NULL DEFAULT 'secret' COMMENT '性别' AFTER `name`;
ALTER TABLE `user` ADD `faculty_id` int(10) UNSIGNED  COMMENT '院系' AFTER `org_id`;
ALTER TABLE `user` ADD `last_login_time` TIMESTAMP NULL DEFAULT NULL  COMMENT '最后登录时间' AFTER `faculty_id`;
ALTER TABLE `user` ADD `last_login_ip` VARCHAR(64) COMMENT '最后登录ip' AFTER `faculty_id`;
