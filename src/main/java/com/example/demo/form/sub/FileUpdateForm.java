package com.example.demo.form.sub;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.annotation.FileRequired;
import com.example.demo.model.SiteUser;

import lombok.Getter;
import lombok.Setter;

/**
 * ファイルupdateのFormクラス.
 */
@Getter
@Setter
public class FileUpdateForm implements Serializable {

	/** シリアルバージョンUID. */
	private static final long serialVersionUID = 1L;
	
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
	@Column(name = "is_image_extension", nullable = true)
	private boolean isImageExtension;
	
	/** 更新日時. */
	@Column(name = "update_date", nullable = true)
	private Timestamp updateDate;
	
	/** 更新ユーザー名. */
	@ManyToOne
	@JoinColumn(name = "update_username", nullable = true)
	private SiteUser updateUser;
	
	/** アップロードファイル. */
	@FileRequired
	private MultipartFile multipartFile;
	
	/** 教材名. */
	@NotEmpty(message = "教材名を入力してください")
	@Size(max = 65, message="20文字内で入力してください")
    private String itemname;
	
	/** 教材内容. */
	@Column(name = "description", length = 300, nullable = false)
	private String description;

}