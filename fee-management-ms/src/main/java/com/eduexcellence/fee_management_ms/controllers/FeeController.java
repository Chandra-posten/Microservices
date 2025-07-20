package com.eduexcellence.fee_management_ms.controllers;

import com.eduexcellence.fee_management_ms.entities.FeeDetailsEntity;
import com.eduexcellence.fee_management_ms.services.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/fee-management")
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

    @GetMapping
    @RequestMapping("/{id}")
    public ResponseEntity<FeeDetailsEntity> getFeeDetailsById(@PathVariable Integer id) {
        return this.feeService.getFeeDetailsById(id);
    }

    @PostMapping
    public ResponseEntity<FeeDetailsEntity> getFeeDetailsByStudentId(@RequestBody Integer student_id) {
        return this.feeService.getFeeDetailsByStudentId(student_id);
    }

    /*@PostMapping
    public ResponseEntity<FeeDetailsEntity> getFeeDetailsByStudentRollNumber(@RequestBody String roll_number) {
        return this.feeService.getFeeDetailsByStudentRollNumber(roll_number);
    }*/
}
