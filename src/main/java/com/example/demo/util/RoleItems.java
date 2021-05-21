package com.example.demo.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ロールの選択肢の定数.
 */
public class RoleItems {

	public static final Map<String, String> ROLEITEMS;

	static {
		Map<String, String> roleItems = new LinkedHashMap<String, String>();
		roleItems.put("GENERAL", "一般ユーザー");
		roleItems.put("ADMIN", "管理者");
		ROLEITEMS = Collections.unmodifiableMap(roleItems);
	}

}
