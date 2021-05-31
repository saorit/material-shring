package com.example.demo.form;

import java.io.Serializable;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.demo.util.RoleItems;
import com.example.demo.util.EnabledItems;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザーEntityクラスののFormクラス.
 */
@Getter
@Setter
public class UserBaseForm implements Serializable {

	/** シリアルバージョンUID. */
	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(max = 30)
	private String displayname;
	
	@Size(max = 255)
	private String profile;

	private String role;

	private boolean isEnabled;

	/**
	 * ロールの選択肢の定数を取得するメソッド.
	 * 
	 * @return 選択肢の定数
	 */
	public Map<String, String> getRoleItems() {
		return RoleItems.ROLEITEMS;
	}

	/**
	 * 有効/無効の選択肢の定数を取得するメソッド.
	 * 
	 * @return 選択肢の定数
	 */
	public Map<Boolean, String> getEnabledItems() {
		return EnabledItems.ENABLEDITEMS;
	}

}
