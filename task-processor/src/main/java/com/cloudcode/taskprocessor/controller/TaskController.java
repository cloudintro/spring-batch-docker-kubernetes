package com.cloudcode.taskprocessor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.cloudcode.taskprocessor.model.TaskInfo;
import com.cloudcode.taskprocessor.service.TaskService;

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
    public ResponseEntity<TaskInfo> saveTask(@RequestBody TaskInfo taskInfo) {
        log.info("save task {}", taskInfo);
        return new ResponseEntity<>(taskService.saveTask(taskInfo), HttpStatus.CREATED);
    }

    @GetMapping("/task")
    public ResponseEntity<List<TaskInfo>> getTaskStatus(@RequestParam(required = false) Integer taskId,
            @RequestParam(required = false) String taskName) {
        return new ResponseEntity<>(taskService.getTaskStatus(taskId, taskName), HttpStatus.OK);
    }
}
