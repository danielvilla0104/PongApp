package co.activemq.listener;

import java.util.Date;

import javax.jms.JMSException;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import co.activemq.pojo.PongMessage;
import co.activemq.repositories.IMessagePongRepository;

@EnableAutoConfiguration
@Controller
public class Listener {
	
	static final String PING_MESSAGE = "PING_MESSAGE";
	static final String PONG_MESSAGE = "PONG_MESSAGE";
	
	@Autowired
	private IMessagePongRepository repository;
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	@JmsListener(destination = "inbound.topic", containerFactory = "jmsListenerContainerFactory")
	public String receiveMessage(final ActiveMQTextMessage msg) throws JMSException, InterruptedException {
		
		
		//System.out.println("el pong message Id es " + msg.getCorrelationId());
		
		String response = null;
		PongMessage ping = new PongMessage(msg.getText(), new Date(), PING_MESSAGE);
		Thread.sleep(2000L);
		
		repository.insertPongMessage(ping);
		
		PongMessage pong = new PongMessage(msg.getText(), new Date(), PONG_MESSAGE);
		
		repository.insertPongMessage(pong);
		pong.setCorrelationId(msg.getCorrelationId());
		jmsTemplate.convertAndSend("outbound.topic", pong);
		
		return response;
	}
	
	@RequestMapping(value = "/allmessages", method = RequestMethod.GET)
	public String getAllMessages(Model model){
		model.addAttribute("messages", repository.findAllPongMessages());
		return "listall";
		
	}
}
