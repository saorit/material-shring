package com.example.demo.form;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.annotation.FileRequired;

import lombok.Getter;
import lombok.Setter;

/**
 * ファイルアップロードのFormクラス.
 */
@Getter
@Setter
public class FileUploadForm implements Serializable {

	/** シリアルバージョンUID. */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", length = 30, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@FileRequired
	private MultipartFile multipartFile;
	
	/** 教材名. */
	@NotEmpty(message = "itemnameを入力してください")
	@Size(max = 35, message="教材名は20文字内で入力してください")
	private String itemname;
	
    @Size(min = 0, max = 300)
    private String description;

}

