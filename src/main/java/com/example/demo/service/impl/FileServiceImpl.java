package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.File;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.FileRepository;
import com.example.demo.service.FileService;

import java.util.List;

/**
 * File Entityクラスを操作するServiceクラス.
 */
@Service
public class FileServiceImpl implements FileService {

	/**
	 * File(Entity)クラスのリポジトリクラス.
	 */
	@Autowired
	private FileRepository repository;

	/**
	 * File(Entity)クラスの1ページ分のデータを取得する.
	 *
	 * @return filesテーブルの1ページ分のデータ
	 */
	public Page<File> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	/**
	 * userIDに紐付くFile(Entity)クラスのデータを取得する.
	 *
	 * @return 該当したfileのデータ
	 */
	public List<File> findMyFile(SiteUser siteUser) {
		return repository.getMyFile(siteUser);
	}
	


	/**
	 * IDに紐付くFile(Entity)クラスのデータを1件取得する.
	 *
	 * @param id ファイルID
	 * @return 該当した1件のデータ
	 */
	public File findOne(int id) {
		return repository.getOne(id);
	}

	/**
	 * File(Entity)クラスのデータを保存する.
	 *
	 * @param file File(Entity)クラス
	 * @return 保存したFile(Entity)クラス
	 */
	@Transactional
	public File save(File file) {
		return repository.save(file);
	}

	/**
	 * IDに紐付くFile(Entity)クラスのデータを削除する.
	 *
	 * @param id ファイルID
	 */
	@Transactional
	public void delete(int id) {
		repository.deleteById(id);
	}
}

