package app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.model.Message;

@RepositoryRestResource(collectionResourceRel = "message", path = "message")
public interface MessageRepository extends MongoRepository<Message, String>{

	List<Message> findByIdUser(String idUser);
	
}
