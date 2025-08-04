package com.Somya.task_scheduler_api.controller;

import com.Somya.task_scheduler_api.model.Job;
import com.Somya.task_scheduler_api.repository.JobRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Add this new import for the List
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Handles POST requests to create a new job.
     */
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job newJob) {
        // 1. Save the job to the database
        Job savedJob = jobRepository.save(newJob);

        // 2. Send a message to the queue with the new job's ID
        rabbitTemplate.convertAndSend("job.queue", savedJob.getId().toString());
        System.out.println("Sent message to queue for Job ID: " + savedJob.getId());

        // 3. Return the response
        return ResponseEntity.ok(savedJob);
    }

    /**
     * Handles GET requests for a single job by its ID.
     * Example: /api/jobs/1
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

    /**
     * Handles GET requests for all jobs.
     * This is the new method you are adding.
     * Example: /api/jobs
     */
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> allJobs = jobRepository.findAll();
        return ResponseEntity.ok(allJobs);
    }
}