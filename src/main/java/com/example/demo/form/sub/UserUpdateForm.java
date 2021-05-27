package com.example.demo.form.sub;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.demo.form.UserBaseForm;
import com.example.demo.model.SiteUser;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザー編集画面のFormクラス.
 */
@Getter
@Setter
public class UserUpdateForm extends UserBaseForm {

	/** シリアルバージョンUID. */
	private static final long serialVersionUID = 1L;

	/** 正規表現(半角英数字). */
	private static final String ALPHANUMERIC_REGEXP = "[a-zA-Z0-9.]*";

	/** 正規表現(半角英数字)のエラーメッセージ. */
	private static final String ALPHANUMERIC_MESSAGE = "半角英字、数字、ピリオドを使用できます";

	@Size(max = 35)
	@Pattern(regexp = ALPHANUMERIC_REGEXP, message = ALPHANUMERIC_MESSAGE)
	private String username;

	@Size(max = 255)
	@Pattern(regexp = ALPHANUMERIC_REGEXP, message = ALPHANUMERIC_MESSAGE)
	private String password;

	/**
	 * コンストラクタ.
	 */
	public UserUpdateForm() {

	}

	/**
	 * コンストラクタ.
	 * 
	 * @param user User(Entityクラス)
	 */
	public UserUpdateForm(SiteUser user) {
		this.setUsername(user.getUsername());
		super.setDisplayname(user.getDisplayname());
		this.setPassword("");
		super.setRole(user.getRole());
	}

	/**
	 * Formクラスの設定内容を文字列で出力する.
	 */
	public String toString() {
		return "username: " + this.getUsername() + ", displayname: " + super.getDisplayname() + ", password: "
				+ this.getPassword() + ", role: " + super.getRole();
	}

	/**
	 * Formの設定内容をUser Entityクラスに変換する.
	 *
	 * @return ユーザー情報(Entityクラス)
	 */
	public SiteUser toEntity() {

		SiteUser user = new SiteUser();
		user.setUsername(this.getUsername());
		user.setDisplayname(super.getDisplayname());
		user.setPassword(this.getPassword());
		user.setRole(super.getRole());

		return user;
	}
}

