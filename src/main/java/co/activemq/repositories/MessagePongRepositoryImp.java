package co.activemq.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import co.activemq.pojo.PongMessage;

@Transactional
@Repository
public class MessagePongRepositoryImp implements IMessagePongRepository{
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void insertPongMessage(PongMessage pongmessage) {
		entityManager.persist(pongmessage);
	}

	@Override
	public List<PongMessage> findAllPongMessages() {
		return entityManager.createQuery("from PongMessage").getResultList();
	}

}
