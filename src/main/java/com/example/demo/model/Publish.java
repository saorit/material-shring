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

	/** 公開ユーザー. */
	@Column(name = "username", nullable = false)
	private String username;
	
	/** ファイル情報ID. */
	@ManyToOne
	@JoinColumn(name = "file_id", nullable = false)
	private File fileId;

}
