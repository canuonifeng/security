ALTER TABLE `organization` ADD `org_code` VARCHAR(64)  COMMENT '内部编码' AFTER `code`;
ALTER TABLE `user` ADD `org_code` VARCHAR(64)  COMMENT '内部编码' AFTER `org_id`;