package com.cloudcode.taskprocessor.service;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudcode.taskprocessor.constant.AppConstants;
import com.cloudcode.taskprocessor.constant.AppConstants.TASK_STATUS;
import com.cloudcode.taskprocessor.model.TaskInfo;
import com.cloudcode.taskprocessor.repo.TaskRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    JobRunner jobRunner;

    @Override
    public Mono<TaskInfo> saveTask(TaskInfo taskInfo) {
        boolean launchJob = false;
        if (Objects.isNull(taskInfo.getUpdateTime())) {
            launchJob = true;
            taskInfo.setTaskId(Instant.now().toEpochMilli());
            taskInfo.setTaskStatus(TASK_STATUS.CREATED.name());
        }
        taskInfo.setUpdateTime(ZonedDateTime.now().format(AppConstants.dateTimeFormatter));

        Mono<TaskInfo> createdTask = taskRepo.save(taskInfo);
        if (launchJob) {
            jobRunner.launchJob();
        }
        return createdTask;
    }

    @Override
    public Flux<TaskInfo> getTaskStatus(Long taskId, String taskName) {
        if (Objects.nonNull(taskId) && Objects.nonNull(taskName)) {
            return taskRepo.findByTaskIdAndTaskNameAllIgnoreCase(taskId, taskName);
        } else if (Objects.nonNull(taskId)) {
            Mono<TaskInfo> taskInfoMono = taskRepo.findById(taskId);
            return taskInfoMono.flux();
        } else if (Objects.nonNull(taskName)) {
            return taskRepo.findByTaskNameAllIgnoreCase(taskName);
        } else {
            return taskRepo.findAll();
        }
    }

    @Override
    public Flux<TaskInfo> getTasks() {
        return taskRepo.findAll();
    }

}
