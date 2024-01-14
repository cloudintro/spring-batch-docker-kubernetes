package com.cloudcode.taskprocessor.service;

import com.cloudcode.taskprocessor.model.TaskInfo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {
    Mono<TaskInfo> saveTask(TaskInfo taskInfo);

    Flux<TaskInfo> getTaskStatus(Long taskId, String taskName);

    Flux<TaskInfo> getTasks();
}
