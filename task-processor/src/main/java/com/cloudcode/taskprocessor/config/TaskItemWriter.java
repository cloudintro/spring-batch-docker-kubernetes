package com.cloudcode.taskprocessor.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import com.cloudcode.taskprocessor.model.TaskInfo;
import com.cloudcode.taskprocessor.repo.TaskRepo;

@Log4j2
public class TaskItemWriter implements ItemWriter<TaskInfo> {

    @Autowired
    private TaskRepo taskRepo;

    @Override
    public void write(@NonNull Chunk<? extends TaskInfo> chunk) {
        log.info("writing task {}", chunk);
        taskRepo.saveAll(chunk);
        log.info(taskRepo.findAll());
    }

}
