package com.example.demo.model;

import java.sql.Timestamp;

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
 * usersテーブルのEntityクラス.
 */
@Getter
@Setter
@Entity
@Table(name = "files")
public class File {

	/**
	 * ID. プライマリーキー.
	 */
	@Id
	@Column(name = "id", length = 30, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/** ファイル名. */
	@Column(name = "name", length = 60, nullable = false)
	private String name;

	/** ファイルデータ. */
	@Column(name = "data", nullable = false, columnDefinition = "mediumblob")
	private byte[] data;

	/** イメージフラグ. */
	@Column(name = "is_image_extension", nullable = false)
	private boolean isImageExtension;

	/** 登録日時. */
	@Column(name = "create_date", nullable = false)
	private Timestamp createDate;

	/** 登録ユーザー名. */
	@ManyToOne
	@JoinColumn(name = "create_username", nullable = false)
	private SiteUser createUser;

	/** 更新日時. */
	@Column(name = "update_date", nullable = false)
	private Timestamp updateDate;

	/** 更新ユーザー名. */
	@ManyToOne
	@JoinColumn(name = "update_username", nullable = false)
	private SiteUser updateUser;

}

