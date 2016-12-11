package app.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="message")
public class Message {

	@Id
	private String id;
	private String content; 
	private List<String> tags;
	private String idUser;
	private List<User> destinataires;
	
	private User user;
	
	public Message(String user, String content, List<String> tags, List<User> destinataires) {
		this.idUser  = user;
		this.content = content;
		this.tags = tags;
		this.setDestinataires(destinataires);
	}


	public String getId() {
		return id;
	}

	public void setId(String messageId) {
		this.id = messageId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public List<User> getDestinataires() {
		return destinataires;
	}


	public void setDestinataires(List<User> destinataires) {
		this.destinataires = destinataires;
	}
	
	
	
	 
}
