# Distributed Task Scheduler (Work in Progress)                           # Date : 27/07/2025
Hey! Welcome to my distributed task scheduler project. I'm building this system from the ground up using Java and Spring Boot to get a real, hands-on understanding of how backend systems work at scale.

# Project Status: This project is currently about 50% complete. The foundation is laid, the core components are talking to each other, and it's ready for the next phase.

# What's the Big Idea?
So, what am I actually building? Imagine a system that can handle background tasks—like sending out thousands of welcome emails, generating daily reports, or processing uploaded videos—without slowing down the main application.

This project is my attempt at building a smaller, personal version of the powerful scheduling systems used at big tech companies. It's designed to be reliable and to handle tasks asynchronously using a message queue.

# Where We Are Right Now (The Story So Far)
As of now, I've built the entire "intake" part of the system. When a new job is created through the API, it kicks off a clean, simple workflow:

The API Call: A user (or another service) sends a POST request to our task-scheduler-api service with the details of the job.
Saving the Job: The API server first saves the job's information into a PostgreSQL database. This gives us a permanent record of every task.
Sending it to the Queue: Immediately after saving, the API server sends a message containing the new Job's ID to a RabbitMQ message queue. This is the crucial step—it decouples the "requesting" of a job from the "doing" of a job.

The message now sits safely in the job.queue, waiting for a worker to pick it up.

# Here's a quick visual of what's built and working:
Postman --- (HTTP POST Request) ---> API Server --- (Saves to) ---> PostgreSQL Database
                                          |
                                          |
                                          +--- (Sends Message with Job ID) ---> RabbitMQ Queue


# Tech I'm Using
   Language: Java 17
   
   Framework: Spring Boot 3
   
   Database: PostgreSQL (running in a Docker container)
   
   Messaging: RabbitMQ (also running in a Docker container)
   
   API Testing: Postman
   
   Build Tool: Maven

# How to Run What We Have So Far
   If you want to run the project in its current state, here’s how:

# Prerequisites:
   Java 17 (JDK)
   
   Docker Desktop

   Postman

# Steps:

Start the Database and Message Queue:
1.Open your terminal and run these two Docker commands:
   # Start PostgreSQL Database
   docker run --name my-postgres -e POSTGRES_PASSWORD=mysecretpassword -e POSTGRES_DB=taskdb -p 5432:5432 -d postgres

   # Start RabbitMQ
   docker run -d --name my-rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management


2.Run the Spring Boot Application:
   Clone this repository, open it in your IDE (like VS Code or IntelliJ), and run the TaskSchedulerApiApplication.java file.

3.Test it with Postman:
   Once the application has started, send a POST request to http://localhost:8080/api/jobs with a JSON body like this:

   {
   "title": "My First Test Job",
    "description": "Testing the API and message queue.",
    "scheduledTime": "2025-09-01T11:00:00"
   }
   You should get a 200 OK response. You can then check the RabbitMQ Management UI at http://localhost:15672 (user: guest, pass: guest) to see your message sitting in the job.queue!

# What's Next?
   The foundation is solid, but the work isn't done. Here's what I'm building next:

   The Worker Service: A completely separate application whose only job is to listen to the job.queue, pick up messages, and "process" them.
   The Automated Scheduler: A mechanism to queue up jobs automatically based on a schedule (e.g., using a CRON expression).

# Thanks for checking out my project. Feel free to follow along as I build out the rest of the system!
