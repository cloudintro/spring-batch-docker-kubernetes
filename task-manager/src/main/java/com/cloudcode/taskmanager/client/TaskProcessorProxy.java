package com.cloudcode.taskmanager.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudcode.taskmanager.model.TaskInfo;

import jakarta.validation.Valid;

@FeignClient(name = "task-processor", url = "${app.task-processor.url:}")
public interface TaskProcessorProxy {
    @PostMapping("/task")
    ResponseEntity<TaskInfo> saveTask(@RequestBody @Valid TaskInfo taskInfo);

    @GetMapping("/task")
    ResponseEntity<List<TaskInfo>> getTaskStatus(@RequestParam(required = false, name = "task-id") Integer taskId,
            @RequestParam(required = false, name = "task-name") String taskName);
}
