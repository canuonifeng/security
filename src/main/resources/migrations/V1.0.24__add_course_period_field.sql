ALTER TABLE `course` CHANGE `period` `practice_period` INT(10) NULL DEFAULT 0 COMMENT '实践学时';
ALTER TABLE `course` ADD `theory_period` INT(10) NULL DEFAULT 0 COMMENT '理论学时' AFTER `practice_period`;