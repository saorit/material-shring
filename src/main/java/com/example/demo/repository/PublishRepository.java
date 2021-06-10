package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.File;
import com.example.demo.model.Publish;
import com.example.demo.model.SiteUser;

/**
 * Publish(Entity)クラスのリポジトリクラス.
 */
@Repository
public interface PublishRepository extends JpaRepository<Publish, String> {
	/**
	 * ファイルに紐付くPublish(Entity)クラスを取得.
	 *
	 * @param file ファイル
	 * @return 取得したデータが格納されたPublish(Entity)クラス
	 */
	@Query("SELECT p FROM Publish p WHERE p.fileId = :file")
	public List<Publish> findByFileId(@Param("file") File file);
	
	/**
	 * ファイルに紐付くPublish(Entity)クラスを削除.
	 *
	 * @param file ファイル
	 * @return 取得したデータが格納されたPublish(Entity)クラスの削除
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Publish p WHERE p.fileId = :file")
	public Integer deleteByFileId(@Param("file") File file);
	
	/**
	 * ユーザーが閲覧可能なFile(Entity)クラスを取得.
	 *
	 * @param username ユーザー名
	 * @param pageable Pageable
	 * @return 取得したデータが格納されたFile(Entity)クラス
	 */
	@Query("SELECT f FROM File f LEFT OUTER JOIN Publish p ON p.fileId = f.id WHERE f.publicPreference = 'PUBLIC' OR (f.publicPreference = 'LIMITED' AND p.username = :username)")
	public Page<File> findViewableFiles(@Param("username") String username, Pageable pageable);

	/**
	 * ユーザーが閲覧可能なFile(Entity)クラスを取得.
	 *
	 * @param username ユーザー名
	 * @param createUser 登録ユーザー名
	 * @return 取得したデータが格納されたFile(Entity)クラス
	 */
	@Query("SELECT f FROM File f LEFT OUTER JOIN Publish p ON p.fileId = f.id WHERE f.createUser = :createUser AND ( f.publicPreference = 'PUBLIC' OR (f.publicPreference = 'LIMITED' AND p.username = :username) )")
	public List<File> findViewableFilesByUser(@Param("username") String username, @Param("createUser") SiteUser createUser);

}

