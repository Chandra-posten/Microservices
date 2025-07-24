package com.eduexcellence.studentms;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

class StudentIdRequest {
    private Integer studentId;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}

@RestController
public class FeeManagementmsClientResource {

    @Autowired
    private WebClient webClient;

    @GetMapping("/fee-details")
    @CircuitBreaker(name = "studentmsclient", fallbackMethod = "feeManagementFallback")
    public Mono getFeesForAllStudents() {
        return webClient.get().uri("/api/v1/fee-management").retrieve().bodyToMono(Object.class);
    }

    @PostMapping(value="/pay-fee/student", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CircuitBreaker(name = "studentmsclient", fallbackMethod = "feeManagementFallback")
    public Mono getStudentById(@RequestBody StudentIdRequest StudentRequest) {
        Integer studentId = StudentRequest.getStudentId();
        return webClient.post().uri("/api/v1/fee-management/pay-fee/student").contentType(MediaType.APPLICATION_JSON).bodyValue(studentId).retrieve().bodyToMono(Object.class);
    }

    @GetMapping("/fee-details-by-student-id/{id}")
    @CircuitBreaker(name = "studentmsclient", fallbackMethod = "feeManagementFallback")
    public Mono getFeeByStudentId(@PathVariable Integer id) {
        return webClient.get().uri("/api/v1/fee-management/student/{id}", id).retrieve().bodyToMono(Object.class);
    }

    private Mono feeManagementFallback(CallNotPermittedException ce) {
        return Mono.just("Fee Management fallback method at studentms");
    }
}
