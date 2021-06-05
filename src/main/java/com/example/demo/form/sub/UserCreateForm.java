package com.example.demo.form.sub;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@NotBlank(message = "ユーザー名を入力してください")
	@Size(max = 20,  message = "20文字以内で入力してください")
	@Pattern(regexp = ALPHANUMERIC_REGEXP, message = ALPHANUMERIC_MESSAGE)
	@CustomCheck(uniqueUsername = "username", message = "既に登録されています")
	private String username;

	@NotBlank
	@Size(min = 4, max = 255, message="パスワードは4文字以上で入力してください")
	@Pattern(regexp = ALPHANUMERIC_REGEXP, message = ALPHANUMERIC_MESSAGE)
	private String password;

	/**
	 * Formクラスの設定内容を文字列で出力する.
	 */
	@Override
	public String toString() {
		return "User(id: " + this.getId() + ", + username: " + this.getUsername() + ", displayname: " + super.getDisplayname() + ", profile: " + super.getProfile() + ", password: "
				+ this.getPassword() + ", role: " + super.getRole() + ", isEnabled: " + super.isEnabled() + ")";
	}

	/**
	 * Formの設定内容をUser Entityクラスに変換する.
	 *
	 * @return ユーザー情報(Entityクラス)
	 */
	public SiteUser toEntity() {
		SiteUser user = new SiteUser();
		user.setId(this.getId());
		user.setUsername(this.getUsername());
		user.setDisplayname(super.getDisplayname());
		user.setProfile(super.getProfile());
		user.setPassword(this.getPassword());
		user.setRole(super.getRole());
		user.setEnabled(super.isEnabled());

		return user;
	}

}
