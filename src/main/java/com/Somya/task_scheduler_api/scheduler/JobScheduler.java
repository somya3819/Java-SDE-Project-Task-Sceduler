package com.Somya.task_scheduler_api.scheduler;

import com.Somya.task_scheduler_api.model.Job;
import com.Somya.task_scheduler_api.model.Job.JobStatus;
import com.Somya.task_scheduler_api.repository.JobRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class JobScheduler {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // This is the method that runs automatically
    @Scheduled(fixedRate = 60000) // Runs every 60 seconds
    public void scheduleDueJobs() {
        System.out.println("SCHEDULER: Checking for due jobs... " + LocalDateTime.now());

        List<Job> dueJobs = jobRepository.findByStatusAndScheduledTimeBefore(JobStatus.PENDING, LocalDateTime.now());

        if (dueJobs.isEmpty()) {
            System.out.println("SCHEDULER: No jobs are due. Going back to sleep.");
            return;
        }

        System.out.println("SCHEDULER: Found " + dueJobs.size() + " jobs to queue.");

        for (Job job : dueJobs) {
            job.setStatus(JobStatus.RUNNING);
            jobRepository.save(job);

            rabbitTemplate.convertAndSend("job.queue", job.getId().toString());
            System.out.println("SCHEDULER: Queued Job ID " + job.getId());
        }
    }
}