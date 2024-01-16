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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    // HTTP Rest endpoints
    @PostMapping("/task")
    public Mono<TaskInfo> saveTask(@RequestBody TaskInfo taskInfo) {
        log.info("save task {}", taskInfo);
        return taskService.saveTask(taskInfo);
    }

    @GetMapping("/task")
    public Flux<TaskInfo> getTask(
            @RequestParam(required = false, name = "task-id") Long taskId,
            @RequestParam(required = false, name = "task-name") String taskName) {
        return taskService.getTaskStatus(taskId, taskName);
    }

    /*
     * For demo this rest endpoint is creted in same service controller
     * In real project this will be there in different client service
     * move to task manager
     */
    private RSocketRequester rSocketRequester;

    public TaskController(RSocketRequester.Builder builder,
            @Value("${spring.rsocket.server.port:7000}") Integer rSocketPort) {
        rSocketRequester = builder.tcp("localhost", rSocketPort);
    }

    @GetMapping("/stream")
    public ResponseEntity<Flux<TaskInfo>> streamTask() {
        Flux<TaskInfo> tasks = rSocketRequester.route("task-stream").retrieveFlux(TaskInfo.class);
        return ResponseEntity.ok().body(tasks);
    }

}
