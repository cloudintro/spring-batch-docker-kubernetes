package com.cloudcode.taskprocessor.config;

import java.time.ZonedDateTime;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.cloudcode.taskprocessor.constant.AppConstants.TASK_STATUS;
import com.cloudcode.taskprocessor.model.TaskInfo;

public class TaskItemProcessor implements ItemProcessor<TaskInfo, TaskInfo> {

    @Override
    @Nullable
    public TaskInfo process(@NonNull TaskInfo item) throws Exception {
        item.setUpdateTime(ZonedDateTime.now());
        item.setTaskStatus(TASK_STATUS.COMPLETED.name());
        return item;
    }

}
