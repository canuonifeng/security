ALTER TABLE `graded_teaching` ADD `subject_id` int(10) UNSIGNED COMMENT '考试科目' AFTER `course_id`;
ALTER TABLE `graded_rank` DROP COLUMN `exam_course_id`;