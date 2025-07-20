package com.eduexcellence.studentms;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class FeeManagementmsClientResource {

    @Autowired
    private WebClient webClient;

    @GetMapping("/fee-details")
    @CircuitBreaker(name = "studentmsclient", fallbackMethod = "feeManagementFallback")
    public Mono getFeesForAllStudents() {
        return webClient.get().uri("/api/v1/fee-management").retrieve().bodyToMono(Object.class);
    }

    @GetMapping("/fee-details/{id}")
    @CircuitBreaker(name = "studentmsclient", fallbackMethod = "feeManagementFallback")
    public Mono getStudentById(@PathVariable Integer id) {
        return webClient.get().uri("/api/v1/fee-management/{id}", id).retrieve().bodyToMono(Object.class);
    }

    private Mono feeManagementFallback(CallNotPermittedException ce) {
        return Mono.just("Fee Management fallback method at studentms");
    }
}
