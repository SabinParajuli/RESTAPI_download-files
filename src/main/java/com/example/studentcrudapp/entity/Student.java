package com.example.studentcrudapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    @JsonProperty("firstname")
    private String firstName;
    
    @Column(name = "middle_name")
    @JsonProperty("middlename")
    private String middleName;
    
    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    @JsonProperty("lastname")
    private String lastName;
    
    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age must be greater than 0")
    @Column(nullable = false)
    @JsonProperty("age")
    private Integer age;
    
    @NotBlank(message = "Location is required")
    @Column(nullable = false)
    @JsonProperty("location")
    private String location;
    
    // Default constructor
    public Student() {}
    
    // Constructor with parameters
    public Student(String firstName, String middleName, String lastName, Integer age, String location) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.age = age;
        this.location = location;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", location='" + location + '\'' +
                '}';
    }
}