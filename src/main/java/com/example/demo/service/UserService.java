package com.example.demo.service;

import java.util.List;

import com.example.demo.form.sub.UserUpdateRequest;
import com.example.demo.model.SiteUser;

/**
 * UserEntityクラスを操作するServiceクラスのインターフェース.
 */
public interface UserService {

	/**
	 * User(Entity)クラスのデータを全件取得する.
	 *
	 * @return usersテーブルの全件データ
	 */
	public List<SiteUser> findAll();

	/**
	 * ユーザー名に紐付くUser(Entity)クラスのデータを1件取得する.
	 *
	 * @param id ユーザー名
	 * @return 該当した1件のデータ
	 */
	public SiteUser findOne(Integer id);
	
	/**
	 * ユーザー名に紐付くUser(Entity)クラスのデータを1件取得する.
	 *
	 * @param username ユーザー名
	 * @return 該当した1件のデータ
	 */
	public SiteUser findOneUsername(String username);

	/**
	 * User(Entity)クラスのデータを保存する.
	 *
	 * @param user User(Entity)クラス
	 * @return 保存したUser(Entity)クラス
	 */
	public SiteUser save(SiteUser user);
	
	/**
	 * ユーザー名に紐付くUser(Entity)クラスの件数を取得する.一旦非表示
	 *
	 * @param username ユーザー名
	 * @return 件数
	 * */
	 
	public long countByUsername(String username);
	
	 /**
     * ユーザー情報 更新
     * @param user ユーザー情報
     */
    public void update(UserUpdateRequest userUpdateRequest);

}