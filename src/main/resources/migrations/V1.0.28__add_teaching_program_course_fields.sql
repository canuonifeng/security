ALTER TABLE `teaching_program_course` ADD `term` INT(10) NULL COMMENT '所属学期' AFTER `theory_period`;
ALTER TABLE `teaching_program_course` ADD `term_code` INT(10) NULL COMMENT '所属学期编码' AFTER `term`;