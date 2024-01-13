package com.cloudcode.taskprocessor.config;

import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

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
        List<TaskInfo> taskList = taskRepo.findByTaskStatus(TASK_STATUS.CREATED.name());
        log.info("reading task {}", taskList);
        if (!CollectionUtils.isEmpty(taskList)) {
            return taskList.get(0);
        }
        return null;
    }

}
