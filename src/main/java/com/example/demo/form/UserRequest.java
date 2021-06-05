package com.example.demo.form;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
   * ユーザー名
   */
  @NotEmpty(message = "ユーザー名を入力してください")
  @Size(max = 20, message = "20文字以内で入力してください")
  @Pattern(regexp = ALPHANUMERIC_REGEXP, message = ALPHANUMERIC_MESSAGE)
  private String username;
  
  /**
   * 名前（表示名）
   */
  @NotEmpty(message = "名前（表示名）を入力してください")
  @Size(max = 20, message = "20文字以内で入力してください")
  private String displayname;
  
  /**
   * 自己紹介
   */
  @Size(max = 255, message = "自己紹介文を255文字以内で入力してください")
  private String profile;
  
  /**
   * パスワード
   */
  @Size(min = 4, max = 255, message="パスワードは4文字以上で入力してください")
  @Pattern(regexp = ALPHANUMERIC_REGEXP, message = ALPHANUMERIC_MESSAGE)
  private String password;
}
