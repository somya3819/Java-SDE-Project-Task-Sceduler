package com.Somya.task_scheduler_api.repository;

import com.Somya.task_scheduler_api.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface jobRepository extends JpaRepository<Job, Long> {
    // This interface remains empty
}