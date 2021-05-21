package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import com.example.demo.validator.UniqueLogin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 20)
    @UniqueLogin
    private String username;

    @Size(min = 4, max = 255)
    private String password;
    
	
	/** ロール. */
	@Column(name = "role", length = 120, nullable = false)
	private String role;


    private boolean admin;
    private boolean active = true;
}

