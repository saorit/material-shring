package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.SiteUser;

public interface SiteUserRepository extends JpaRepository<SiteUser, String> {
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
}
