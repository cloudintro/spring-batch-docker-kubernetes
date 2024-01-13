package com.cloudcode.taskmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.cloudcode.taskmanager.model.TaskInfo;
import com.cloudcode.taskmanager.service.TaskService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@RestController
public class TaskController {

    private TaskService taskService;

    private TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/task")
    public ResponseEntity<TaskInfo> createTask(@RequestBody @Valid TaskInfo taskInfo) {
        log.info("Create task {}", taskInfo);
        return new ResponseEntity<>(taskService.createTask(taskInfo), HttpStatus.CREATED);
    }

    @GetMapping("/task")
    public ResponseEntity<List<TaskInfo>> getTaskStatus(@RequestParam(required = false, name = "task-id") Integer taskId,
            @RequestParam(required = false, name = "task-name") String taskName) {
        return new ResponseEntity<>(taskService.getTaskStatus(taskId, taskName), HttpStatus.OK);
    }

}
