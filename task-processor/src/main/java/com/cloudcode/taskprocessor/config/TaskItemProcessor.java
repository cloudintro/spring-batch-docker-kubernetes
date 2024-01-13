package com.cloudcode.taskprocessor.config;

import java.time.ZonedDateTime;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.cloudcode.taskprocessor.constant.AppConstants.TASK_STATUS;
import com.cloudcode.taskprocessor.model.TaskInfo;

@Log4j2
public class TaskItemProcessor implements ItemProcessor<TaskInfo, TaskInfo> {

    @Override
    @Nullable
    public TaskInfo process(@NonNull TaskInfo item) throws Exception {
        item.setUpdateTime(ZonedDateTime.now());
        item.setTaskStatus(TASK_STATUS.COMPLETED.name());
        log.info("processing task {}", item);
        return item;
    }

}
