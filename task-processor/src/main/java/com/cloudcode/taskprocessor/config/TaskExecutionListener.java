package com.cloudcode.taskprocessor.config;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.lang.NonNull;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TaskExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(@NonNull JobExecution jobExecution) {
        log.info("Before Job status: {}, start time {}", jobExecution.getStatus(), jobExecution.getStartTime());
	}

	@Override
	public void afterJob(@NonNull JobExecution jobExecution) {
        log.info("After Job status: {}, start time {}", jobExecution.getStatus(), jobExecution.getEndTime());
	}
    
}
