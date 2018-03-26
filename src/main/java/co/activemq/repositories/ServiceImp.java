package co.activemq.repositories;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import co.activemq.pojo.PongMessage;

@Transactional
public class ServiceImp implements IService {
	@Autowired
	private IMessagePongRepository messagePongRepoImp;
	public void insertPongMessage(PongMessage pongmessage) {
		 messagePongRepoImp.insertPongMessage(pongmessage);
	}

}
