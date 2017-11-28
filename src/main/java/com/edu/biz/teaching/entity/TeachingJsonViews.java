package com.edu.biz.teaching.entity;

import com.edu.biz.teachingres.entity.TeachingresJsonViews.CascadeTeacher;
import com.edu.biz.viewgroup.JsonViews;

public class TeachingJsonViews {

	public static interface CascadeStudent extends JsonViews.Cascade {
	}
	public static interface CascadeGradedCourse extends JsonViews.Cascade {
	}
	public static interface CascadeSelectCourse extends JsonViews.Cascade {
	}
	public static interface CascadeSelectCourseClass extends JsonViews.Cascade {
	}
	public static interface CascadeSelectCourseClassAndTeacher extends CascadeTeacher , CascadeSelectCourseClass {
	}
}
