ALTER TABLE `student` ADD `idtype` varchar(16)  COMMENT '证件类型' AFTER `idcard`;
ALTER TABLE `student` ADD `nation` varchar(32)  COMMENT '民族' AFTER `idcard`;
ALTER TABLE `student` ADD `birthday` varchar(16)  COMMENT '出生日期' AFTER `idcard`;
