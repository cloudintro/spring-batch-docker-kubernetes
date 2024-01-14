package com.cloudcode.taskprocessor.config;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import com.cloudcode.taskprocessor.constant.AppConstants.TASK_STATUS;
import com.cloudcode.taskprocessor.model.TaskInfo;
import com.cloudcode.taskprocessor.repo.TaskRepo;

@Log4j2
public class TaskItemReader implements ItemReader<TaskInfo> {

    @Autowired
    private TaskRepo taskRepo;

    @Override
    @Nullable
    public TaskInfo read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Flux<TaskInfo> taskList = taskRepo.findByTaskStatus(TASK_STATUS.CREATED.name());
        log.info("reading task {}", taskList);
        return taskList.next().block();
    }

}
