package com.example.demo.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ロールの選択肢の定数.
 */
public class PublicPreferenceItems {

	public static final Map<String, String> PUBLICITEMS;

	static {
		Map<String, String> publicPreferenceItems = new LinkedHashMap<String, String>();
		publicPreferenceItems.put("PUBLIC", "全ての人が閲覧可能");
		publicPreferenceItems.put("LIMITED", "一部の人が閲覧可能");
		PUBLICITEMS = Collections.unmodifiableMap(publicPreferenceItems);
	}

}

