package com.cloudcode.taskprocessor.service;

import java.util.List;

import com.cloudcode.taskprocessor.model.TaskInfo;

import reactor.core.publisher.Flux;

public interface TaskService {
    TaskInfo saveTask(TaskInfo taskInfo);

    List<TaskInfo> getTaskStatus(Integer taskId, String taskName);

    Flux<TaskInfo> getTasks();
}
