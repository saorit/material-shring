package com.example.demo.form;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.annotation.FileRequired;
import com.example.demo.util.PublicPreferenceItems;

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
	@NotEmpty(message = "教材名を入力してください")
	@Size(max = 65, message="20文字内で入力してください")
    private String itemname;
	
	/** 教材内容. */
	@Column(name = "description", length = 300, nullable = false)
	private String description;
	
	/** 公開設定. */
	@NotEmpty(message ="公開設定を選択してください")
    private String publicPreference;

	private List<String> publicUser;
	
	/** 
	 *  公開設定の選択肢の定数を取得するメソッド.
	 *  
	 *  @return 選択肢の定数
	 * */
	public Map<String, String> getPublicPreferenceItems() {
		return PublicPreferenceItems.PUBLICITEMS;
	}

}

