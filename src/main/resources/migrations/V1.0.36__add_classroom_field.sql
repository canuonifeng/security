ALTER TABLE `classroom` ADD `last_assign_num` int(10) NOT NULL DEFAULT 0 COMMENT '最后个分配序号' AFTER `is_assign_num`;