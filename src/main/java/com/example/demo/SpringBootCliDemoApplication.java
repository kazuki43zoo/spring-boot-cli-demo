package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootCliDemoApplication {

  public static void main(String[] args) {
    int exitCode = SpringApplication.exit(SpringApplication.run(SpringBootCliDemoApplication.class, args));
    if (exitCode > 0) {
      System.exit(exitCode);
    }
  }

}
