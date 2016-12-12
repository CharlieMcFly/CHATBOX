package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.Application;
import app.model.User;
import app.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class UserControllerTest {

  //Required to Generate JSON content from Java objects
  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  
  //Required to delete the data added for tests.
  //Directly invoke the APIs interacting with the DB
  @Autowired
	private UserRepository userRepository;
  
  //Test RestTemplate to invoke the APIs.
  private RestTemplate restTemplate = new TestRestTemplate();
  
  @Test
  public void testCreateUserApi() throws JsonProcessingException{
    
    //Building the Request body data
    Map<String, Object> requestBody = new HashMap<String, Object>();
    requestBody.put("username", "User 1");
    requestBody.put("password", "Pwd 1");
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(MediaType.APPLICATION_JSON);

    //Creating http entity object with request body and headers
    HttpEntity<String> httpEntity = 
        new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);
    
    //Invoking the API
    Map<String, Object> apiResponse = 
        restTemplate.postForObject("http://localhost:8888/users", httpEntity, Map.class, Collections.EMPTY_MAP);

    assertNotNull(apiResponse);
    
    //Asserting the response of the API.
    String message = apiResponse.get("message").toString();
    assertEquals("User created successfully", message);
    String userId = ((Map<String, Object>)apiResponse.get("user")).get("id").toString();
    
    assertNotNull(userId);
    
    //Fetching the User details directly from the DB to verify the API succeeded
    User userFromDb = userRepository.findOne(userId);
    assertEquals("User 1", userFromDb.getUsername());
    assertEquals("Pwd 1", userFromDb.getPassword());
    
    //Delete the data added for testing
    userRepository.delete(userId);

  }
  
  public void testGetUserDetailsApi(){
    //Create a new user using the UserRepository API
    User user = new User("User1", "Pwd1");
    user = userRepository.save(user);
    
    String userId = user.getId();
    
    //Now make a call to the API to get details of the user
    User apiResponse = restTemplate.getForObject("http://localhost:8888/users/"+ userId, User.class);
    
    //Verify that the data from the API and data saved in the DB are same
    assertNotNull(apiResponse);
    assertEquals(user.getUsername(), apiResponse.getUsername());
    assertEquals(user.getId(), apiResponse.getId());
    assertEquals(user.getPassword(), apiResponse.getPassword());
    
    //Delete the Test data created
    userRepository.delete(userId);
  }
  
  @Test
  public void testUpdateUserDetails() throws JsonProcessingException{
    //Create a new user using the UserRepository API
    User user = new User("user1", "password1");
    userRepository.save(user);
    
    String userId = user.getId();
    
    //Now create Request body with the updated User Data.
    Map<String, Object> requestBody = new HashMap<String, Object>();
    requestBody.put("username", "user2");
    requestBody.put("password", "password2");
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(MediaType.APPLICATION_JSON);

    //Creating http entity object with request body and headers
    HttpEntity<String> httpEntity = 
        new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);
    
    //Invoking the API
    Map<String, Object> apiResponse = (Map) restTemplate.exchange("http://localhost:8888/users/" + userId, 
        HttpMethod.PUT, httpEntity, Map.class, Collections.EMPTY_MAP).getBody();
    
    
    assertNotNull(apiResponse);
    assertTrue(!apiResponse.isEmpty());
    
    //Asserting the response of the API.
    String message = apiResponse.get("message").toString();
    assertEquals("User updated successfully", message);
    
    //Fetching the User details directly from the DB to verify the API succeeded in updating the user details
    User userFromDb = userRepository.findOne(userId);
    assertEquals(requestBody.get("username"), userFromDb.getUsername());
    assertEquals(requestBody.get("password"), userFromDb.getPassword());
    
    //Delete the data added for testing
    userRepository.delete(userId);

  }
  
  @Test
  public void testDeleteUserApi(){
    //Create a new user using the UserRepository API
    User user = new User("User1", "Pwd1");
    userRepository.save(user);
    
    String userId = user.getId();
    
    //Now Invoke the API to delete the user
    restTemplate.delete("http://localhost:8888/users/"+ userId, Collections.EMPTY_MAP);
    
    //Try to fetch from the DB directly
    User userFromDb = userRepository.findOne(userId);
    //and assert that there is no data found
    assertNull(userFromDb);
  }
  
  
  public void testGetAllUsersApi(){
    //Add some test data for the API
    User user1 = new User("User1", "Pwd1");
    userRepository.save(user1);
    
    User user2 = new User("User2", "Pwd2");
    userRepository.save(user2);
    
    //Invoke the API
    Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8888/users", Map.class);
    
    //Assert the response from the API
    int totalUsers = Integer.parseInt(apiResponse.get("totalusers").toString());
    assertTrue(totalUsers == 2);
    
    List<Map<String, Object>> usersList = (List<Map<String, Object>>)apiResponse.get("users");
    assertTrue(usersList.size() == 2);
    
    //Delete the test data created
    userRepository.delete(user1.getId());
    userRepository.delete(user2.getId());
  }
}
