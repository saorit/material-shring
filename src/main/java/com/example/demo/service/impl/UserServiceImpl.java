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
 * SiteUserEntityクラスを操作するServiceクラス.
 */
@Service
public class UserServiceImpl implements UserService {

	/**
	 * SiteUser(Entity)クラスのリポジトリクラス.
	 */
	@Autowired
	private SiteUserRepository repository;

	/**
	 * PasswordEncoderクラス.
	 */
	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * SiteUser(Entity)クラスのデータを全件取得する.
	 * @return usersテーブルの全件データ
	 */
	public List<SiteUser> findAll() {
		return repository.findAll();
	}

	/**
	 * ユーザーIdに紐付くSiteUser(Entity)クラスのデータを1件取得する.
	 * @return 該当した1件のデータ
	 */
	public SiteUser findOne(Integer id) {
	  return repository.getOne(id);
	    }
	
	/**
	 * ユーザー名に紐付くSiteUser(Entity)クラスのデータを1件取得する.
	 * @param username ユーザー名
	 * @return 該当した1件のデータ
	 */
	public SiteUser findOneUsername(String username){
		return repository.getOneUsername(username);
	}

	/**
	 * User(Entityクラス)のデータを保存する.
	 * @return 保存したSiteUser(Entityクラス)
	 */
	@Transactional
	public SiteUser save(SiteUser user) {
		// データベースにSiteUser(Entityクラス)を保存
		return repository.save(user);
	}

	/**
	 * ユーザー名に紐付くUser(Entity)クラスの件数を取得する.
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
        user.setPassword(userUpdateRequest.getPassword());
        repository.save(user);
    }
}