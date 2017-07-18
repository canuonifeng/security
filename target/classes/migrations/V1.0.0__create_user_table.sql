CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `nickname` varchar(64) NOT NULL COMMENT '昵称',
  `email` varchar(128) NOT NULL COMMENT '用户邮箱',
  `password` varchar(64) NOT NULL COMMENT '用户密码',
  `salt` varchar(32) NOT NULL COMMENT '密码SALT',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user` (`username`, `nickname`, `email`, `password`, `salt`, `created_time`, `updated_time`) VALUES
('admin', 'test', 'test@aa', 'test', 'xxx', '2017-07-17 09:21:20', '2017-07-17 09:21:20');


