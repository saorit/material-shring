package com.example.demo.form.sub;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * ファイルcountのFormクラス.
 */
@Getter
@Setter
public class FileCountForm implements Serializable {
	
	/** シリアルバージョンUID. */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id",nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/** 教材数. */
	@Column(name = "fileCount")
	private String fileCount;

}
