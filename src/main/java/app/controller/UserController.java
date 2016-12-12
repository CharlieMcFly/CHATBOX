package app.controller;

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

import app.model.User;
import app.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createUser(@RequestBody Map<String, Object> userMap) {
		User user = new User(userMap.get("username").toString(), userMap.get("password").toString());

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "User created successfully");
		response.put("user", userRepository.save(user));
		return response;
	}

	@RequestMapping(value = "/connexion", method = RequestMethod.POST)
	public Map<String, Object> connexionUser(@RequestBody Map<String, Object> userMap) {
		User user = userRepository.findByUsername(userMap.get("username").toString());
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		if (user == null)
			return createUser(userMap);
		else{ 
			if(user.getPassword().equals(userMap.get("password"))){
				response.put("message", "user connected");
				response.put("user", user);
				return response;
			}
			else{
				response.put("message", "invalid password");
				return response;
			}
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{userId}")
	public User getuserDetails(@PathVariable("userId") String userId) {
		return userRepository.findOne(userId);
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{userId}")
	public Map<String, Object> edituser(@PathVariable("userId") String userId,
			@RequestBody Map<String, Object> userMap) {
		
		User user = new User(userMap.get("username").toString(), userMap.get("password").toString());
		if(userMap.get("email") != null) user.setEmail(userMap.get("email").toString());
		if(userMap.get("photo") != null) user.setPhoto(userMap.get("photo").toString());
		user.setId(userId);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "User updated successfully");
		response.put("user", userRepository.save(user));
		return response;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getAllusers() {
		List<User> users = userRepository.findAll();
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("totalusers", users.size());
		response.put("users", users);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{userId}")
	public Map<String, String> deleteUser(@PathVariable("userId") String userId) {
		userRepository.delete(userId);
		Map<String, String> response = new HashMap<String, String>();
		response.put("message", "user deleted successfully");

		return response;
	}
}
