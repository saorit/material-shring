package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Publish;
import com.example.demo.repository.PublishRepository;
import com.example.demo.service.PublishService;

@Service
public class PublishServiceImpl implements PublishService {
	
	/**
	 * Publish(Entity)クラスのリポジトリクラス.
	 */
	@Autowired
	private PublishRepository repository;
	
	/**
	 * Publish(Entity)クラスのデータを保存する.
	 * @param publish Publish(Entity)クラス
	 * @return 保存したPublish(Entity)クラス
	 */
	@Transactional
	public Publish save(Publish publish) {
		return repository.save(publish);
		}
	}
