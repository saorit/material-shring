package com.example.demo.annotation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.annotation.FileRequired;

public class FileRequiredValidator implements ConstraintValidator<FileRequired, MultipartFile> {

	/**
	 * 初期化処理.
	 * 
	 * @param annotation FileRequiredの情報
	 */
	@Override
	public void initialize(FileRequired constraint) {
		// 何もしない
	}

	/**
	 * バリデーション.
	 * 
	 * @param value   入力値
	 * @param context バリデーション情報
	 * @return 検証結果(trueの場合OK、falseの場合NG)
	 */
	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		// ファイル情報が存在するか判定
		if (multipartFile != null && !multipartFile.getOriginalFilename().isEmpty()) {
			// 存在する場合
			// 検証はOKとして扱う
			return true;

		} else {
			// 存在しない場合
			// 検証はNGとして扱う
			return false;

		}

	}
}

