package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.File;
import com.example.demo.model.SiteUser;

import java.util.List;


/**
 * File(Entity)クラスのリポジトリクラス.
 */
@Repository
public interface FileRepository extends JpaRepository<File, String> {

	Page<File> findAll(Pageable pageable);
	
	/**
	 * userIDに紐付くFile(Entity)クラスを取得.
	 *
	 * @param id ファイルID
	 * @return 取得したデータが格納されたFile(Entity)クラス
	 */
	@Query("SELECT f FROM File f WHERE f.createUser = :siteUser")
	List<File> getMyFile(@Param("siteUser") SiteUser siteUser);

	/**
	 * IDに紐付くFile(Entity)クラスを取得.
	 * @param id ファイルID
	 * @return 取得したデータが格納されたFile(Entity)クラス
	 */
	@Query("SELECT f FROM File f INNER JOIN f.createUser INNER JOIN f.updateUser WHERE f.id = :id")
	File getOne(@Param("id") int id);

	/**
	 * ファイルIDに紐付くFile(Entity)クラスを削除.
	 * @param id ファイルID
	 */
	@Query("DELETE FROM File f WHERE f.id = :id")
	@Transactional
	@Modifying
	void deleteById(@Param("id") int id);
	
	/**
	 * createUserの教材件数を取得.
	 * @return 件数
	 */
	@Query("select count(f) from File f WHERE f.createUser = :createUser")
	long countFile(@Param("createUser") String createUser);
}

