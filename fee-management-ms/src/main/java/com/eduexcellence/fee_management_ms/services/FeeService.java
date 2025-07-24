package com.eduexcellence.fee_management_ms.services;

import com.eduexcellence.fee_management_ms.entities.FeeDetailsEntity;
import com.eduexcellence.fee_management_ms.repo.FeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeeService {
    private FeeRepo feeRepo;
    @Autowired
    public FeeService(FeeRepo feeRepo)
    {
        this.feeRepo = feeRepo;
    }

    public List<FeeDetailsEntity> getFeeDetailsForAllStudents() {
        return this.feeRepo.findAll();
    }


    public ResponseEntity<FeeDetailsEntity> getFeeDetailsById(Integer id) {
        Optional<FeeDetailsEntity> feeDetails = this.feeRepo.findById(id);
        if (feeDetails.isPresent()) {
            return ResponseEntity.ok(feeDetails.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<FeeDetailsEntity> getFeeDetailsByStudentId(Integer student_id) {
        Optional<FeeDetailsEntity> feeDetails = this.feeRepo.findByStudentId(student_id);
        if (feeDetails.isPresent()) {
            return ResponseEntity.ok(feeDetails.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<FeeDetailsEntity> payFeeByStudentId(Integer student_id) {
        Optional<FeeDetailsEntity> feeDetails = this.feeRepo.findByStudentId(student_id);
        if (feeDetails.isPresent()) {
            FeeDetailsEntity studentFeeDetails = feeDetails.get();
            studentFeeDetails.setStatus("Paid");
            FeeDetailsEntity updatedFee = this.feeRepo.save(studentFeeDetails);
            return ResponseEntity.ok(updatedFee);
        }
        return ResponseEntity.notFound().build();
    }

    /*public ResponseEntity<FeeDetailsEntity> getFeeDetailsByStudentRollNumber(String rollNumber) {
        Optional<FeeDetailsEntity> feeDetails = this.feeRepo.findByStudentRollNumber(rollNumber);
        if (feeDetails.isPresent()) {
            return ResponseEntity.ok(feeDetails.get());
        }
        return ResponseEntity.notFound().build();
    }*/
}
