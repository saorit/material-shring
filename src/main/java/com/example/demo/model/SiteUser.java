package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.domain.Page;

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
   
    @NotEmpty(message = "usernameを入力してください")
    @Size(max = 35, message = "35文字以内で入力してください")
    @UniqueLogin
    private String username;

    @Size(min = 4, max = 255, message="パスワードは4文字以上で入力してください")
    private String password;
    
    /** 表示名. */
    @NotEmpty(message = "displaynameを入力してください")
    @Size(max = 35, message = "35文字以内で入力してください")
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
	
	/** 有効フラグ. */
	@Column(name = "is_enabled", nullable = true)
	private boolean isEnabled;

    private boolean admin;
    private boolean active = true;
}

