package com.example.studentcrudapp.service;

import com.example.studentcrudapp.entity.Student;
import com.example.studentcrudapp.repository.StudentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private ExcelExportService excelExportService;
    
    @Autowired
    private PdfExportService pdfExportService;
    
    @Value("${app.json.file.path:students.json}")
    private String jsonFilePath;
    
    // Load data from JSON file on startup
    @PostConstruct
    public void loadDataFromJson() {
        try {
            File jsonFile = new File(jsonFilePath);
            if (jsonFile.exists()) {
                List<Student> students = objectMapper.readValue(jsonFile, new TypeReference<List<Student>>() {});
                studentRepository.saveAll(students);
                System.out.println("Loaded " + students.size() + " students from JSON file");
            } else {
                System.out.println("JSON file not found at path: " + jsonFilePath);
            }
        } catch (IOException e) {
            System.err.println("Error loading data from JSON file: " + e.getMessage());
        }
    }
    
    // Create a new student
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }
    
    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    // Get student by ID
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    // Update student
    public Student updateStudent(Long id, Student studentDetails) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setFirstName(studentDetails.getFirstName());
            student.setMiddleName(studentDetails.getMiddleName());
            student.setLastName(studentDetails.getLastName());
            student.setAge(studentDetails.getAge());
            student.setLocation(studentDetails.getLocation());
            return studentRepository.save(student);
        }
        return null;
    }
    
    // Delete student by ID
    public boolean deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Search students by first name
    public List<Student> searchByFirstName(String firstName) {
        return studentRepository.findByFirstNameContainingIgnoreCase(firstName);
    }
    
    // Search students by last name
    public List<Student> searchByLastName(String lastName) {
        return studentRepository.findByLastNameContainingIgnoreCase(lastName);
    }
    
    // Search students by location
    public List<Student> searchByLocation(String location) {
        return studentRepository.findByLocationContainingIgnoreCase(location);
    }
    
    // Search students by age
    public List<Student> searchByAge(Integer age) {
        return studentRepository.findByAge(age);
    }
    
    // Search students by age range
    public List<Student> searchByAgeRange(Integer minAge, Integer maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }
    
    // Search students by full name
    public List<Student> searchByFullName(String name) {
        return studentRepository.findByFullNameContaining(name);
    }
    
    // Export students to JSON file
    public void exportStudentsToJson(String filePath) throws IOException {
        List<Student> students = studentRepository.findAll();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), students);
    }
    
    // Export students to Excel file
    public byte[] exportStudentsToExcel() throws IOException {
        List<Student> students = studentRepository.findAll();
        return excelExportService.exportStudentsToExcel(students);
    }
    
    // Export students to PDF file
    public byte[] exportStudentsToPdf() throws IOException {
        List<Student> students = studentRepository.findAll();
        return pdfExportService.exportStudentsToPdf(students);
    }
}