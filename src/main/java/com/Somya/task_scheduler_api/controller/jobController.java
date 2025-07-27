package com.Somya.task_scheduler_api.controller;

import com.Somya.task_scheduler_api.model.Job;
import com.Somya.task_scheduler_api.repository.jobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController //will handle web requests
@RequestMapping("/api/jobs") // All endpoints in this class will start with /api/jobs
public class jobController {

    // Spring's magic to give us an instance of our JobRepository
    @Autowired
    private jobRepository jobRepository;

    /**
     * Endpoint to create a new job.
     * Listens for POST requests to http://localhost:8080/api/jobs
     */
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job newJob) {
        // The @PrePersist methods in your Job entity will set the timestamps automatically
        Job savedJob = jobRepository.save(newJob);
        return ResponseEntity.ok(savedJob);
    }

    /**
     * Endpoint to get a job by its ID.
     * Listens for GET requests to http://localhost:8080/api/jobs/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Optional<Job> jobOptional = jobRepository.findById(id);

        if (jobOptional.isPresent()) {
            return ResponseEntity.ok(jobOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}