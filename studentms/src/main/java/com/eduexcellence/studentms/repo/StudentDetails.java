package com.eduexcellence.studentms.repo;

import com.eduexcellence.studentms.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDetails extends JpaRepository<Student, Integer> {

}
