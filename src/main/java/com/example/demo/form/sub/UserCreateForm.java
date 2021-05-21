package com.example.demo.form.sub;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.demo.annotation.CustomCheck;
import com.example.demo.form.UserBaseForm;
import com.example.demo.model.SiteUser;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザー新規登録画面のFormクラス.
 */
@Getter
@Setter
public class UserCreateForm extends UserBaseForm {

	/** シリアルバージョンUID. */
	private static final long serialVersionUID = 1L;

	/** 正規表現(半角英数字). */
	private static final String ALPHANUMERIC_REGEXP = "[a-zA-Z0-9.]*";

	/** 正規表現(半角英数字)のエラーメッセージ. */
	private static final String ALPHANUMERIC_MESSAGE = "半角英字、数字、ピリオドを使用できます";

	@NotBlank
	@Size(max = 30)
	@Pattern(regexp = ALPHANUMERIC_REGEXP, message = ALPHANUMERIC_MESSAGE)
	@CustomCheck(uniqueUsername = "username", message = "既に登録されています")
	private String username;

	@NotBlank
	@Size(max = 255)
	@Pattern(regexp = ALPHANUMERIC_REGEXP, message = ALPHANUMERIC_MESSAGE)
	private String password;

	/**
	 * Formクラスの設定内容を文字列で出力する.
	 */
	@Override
	public String toString() {
		return "SiteUser(username: " + this.getUsername() + ", displayName: " + super.getDisplayName() + ", password: "
				+ this.getPassword() + ", role: " + super.getRole() + ", isEnabled: " + super.isEnabled() + ")";
	}

	/**
	 * Formの設定内容をUser Entityクラスに変換する.
	 *
	 * @return ユーザー情報(Entityクラス)
	 */
	public SiteUser toEntity() {

		SiteUser user = new SiteUser();
		user.setUsername(this.getUsername());
		user.setPassword(this.getPassword());
		user.setRole(super.getRole());

		return user;
	}
}
