// src/main/java/com/Somya/task_scheduler_api/config/RabbitMQConfig.java
package com.Somya.task_scheduler_api.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Tells Spring this is a configuration class
public class RabbitMQConfig {

    // This is our magic spell to create the mailbox
    @Bean // Tells Spring to run this method and manage the result
    public Queue jobQueue() {
        // This creates a new queue named "job.queue"
        // The "false" means it's not durable (it will be deleted if RabbitMQ restarts)
        // which is fine for our project.
        return new Queue("job.queue", false);
    }
}
