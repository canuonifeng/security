CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `nickname` varchar(64) NOT NULL COMMENT '昵称',
  `email` varchar(128) NOT NULL COMMENT '用户邮箱',
  `password` varchar(64) NOT NULL COMMENT '用户密码',
  `salt` varchar(32) NOT NULL COMMENT '密码SALT',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user` (`username`, `nickname`, `email`, `password`, `salt`, `created_time`, `updated_time`) VALUES
('admin', 'test', 'test@aa', 'test', 'xxx', '2017-07-17 09:21:20', '2017-07-17 09:21:20');


CREATE TABLE `role` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `permission` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `role_permission` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(10) NOT NULL,
  `permission_id` int(10) NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `user_role` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) NOT NULL,
  `role_id` int(10) NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
