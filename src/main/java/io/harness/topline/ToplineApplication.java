package io.harness.topline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(
    exclude = {org.springframework.boot.autoconfigure.security.servlet
                   .SecurityAutoConfiguration.class}, scanBasePackages = {"io.harness.topline"})
public class ToplineApplication {

  public static void main(String[] args) {
    SpringApplication.run(ToplineApplication.class, args);
  }
}
