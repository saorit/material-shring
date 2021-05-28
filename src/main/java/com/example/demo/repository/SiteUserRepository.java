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
	 * ユーザー名に紐付くUser(Entity)クラスの件数を取得.
	 *
	 * @param username ユーザー名
	 * @return 件数
	 */
	@Query("select count(e) from SiteUser e WHERE e.username = :username")
	long countByUsername(@Param("username") String username);

	@Query("select e from SiteUser e WHERE e.id = :id")
	SiteUser getById(@Param("id") Long id);
}