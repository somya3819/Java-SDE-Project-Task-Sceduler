package com.Somya.task_scheduler_api.controller;

import com.Somya.task_scheduler_api.model.Job;
// Notice the capital 'J' and 'R' here. This is important!
import com.Somya.task_scheduler_api.repository.JobRepository;
// This is the new import for our message-sending tool
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    // Here is our new tool for sending messages!
    @Autowired
    private RabbitTemplate rabbitTemplate;


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