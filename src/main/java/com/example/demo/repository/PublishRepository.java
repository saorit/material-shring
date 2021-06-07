package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.File;
import com.example.demo.model.Publish;

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
}

