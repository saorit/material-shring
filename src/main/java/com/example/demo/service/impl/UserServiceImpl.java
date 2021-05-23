package com.example.demo.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.SiteUser;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.service.UserService;

/**
 * UserEntityクラスを操作するServiceクラス.
 */
@Service
public class UserServiceImpl implements UserService {

	/**
	 * User(Entity)クラスのリポジトリクラス.
	 */
	@Autowired
	private SiteUserRepository repository;

	/**
	 * PasswordEncoderクラス.
	 */
	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * User(Entity)クラスのデータを全件取得する.
	 *
	 * @return usersテーブルの全件データ
	 */
	public List<SiteUser> findAll() {
		return repository.findAll();
	}

	/**
	 * ユーザー名に紐付くUser(Entity)クラスのデータを1件取得する.
	 *
	 * @param username ユーザー名
	 * @return 該当した1件のデータ
	 */
	public SiteUser findOne(String username) {
		return repository.getOne(username);
	}

	/**
	 * User(Entityクラス)のデータを保存する.
	 *
	 * @param user User(Entityクラス)
	 * @return 保存したUser(Entityクラス)
	 */
	@Transactional
	public SiteUser save(SiteUser user) {

		// パスワード
		String password = user.getPassword();

		// パスワードが空か判定
		if (StringUtils.isBlank(password)) {
			// 空の場合

			// DBからパスワードを取得し、User(Entity)クラスのパスワードに設定
			SiteUser dbData = this.findOne(user.getUsername());
			user.setPassword(dbData.getPassword());

		} else {
			// 空以外の場合

			// パスワードを暗号化し、User(Entity)クラスのパスワードに設定
			user.setPassword(passwordEncoder.encode(password));
		}

		// データベースにUser(Entityクラス)を保存
		return repository.save(user);
	}

	/**
	 * ユーザー名に紐付くUser(Entity)クラスの件数を取得する.
	 *
	 * @param username ユーザー名
	 * @return 件数
	 */
	public long countByUsername(String username) {
		return repository.countByUsername(username);
	}
}