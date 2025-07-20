package com.eduexcellence.fee_management_ms.repo;

import com.eduexcellence.fee_management_ms.entities.FeeDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeeRepo extends JpaRepository<FeeDetailsEntity, Integer> {
    Optional<FeeDetailsEntity> findByStudentId(Integer studentId);
    //Optional<FeeDetailsEntity> findByStudentRollNumber(String rollNumber);
}
