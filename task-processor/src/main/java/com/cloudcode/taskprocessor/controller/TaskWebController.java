package com.cloudcode.taskprocessor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.cloudcode.taskprocessor.service.TaskService;

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

}
