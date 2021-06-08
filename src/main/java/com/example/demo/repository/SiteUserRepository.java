package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.SiteUser;

/**
 * User(Entity)クラスのリポジトリクラス.
 */
@Repository
public interface SiteUserRepository extends JpaRepository<SiteUser, Integer> {
	SiteUser findByUsername(String username);
    boolean existsByUsername(String username);
    
    /**
	 * usernameに紐付くFile(Entity)クラスを取得.
	 *
	 * @param username userID
	 * @return 取得したデータが格納されたSiteUser(Entity)クラス
	 */
	@Query("SELECT f FROM SiteUser f WHERE f.username = :username")
	SiteUser getOneUsername(@Param("username") String username);
	
	/**
	 * IDに紐付くFile(Entity)クラスを取得.
	 *
	 * @param ID
	 * @return 取得したデータが格納されたSiteUser(Entity)クラス
	 */
	@Query("select e from SiteUser e WHERE e.id = :id")
	SiteUser getById(@Param("id") Long id);
 
    /**
	 * ユーザー名に紐付くSiteUser(Entity)クラスの件数を取得.
	 *
	 * @param username ユーザー名
	 * @return 件数
	 */
	@Query("select count(e) from SiteUser e WHERE e.username = :username")
	long countByUsername(@Param("username") String username);
	
}