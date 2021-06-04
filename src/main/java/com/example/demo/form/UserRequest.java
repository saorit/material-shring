package com.example.demo.form;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * ユーザー情報 リクエストデータ
 */
@Getter
@Setter
@Data
public class UserRequest implements Serializable {
	
	/** シリアルバージョンUID. */
	private static final long serialVersionUID = 1L;

	/** 正規表現(半角英数字). */
	private static final String ALPHANUMERIC_REGEXP = "[a-zA-Z0-9.]*";

	/** 正規表現(半角英数字)のエラーメッセージ. */
	private static final String ALPHANUMERIC_MESSAGE = "半角英字、数字、ピリオドを使用できます";
	
	
  /**
   * 名前
   */
  @NotEmpty(message = "usernameを入力してください")
  @Size(max = 35, message = "35文字以内で入力してください")
  @Pattern(regexp = ALPHANUMERIC_REGEXP, message = ALPHANUMERIC_MESSAGE)
  private String username;
  /**
   * 住所
   */
  @NotEmpty(message = "displaynameを入力してください")
  @Size(max = 35, message = "35文字以内で入力してください")
  private String displayname;
  /**
   * 住所
   */
  @Size(max = 255, message = "255文字以内で入力してください")
  private String profile;
  
  @Size(min = 4, max = 255, message="パスワードは4文字以上で入力してください")
  private String password;
}
