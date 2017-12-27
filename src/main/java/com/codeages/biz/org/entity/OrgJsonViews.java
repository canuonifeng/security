package com.codeages.biz.org.entity;

import com.codeages.biz.viewgroup.JsonViews;

public class OrgJsonViews {
	public static interface Public extends JsonViews.Public {
	}

	public static interface CascadeParent extends JsonViews.Cascade {
	}
	
	public static interface CascadeChildren extends JsonViews.Cascade {
	}
	
	public static interface NoCascadeParent extends JsonViews.NoCascade {
	}
	
	public static interface NoCascadeChildren extends JsonViews.NoCascade {
	}
	
	
	public static interface CascadeChildrenAndParent extends CascadeParent , CascadeChildren {
	}
}
