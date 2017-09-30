ALTER TABLE `course` ADD `week_period` INT(10) NULL DEFAULT 0 COMMENT '周学时' AFTER `theory_period`;
ALTER TABLE `course` ADD `faculty_id` int(10) UNSIGNED NOT NULL COMMENT '专业ID' AFTER `week_period`;