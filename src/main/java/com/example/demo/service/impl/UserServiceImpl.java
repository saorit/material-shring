package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.form.sub.UserUpdateRequest;
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
	public SiteUser findOne(Integer id) {
	  return repository.getOne(id);
	    }
	
	/**
	 * ユーザー名に紐付くUser(Entity)クラスのデータを1件取得する.
	 *
	 * @param username ユーザー名
	 * @return 該当した1件のデータ
	 */
	public SiteUser findOneUsername(String username){
		return repository.getOneUsername(username);
	}

	/**
	 * User(Entityクラス)のデータを保存する.
	 *
	 * @param user User(Entityクラス)
	 * @return 保存したUser(Entityクラス)
	 */
	@Transactional
	public SiteUser save(SiteUser user) {
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
	
	 /**
     * ユーザー情報 更新
     * @param user ユーザー情報
     */
    public void update(UserUpdateRequest userUpdateRequest) {
        SiteUser user = findOne(userUpdateRequest.getId());
        user.setUsername(userUpdateRequest.getUsername());
        user.setDisplayname(userUpdateRequest.getDisplayname());
        user.setProfile(userUpdateRequest.getProfile());
        repository.save(user);
    }
}