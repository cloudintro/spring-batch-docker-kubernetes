package com.cloudcode.taskprocessor.config;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import com.cloudcode.taskprocessor.model.TaskInfo;
import com.cloudcode.taskprocessor.repo.TaskRepo;

public class TaskItemWriter implements ItemWriter<TaskInfo> {

    @Autowired
    private TaskRepo taskRepo;

    @Override
    public void write(@NonNull Chunk<? extends TaskInfo> chunk) throws Exception {
        taskRepo.saveAll(chunk);
    }

}
