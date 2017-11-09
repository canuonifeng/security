ALTER TABLE `teaching_program_course` ADD `master_teacher_id`  int(10) UNSIGNED COMMENT '主带老师' AFTER `week_period`;
ALTER TABLE `teaching_program_course` ADD `merge_classroom_ids` VARCHAR(255) NULL COMMENT '合并班级ids' AFTER `master_teacher_id`;
ALTER TABLE `teaching_program_course` ADD `class_week_stage` VARCHAR(255) NULL COMMENT '上课周阶段' AFTER `merge_classroom_ids`;