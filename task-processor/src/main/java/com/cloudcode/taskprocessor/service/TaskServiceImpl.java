package com.cloudcode.taskprocessor.service;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudcode.taskprocessor.constant.AppConstants.TASK_STATUS;
import com.cloudcode.taskprocessor.model.TaskInfo;
import com.cloudcode.taskprocessor.repo.TaskRepo;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    JobRunner jobRunner;

    @Override
    public TaskInfo saveTask(TaskInfo taskInfo) {
        if (Objects.isNull(taskInfo.getUpdateTime())) {
            taskInfo.setTaskStatus(TASK_STATUS.CREATED.name());
        }
        taskInfo.setUpdateTime(ZonedDateTime.now());

        TaskInfo createdTask = taskRepo.save(taskInfo);
        if (TASK_STATUS.CREATED.name().equals(createdTask.getTaskStatus())) {
            jobRunner.launchJob();
        }
        return createdTask;
    }

    @Override
    public List<TaskInfo> getTaskStatus(Integer taskId, String taskName) {
        if (Objects.nonNull(taskId) && Objects.nonNull(taskName)) {
            return taskRepo.findByTaskIdAndTaskNameAllIgnoreCase(taskId, taskName);
        } else if (Objects.nonNull(taskId)) {
            Optional<TaskInfo> taskInfoOptional = taskRepo.findById(taskId);
            return taskInfoOptional.isPresent() ? List.of(taskInfoOptional.get()) : Collections.EMPTY_LIST;
        } else if (Objects.nonNull(taskName)) {
            return taskRepo.findByTaskNameAllIgnoreCase(taskName);
        } else {
            return taskRepo.findAll();
        }
    }

}
