package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableAutoConfiguration
@EnableMongoRepositories
@SpringBootApplication
public class Application {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(new Object[] { Application.class }, args);
  }
}
