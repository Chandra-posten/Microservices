package com.eduexcellence.studentms.Services;

import com.eduexcellence.studentms.entities.Student;
import com.eduexcellence.studentms.repo.StudentDetails;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentDetails studentRepo;
    //private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    @Autowired
    public StudentService(StudentDetails studentRepo) {
        this.studentRepo = studentRepo;
    }

    public List<Student> getAllStudentDetails() {
        return this.studentRepo.findAll();
    }

    public ResponseEntity<Student> getStudentById(Integer id) {
        Optional<Student> student = this.studentRepo.findById(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Student> createStudent(Student student) {
        Student std = this.studentRepo.save(student);
        return ResponseEntity.created(URI.create(std.getId().toString())).build();
    }

    public ResponseEntity<Student> updateStudentDetails(Student student) {
        Integer id = student.getId();
        Optional<Student> studentToBeUpdated = this.studentRepo.findById(id);
        if (studentToBeUpdated.isPresent()) {
            Student existingStudent = studentToBeUpdated.get();
            if (student.getFirstName() != null) existingStudent.setFirstName(student.getFirstName());
            if (student.getLastName() != null) existingStudent.setLastName(student.getLastName());
            if (student.getRoll_number() != null) existingStudent.setRoll_number(student.getRoll_number());
            if (student.getDate_of_birth() != null) existingStudent.setDate_of_birth(student.getDate_of_birth());
            if (student.getGrade() != null) existingStudent.setGrade(student.getGrade());
            Student updatedStudent = this.studentRepo.save(existingStudent);
            return ResponseEntity.ok(updatedStudent);
        }
        return ResponseEntity.notFound().build();
    }

    public void deleteStudent(Integer id) {
        try {
            if (!this.studentRepo.existsById(id)) {
                throw new RuntimeException("student not found");
            }
            this.studentRepo.deleteById(id);
        } catch (Exception e) {
            //logger.error("Student details not found", e);
        }
    }
}
