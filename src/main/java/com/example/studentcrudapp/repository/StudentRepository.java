package com.example.studentcrudapp.repository;

import com.example.studentcrudapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    // Find students by first name
    List<Student> findByFirstNameContainingIgnoreCase(String firstName);
    
    // Find students by last name
    List<Student> findByLastNameContainingIgnoreCase(String lastName);
    
    // Find students by location
    List<Student> findByLocationContainingIgnoreCase(String location);
    
    // Find students by age range
    List<Student> findByAgeBetween(Integer minAge, Integer maxAge);
    
    // Find students by exact age
    List<Student> findByAge(Integer age);
    
    // Custom query to find by full name
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(CONCAT(s.firstName, ' ', COALESCE(s.middleName, ''), ' ', s.lastName)) " +
           "LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Student> findByFullNameContaining(@Param("name") String name);
    
    // Find by first name and last name
    Optional<Student> findByFirstNameAndLastName(String firstName, String lastName);
}