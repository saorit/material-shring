package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.example.demo.validator.UniqueLogin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @Size(min = 2, max = 20, message="ユーザー名は2文字から20文字内で入力してください")
    @UniqueLogin
    private String username;

    @Size(min = 4, max = 255, message="パスワードは4文字以上で入力してください")
    private String password;
    
    /** 表示名. */
    @Size(min = 2, max = 20, message="名前は2文字から20文字内で入力してください")
	private String displayname;
    
    /** 自己紹介. */
    @Column(name = "profile", length = 200, nullable = true)
	private String profile;
    
    /** プロフィール画像. */
    @Column(name = "photos", length = 64, nullable = true)
	private String photos;
    
    /** ロール. */
	@Column(name = "role", length = 120, nullable = true)
	private String role;

    private boolean admin;
    private boolean active = true;
}

