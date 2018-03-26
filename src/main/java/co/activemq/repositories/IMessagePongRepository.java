package co.activemq.repositories;

import java.util.List;

import co.activemq.pojo.PongMessage;

public interface IMessagePongRepository {
	public void insertPongMessage(PongMessage pongmessage);
	public List <PongMessage> findAllPongMessages();
}
