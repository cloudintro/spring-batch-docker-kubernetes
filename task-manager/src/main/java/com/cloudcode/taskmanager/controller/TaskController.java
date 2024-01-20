package com.cloudcode.taskmanager.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<List<TaskInfo>> getTaskStatus(
            @RequestParam(required = false, name = "task-id") Integer taskId,
            @RequestParam(required = false, name = "task-name") String taskName) {
        return new ResponseEntity<>(taskService.getTaskStatus(taskId, taskName), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<List<TaskInfo>> createBatchTask(@RequestParam("file") MultipartFile fileStream) {
        if (Objects.isNull(fileStream) || fileStream.getSize() == 0) {
            throw new RuntimeException("Invalid or empty file");
        }
        String fileName = !StringUtils.hasLength(fileStream.getOriginalFilename()) ? fileStream.getName()
                : fileStream.getOriginalFilename();
        log.info("Received {} ", fileName);

        List<TaskInfo> requestTaskInfo = new ArrayList<>();
        try ( // read content from file
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream.getInputStream()))) {
            reader.lines().forEach(line -> buildTaskInfo(line, requestTaskInfo));
        } catch (IOException ex) {
            log.error("Failed to read data from file {}", ex.getMessage());
            throw new RuntimeException("Failed to read data from file " + ex.getMessage());
        }
        // codeto create task
        List<TaskInfo> responseTaskInfo = new ArrayList<>();
        for (TaskInfo taskInfo : requestTaskInfo) {
            try {
                responseTaskInfo.add(taskService.createTask(taskInfo));
            } catch (Exception ex) {
                taskInfo.setTaskStatus("FAILED");
                responseTaskInfo.add(taskInfo);
            }
        }
        /*
         * OutputStream outputStream = new FileOutputStream(fileName);
         * OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
         * responseTaskInfo.forEach(d -> {
         * try {
         * streamWriter.write(d.toString());
         * } catch (IOException e) {
         * e.printStackTrace();
         * }
         * });
         */
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<>(responseTaskInfo, headers, HttpStatus.OK);
    }

    private void buildTaskInfo(String line, List<TaskInfo> taskInfos) {
        String[] lineData = line.split(",");
        if (!StringUtils.hasText(line) || lineData.length < 2) {
            return;
        }
        taskInfos.add(new TaskInfo(lineData[0], lineData[1]));
    }

}
