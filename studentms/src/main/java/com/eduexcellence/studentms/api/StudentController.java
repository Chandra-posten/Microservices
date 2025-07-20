package com.eduexcellence.studentms.api;

import com.eduexcellence.studentms.Services.StudentService;
import com.eduexcellence.studentms.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private StudentService studentServcie;

    @Autowired
    public StudentController (StudentService studentService) {
        this.studentServcie = studentService;
    }

    @GetMapping
    public List<Student> getAllStudentDetails() {
        return this.studentServcie.getAllStudentDetails();
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id) {
        return this.studentServcie.getStudentById(id);
    }

    @PostMapping
    public ResponseEntity<Student> createStudentDetails(@RequestBody Student student) {
        return this.studentServcie.createStudent(student);
    }

    @PatchMapping
    @RequestMapping("/update")
    public ResponseEntity<Student> updateStudentDetails(@RequestBody Student student) {
        return this.studentServcie.updateStudentDetails(student);
    }

    @DeleteMapping
    @RequestMapping("/delete/{id}")
    public void deleteStudentDetails(@PathVariable Integer id) {
        this.studentServcie.deleteStudent(id);
    }
}
