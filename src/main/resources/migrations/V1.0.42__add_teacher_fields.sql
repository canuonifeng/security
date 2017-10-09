ALTER TABLE `teacher` ADD `title` varchar(32) COMMENT '职称' AFTER `gender`;
ALTER TABLE `teacher` ADD `degree` varchar(32) COMMENT '学历' AFTER `gender`;