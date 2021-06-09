package com.example.demo.service;

import java.util.List;

import com.example.demo.model.File;
import com.example.demo.model.Publish;

/**
 * Publish Entityクラスを操作するServiceクラスのインターフェース.
 */
public interface PublishService {
	/**
	 * Publish(Entity)クラスのデータを保存する.
	 * @param publish Publish(Entity)クラス
	 * @return 保存したPublish(Entity)クラス
	 */
	public Publish save(Publish publish);

	/**
	 * ファイルに紐付くPublish(Entity)クラスを取得.
	 * @param file ファイル
	 * @return 取得したデータが格納されたPublish(Entity)クラス
	 */
	public List<Publish> findByFile(File file);
	
	/**
	 * ファイルに紐付くPublish(Entity)クラスの削除.
	 *
	 * @param file ファイル
	 * @return 取得したデータが格納されたPublish(Entity)クラスの削除
	 */
	public Integer deleteByFileId(File file);
	
}
