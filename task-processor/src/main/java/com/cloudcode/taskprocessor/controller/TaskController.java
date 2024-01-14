package com.cloudcode.taskprocessor.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cloudcode.taskprocessor.model.TaskInfo;
import com.cloudcode.taskprocessor.service.TaskService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<TaskInfo> saveTask(@RequestBody TaskInfo taskInfo) {
        log.info("save task {}", taskInfo);
        return taskService.saveTask(taskInfo);
    }

    @GetMapping("/task")
    public Flux<TaskInfo> getTaskStatus(
            @RequestParam(required = false, name = "task-id") Long taskId,
            @RequestParam(required = false, name = "task-name") String taskName) {
        return taskService.getTaskStatus(taskId, taskName);
    }
}
