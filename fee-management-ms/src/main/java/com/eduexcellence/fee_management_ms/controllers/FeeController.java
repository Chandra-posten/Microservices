package com.eduexcellence.fee_management_ms.controllers;

import com.eduexcellence.fee_management_ms.entities.FeeDetailsEntity;
import com.eduexcellence.fee_management_ms.services.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
@RestController
@RequestMapping("/api/v1/fee-management")
public class FeeController {
    private FeeService feeService;

    @Autowired
    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    @GetMapping
    public List<FeeDetailsEntity> getAllStudentFeeDetails() {
        return this.feeService.getFeeDetailsForAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeeDetailsEntity> getFeeDetailsById(@PathVariable Integer id) {
        return this.feeService.getFeeDetailsById(id);
    }

    @PostMapping
    public ResponseEntity<FeeDetailsEntity> getFeeDetailsByStudentId(@RequestBody Integer student_id) {
        return this.feeService.getFeeDetailsByStudentId(student_id);
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<FeeDetailsEntity> getFeeByStudentId(@PathVariable Integer id) {
        return this.feeService.getFeeDetailsByStudentId(id);
    }

    @PostMapping(value = "/pay-fee/student", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FeeDetailsEntity> payFeeByStudentId(@RequestBody Object studentId) {
        if (studentId instanceof Integer) {
            return this.feeService.payFeeByStudentId((Integer) studentId);
        }
        return ResponseEntity.badRequest().build();
    }

    /*@PostMapping
    public ResponseEntity<FeeDetailsEntity> getFeeDetailsByStudentRollNumber(@RequestBody String roll_number) {
        return this.feeService.getFeeDetailsByStudentRollNumber(roll_number);
    }*/
}
