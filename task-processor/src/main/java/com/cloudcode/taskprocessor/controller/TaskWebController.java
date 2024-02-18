package com.cloudcode.taskprocessor.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.cloudcode.taskprocessor.model.TaskInfo;
import com.cloudcode.taskprocessor.service.TaskService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskWebController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public Mono<String> getTasks(Model model) {
        model.addAttribute("tasks", taskService.getTasks());
        return Mono.just("index");
    }

    @MessageMapping("task-stream")
    public Flux<TaskInfo> streamTasks() { // delay is added to simulate the streams in response
        return taskService.getTasks().delayElements(Duration.ofSeconds(1));
    }

}
