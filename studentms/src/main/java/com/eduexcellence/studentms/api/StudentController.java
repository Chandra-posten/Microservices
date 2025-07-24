package com.eduexcellence.studentms.api;

import com.eduexcellence.studentms.Services.StudentService;
import com.eduexcellence.studentms.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id) {
        return this.studentServcie.getStudentById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Student> createStudentDetails(@RequestBody Student student) {
        return this.studentServcie.createStudent(student);
    }

    @PatchMapping("/update")
    public ResponseEntity<Student> updateStudentDetails(@RequestBody Student student) {
        if (student.getId() == null) {
            throw new IllegalArgumentException("id is mandatory");
        }
        // Check at least one updatable property is present (besides id)
        if (student.getFirstName() == null && student.getLastName() == null && student.getRoll_number() == null && student.getDate_of_birth() == null && student.getGrade() == null) {
            throw new IllegalArgumentException("At least one property to update must be provided");
        }
        return this.studentServcie.updateStudentDetails(student);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudentDetails(@PathVariable Integer id) {
        this.studentServcie.deleteStudent(id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
