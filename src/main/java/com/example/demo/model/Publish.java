package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * publishテーブルのEntityクラス.
 */
@Getter
@Setter
@Entity
@Table(name = "publish")
public class Publish {
	
	/**
	 * ID. プライマリーキー.
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	/** 教材名. */
	@JoinColumn(name = "publish_itemname", nullable = false)
	private String publishItemname;
	
    /** 公開範囲. */
	@Column(name = "publicPreference", nullable = false)
    private String publicPreference;

	/** 登録ユーザー. */
	@ManyToOne
	@JoinColumn(name = "make_user", nullable = false)
	private SiteUser makeUser;

}
