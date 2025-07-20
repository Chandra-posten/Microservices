package com.eduexcellence.fee_management_ms;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class StudentmsClientResource {

    @Autowired
    private WebClient webClient;

    @GetMapping("/students")
    @CircuitBreaker(name = "fee-management-ms-client", fallbackMethod = "studentmsFallback")
    public Mono getStudents() {
        return webClient.get().uri("/api/v1/students").retrieve().bodyToMono(Object.class);
    }

    @GetMapping("/students/{id}")
    @CircuitBreaker(name = "fee-management-ms-client", fallbackMethod = "studentmsFallback")
    public Mono getStudentById(@PathVariable Integer id) {
        return webClient.get().uri("/api/v1/students/{id}", id).retrieve().bodyToMono(Object.class);
    }

    private Mono studentmsFallback(CallNotPermittedException ce) {
        return Mono.just("Studentms fallback method at FeeManagementms");
    }
}
