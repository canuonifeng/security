DROP TABLE permission;
ALTER TABLE `role_permission` CHANGE `permission_id` `permission_code` varchar(32) COMMENT '权限编码';