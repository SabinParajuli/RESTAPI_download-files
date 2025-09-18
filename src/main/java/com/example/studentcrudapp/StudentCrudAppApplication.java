package com.example.studentcrudapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StudentCrudAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentCrudAppApplication.class, args);
        System.out.println("Student CRUD Application started successfully!");
        System.out.println("Access the API at: http://localhost:8080/api/students");
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}