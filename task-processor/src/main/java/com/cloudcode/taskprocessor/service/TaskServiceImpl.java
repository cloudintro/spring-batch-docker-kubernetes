package com.cloudcode.taskprocessor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cloudcode.taskprocessor.model.TaskInfo;

@Service
public class TaskServiceImpl implements TaskService {

    @Override
    public TaskInfo saveTask(TaskInfo taskInfo) {
        return taskInfo;
    }

    @Override
    public List<TaskInfo> getTaskStatus(Integer taskId, String taskName) {
        return List.of(new TaskInfo());
    }

}
