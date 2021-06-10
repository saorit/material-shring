package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.example.demo.validator.UniqueLogin;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@Entity
@Table(name = "users")
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
   
    @NotEmpty(message = "ユーザー名を入力してください")
    @Size(max = 20, message = "20文字以内で入力してください")
    @UniqueLogin
    private String username;

    @Size(min = 4, max = 255, message="パスワードは4文字以上で入力してください")
    private String password;
    
    /** 表示名. */
    @NotEmpty(message = "名前を入力してください")
    @Size(max = 20, message = "20文字以内で入力してください")
	private String displayname;
    
    /** 自己紹介. */
    @Size(max = 255, message = "255文字以内で入力してください")
	private String profile;
    
    /** プロフィール画像. */
    @Column(name = "photos", length = 64, nullable = true)
	private String photos;
    
    /** ロール. */
	@Column(name = "role", length = 120, nullable = true)
	private String role;
	
	/** 有効フラグ. */
	@Column(name = "is_enabled", nullable = true)
	private boolean isEnabled;
	
	/** 教材数. */
	@Column(name = "fileCount", nullable = true)
	private Long fileCount;

    private boolean admin;
    private boolean active = true;
}

