package com.edu.biz.security.entity;

import com.edu.biz.viewgroup.JsonViews;

public class OrgJsonViews {
	public static class Public extends com.edu.biz.viewgroup.JsonViews.Public {
	}

	public static class AscadeParent extends JsonViews.Ascade {
	}
	
	public static class NoAscadeParent extends JsonViews.NoAscade {
	}
	
	public static class AscadeChildren extends JsonViews.Ascade {
	}
	
	public static class NoAscadeChildren extends JsonViews.NoAscade {
	}
}
