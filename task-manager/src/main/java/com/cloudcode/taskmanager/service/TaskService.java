package com.cloudcode.taskmanager.service;

import java.util.List;

import com.cloudcode.taskmanager.model.TaskInfo;

public interface TaskService {
    TaskInfo createTask(TaskInfo taskInfo);

    List<TaskInfo> getTaskStatus(Integer taskId, String taskName);
}
