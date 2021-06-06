package com.example.demo.service;

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

}

