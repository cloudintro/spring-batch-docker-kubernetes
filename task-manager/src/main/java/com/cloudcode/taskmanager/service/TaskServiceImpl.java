package com.cloudcode.taskmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudcode.taskmanager.client.TaskProcessorProxy;
import com.cloudcode.taskmanager.model.TaskInfo;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskProcessorProxy taskprocessor;

    @Override
    public TaskInfo createTask(TaskInfo taskInfo) {
        TaskInfo newTaskInfo = new TaskInfo(taskInfo.getTaskName(), taskInfo.getTaskDesc());
        return taskprocessor.saveTask(newTaskInfo).getBody();
    }

    @Override
    public List<TaskInfo> getTaskStatus(Integer taskId, String taskName) {
        return taskprocessor.getTaskStatus(taskId, taskName).getBody();
    }
}
