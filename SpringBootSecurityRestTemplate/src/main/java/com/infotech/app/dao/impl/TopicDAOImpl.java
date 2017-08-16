package com.infotech.app.dao.impl;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.infotech.app.dao.TopicDAO;
import com.infotech.app.entities.Topic;

@Transactional
@Repository
public class TopicDAOImpl implements TopicDAO {
	
	@PersistenceContext	
	private EntityManager entityManager;	
	
	@Override
	public Topic getTopicById(int topicId) {
		return entityManager.find(Topic.class, topicId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Topic> getAllTopics() {
		String HQL = "FROM Topic as t ORDER BY t.topicId";
		return (List<Topic>) entityManager.createQuery(HQL).getResultList();
	}	
	
	@Override
	public void addTopic(Topic topic) {
		entityManager.persist(topic);
	}
	
	@Override
	public void updateTopic(Topic topic) {
		Topic topicFromDb = getTopicById(topic.getTopicId());
		if(topicFromDb != null){
			topicFromDb.setTitle(topic.getTitle());
			topicFromDb.setCategory(topic.getCategory());
			entityManager.flush();
		}else{
			System.out.println("Topic doesn't exist which u want update.");
		}
	}
		
	@Override
	public void deleteTopic(int topicId) {
		Topic topic = getTopicById(topicId);
		if(topic != null)
		entityManager.remove(topic);
		else
			System.out.println("Topic doesn't exist which u want delete.");	
	}
	@Override
	public boolean topicExists(String title, String category) {
		String hql = "FROM Topic as t WHERE t.title = ? and t.category = ?";
		int count = entityManager.createQuery(hql).setParameter(1, title)
		              .setParameter(2, category).getResultList().size();
		return count > 0 ? true : false;
	}
}
