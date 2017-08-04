package com.edu.biz.org.entity;

import com.edu.biz.viewgroup.JsonViews;

public class OrgJsonViews {
	public static interface Public extends JsonViews.Public {
	}

	public static interface AscadeParent extends JsonViews.Ascade {
	}
	
	public static interface AscadeChildren extends JsonViews.Ascade {
	}
	
	public static interface NoAscadeParent extends JsonViews.NoAscade {
	}
	
	public static interface NoAscadeChildren extends JsonViews.NoAscade {
	}
	
	public static interface AscadeChildrenAndParent extends AscadeParent , AscadeChildren {
	}
}
