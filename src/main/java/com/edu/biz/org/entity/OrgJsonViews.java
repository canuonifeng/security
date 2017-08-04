package com.edu.biz.org.entity;

import com.edu.biz.viewgroup.JsonViews;

public class OrgJsonViews {
	public static interface Public extends JsonViews.Public {
	}

	public static interface CascadeParent extends JsonViews.Cascade {
	}
	
	public static interface AscadeChildren extends JsonViews.Cascade {
	}
	
	public static interface NoCascadeParent extends JsonViews.NoCascade {
	}
	
	public static interface NoCascadeChildren extends JsonViews.NoCascade {
	}
	
	public static interface AscadeChildrenAndParent extends CascadeParent , AscadeChildren {
	}
}
