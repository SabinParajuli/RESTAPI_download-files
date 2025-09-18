package com.example.studentcrudapp.controller;

import com.example.studentcrudapp.entity.Student;
import com.example.studentcrudapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    // Create a new student
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        try {
            Student savedStudent = studentService.createStudent(student);
            return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            if (students.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> studentData = studentService.getStudentById(id);
        if (studentData.isPresent()) {
            return new ResponseEntity<>(studentData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Update student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(id, student);
        if (updatedStudent != null) {
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable Long id) {
        try {
            boolean deleted = studentService.deleteStudent(id);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Delete all students
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            for (Student student : students) {
                studentService.deleteStudent(student.getId());
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Search endpoints
    @GetMapping("/search/firstname")
    public ResponseEntity<List<Student>> searchByFirstName(@RequestParam String firstName) {
        try {
            List<Student> students = studentService.searchByFirstName(firstName);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/search/lastname")
    public ResponseEntity<List<Student>> searchByLastName(@RequestParam String lastName) {
        try {
            List<Student> students = studentService.searchByLastName(lastName);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/search/location")
    public ResponseEntity<List<Student>> searchByLocation(@RequestParam String location) {
        try {
            List<Student> students = studentService.searchByLocation(location);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/search/age")
    public ResponseEntity<List<Student>> searchByAge(@RequestParam Integer age) {
        try {
            List<Student> students = studentService.searchByAge(age);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/search/age-range")
    public ResponseEntity<List<Student>> searchByAgeRange(@RequestParam Integer minAge, @RequestParam Integer maxAge) {
        try {
            List<Student> students = studentService.searchByAgeRange(minAge, maxAge);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/search/fullname")
    public ResponseEntity<List<Student>> searchByFullName(@RequestParam String name) {
        try {
            List<Student> students = studentService.searchByFullName(name);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Export students to JSON
    @PostMapping("/export")
    public ResponseEntity<String> exportStudents(@RequestParam String filePath) {
        try {
            studentService.exportStudentsToJson(filePath);
            return new ResponseEntity<>("Students exported successfully to " + filePath, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error exporting students: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}