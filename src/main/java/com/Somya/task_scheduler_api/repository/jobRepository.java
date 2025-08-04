package com.Somya.task_scheduler_api.repository;

import com.Somya.task_scheduler_api.model.Job;
import com.Somya.task_scheduler_api.model.Job.JobStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    // This interface remains empty
    List<Job> findByStatusAndScheduledTimeBefore(JobStatus status, LocalDateTime time);
}
