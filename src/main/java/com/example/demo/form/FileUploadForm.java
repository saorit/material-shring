package com.example.demo.form;

import java.io.Serializable;

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

	@FileRequired
	private MultipartFile multipartFile;
	
	@Size(min = 1, max = 65, message="教材名は1文字以上20文字内で入力してください")
    private String itemname;
	
    @Size(min = 0, max = 300)
    private String description;

}

