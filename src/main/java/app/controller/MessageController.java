package app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Message;
import app.model.User;
import app.repository.MessageRepository;
import app.repository.UserRepository;

@RestController
@RequestMapping("/messages")
public class MessageController {

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createMessage(@RequestBody Map<String, Object> messageMap) {
		
		List<String> tags = traitementTagsMessage(messageMap.get("content").toString());
		List<User> destinateurs = traitementDestinatairesMessage(messageMap.get("content").toString());
		
		Message msg = new Message(messageMap.get("iduser").toString(), messageMap.get("content").toString(),
				tags, destinateurs);

		messageRepository.save(msg);		
		return getAllMessages();
	}


	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public Map<String, Object> getMessageByIdUser(@PathVariable("userId") String userId) {
		List<Message> messages = messageRepository.findByIdUser(userId);
		for (Message message : messages) {
			User u = userRepository.findOne(message.getIdUser());
			message.setUser(u);
		}
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "messages retrieved");
		response.put("messages", messages);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getAllMessages() {
		List<Message> messages = messageRepository.findAll();
		for (Message message : messages) {
			User u = userRepository.findOne(message.getIdUser());
			message.setUser(u);
		}
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("totalusers", messages.size());
		response.put("messages", messages);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/hashtag/{tag}")
	public List<Message> getAllMessagesWithTag(@PathVariable("tag") String tag){
		List<Message> messages  = messageRepository.findAll();
		List<Message> messageswithtag = new ArrayList<>();
		for (Message message : messages) {
			if(message.getContent().contains(tag)){
				User u = userRepository.findOne(message.getIdUser());
				message.setUser(u);
				messageswithtag.add(message);
			}
		}
		return messageswithtag;
		
	}


	private List<String> traitementTagsMessage(String content) {
		List<String> tags = new ArrayList<>();
		String [] tabSplit = content.split("\\s+");
		for (String string : tabSplit) {
			if(string.startsWith("#"))
				tags.add(string);
		}
		
		return tags;
	}
	
	private List<User> traitementDestinatairesMessage(String content) {
		List<User> destinataire = new ArrayList<>();
		String [] tabSplit = content.split("\\s+");
		for (String string : tabSplit) {
			if(string.startsWith("@")){
				User u = userRepository.findByUsername(string.substring(1));
				if(u != null)
					destinataire.add(u);
			}
		}
		
		return destinataire;
	}


	
	/////////////////////////////////////////////////////////////
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/{messageId}")
	public Message getMessageDetails(@PathVariable("messageId") String messageId) {
		return messageRepository.findOne(messageId);
	}

	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{messageId}")
	public Map<String, String> deleteMessage(@PathVariable("messageId") String messageId) {
		messageRepository.delete(messageId);
		Map<String, String> response = new HashMap<String, String>();
		response.put("message", "message deleted successfully");

		return response;
	}
}


