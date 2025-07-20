package com.eduexcellence.fee_management_ms.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "fee_details")
public class FeeDetailsEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name="student_id")
    private Integer studentId;

    private Long fee;
    private String status;

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
