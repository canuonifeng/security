CREATE TABLE `user` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `nickname` varchar(64) NOT NULL COMMENT '昵称',
  `email` varchar(128) NOT NULL COMMENT '用户邮箱',
  `password` varchar(64) NOT NULL COMMENT '用户密码',
  `salt` varchar(32) NOT NULL COMMENT '密码SALT',
  `org_id` int(10) UNSIGNED,
  `created_time` timestamp NOT NULL COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `role` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `code` varchar(64) NOT NULL COMMENT 'code',
  `created_time` timestamp NOT NULL COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `permission` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `code` varchar(64) NOT NULL COMMENT 'code',
  `created_time` timestamp NOT NULL COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `role_permission` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(10) UNSIGNED NOT NULL,
  `permission_id` int(10) UNSIGNED NOT NULL,
  `created_time` timestamp NOT NULL COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_role` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) UNSIGNED NOT NULL,
  `role_id` int(10) UNSIGNED NOT NULL,
  `created_time` timestamp NOT NULL COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `organization` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` int(10) UNSIGNED,
  `name` varchar(64) NOT NULL COMMENT '名称',
  `code` varchar(64) NOT NULL COMMENT '',
  `created_time` timestamp NOT NULL COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `menu` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` int(10) UNSIGNED,
  `permission_id` int(10) UNSIGNED,
  `name` varchar(64) NOT NULL COMMENT '名称',
  `code` varchar(64) NOT NULL COMMENT '',
  `menu_type` varchar(64) NOT NULL DEFAULT 'menu' COMMENT '',
  `url` varchar(64) COMMENT '',
  `created_time` timestamp NOT NULL COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user` (`username`, `nickname`, `email`, `password`, `salt`, `created_time`, `updated_time`) VALUES
('admin', 'test', 'test@aa', '56e88fcd763ee7a36c409ef6cf852aea', 'ujQAwILUUuVemyxz', now(), now());

INSERT INTO `permission` (`name`,`code`,`created_time`) VALUES
('系统管理', 'sys:manage', now()),
('用户管理', 'user:manage', now()),
('用户添加', 'user:add', now()),
('用户修改', 'user:edit', now()),
('用户删除', 'user:delete', now());

INSERT INTO `role` (`name`,`code`,`created_time`) VALUES 
('超级管理员','ROLE_SUPER_ADMIN',now()),
('账号管理员','ROLE_USER_MANAGER',now());

INSERT INTO `user_role` (`user_id`,`role_id`,`created_time`) VALUES 
(1, 1,now());

INSERT INTO `role_permission` (`role_id`,`permission_id`,`created_time`) VALUES 
(2, 1,now()),
(2, 2,now()),
(2, 3,now());

INSERT INTO `menu` (`name`,`code`, `url`, `parent_id`,`permission_id`) VALUES 
('系统管理', 'sys_manage', '', null, 1),
('用户管理', 'user_manage', '', 1, 2),
('添加', 'user_add', '' ,2, 3),
('修改', 'user_edit', '', 2, 4),
('删除', 'user_delete', '', 2, 5);