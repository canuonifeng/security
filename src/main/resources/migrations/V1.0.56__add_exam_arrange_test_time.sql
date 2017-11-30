ALTER TABLE `exam_arrange` ADD `exam_start_time` TIMESTAMP NULL DEFAULT NULL COMMENT '考试开始时间' AFTER `faculty_id`;
ALTER TABLE `exam_arrange` ADD `exam_end_time` TIMESTAMP NULL DEFAULT NULL COMMENT '考试结束时间' AFTER `exam_start_time`;
ALTER TABLE `exam_arrange` DROP COLUMN `exam_time`;