package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.File;
import com.example.demo.model.SiteUser;

import java.util.List;

/**
 * File Entityクラスを操作するServiceクラスのインターフェース.
 */
public interface FileService {

	/**
	 * File(Entity)クラスの1ページ分のデータを取得する.
	 *
	 * @return filesテーブルの1ページ分のデータ
	 */
	public Page<File> findAll(Pageable pageable);
	
	/**
	 * userIDに紐付くFile(Entity)クラスのデータを取得する.
	 *
	 * @param id userID
	 * @return 該当したfileのデータ
	 */
	public List<File> findMyFile(Integer userId);
	

	/**
	 * ファイルIDに紐付くFile(Entity)クラスのデータを1件取得する.
	 *
	 * @param id ファイルID
	 * @return 該当した1件のデータ
	 */
	public File findOne(int id);

	/**
	 * File(Entity)クラスのデータを保存する.
	 *
	 * @param file File(Entity)クラス
	 * @return 保存したFile(Entity)クラス
	 */
	public File save(File file);

	/**
	 * ファイルIDに紐付くFile(Entity)クラスのデータを削除する.
	 *
	 * @param id ファイルID
	 */
	public void delete(int id);
}
