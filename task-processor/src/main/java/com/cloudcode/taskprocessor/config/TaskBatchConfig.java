package com.cloudcode.taskprocessor.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.cloudcode.taskprocessor.model.TaskInfo;

import java.time.Instant;

@Configuration
public class TaskBatchConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Bean
    public Job job() {
        // return new JobBuilder("job-"+ Instant.now().getEpochSecond(),
        // jobRepository).flow(step()).end().build();
        return new JobBuilder("job-" + Instant.now().getEpochSecond(), jobRepository).start(step()).listener(listener())
                .build();
    }

    @Bean
    public Step step() {
        return new StepBuilder("step-1", jobRepository)
                .<TaskInfo, TaskInfo>chunk(1, transactionManager)
                .reader(reader()).processor(processor()).writer(writer()).build();
    }

    @Bean
    public TaskItemReader reader() {
        return new TaskItemReader();
    }

    @Bean
    public TaskItemProcessor processor() {
        return new TaskItemProcessor();
    }

    @Bean
    public TaskItemWriter writer() {
        return new TaskItemWriter();
    }

    public TaskExecutionListener listener() {
        return new TaskExecutionListener();
    }

}
