package com.cloudcode.taskprocessor.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.cloudcode.taskprocessor.model.TaskInfo;

import reactor.core.publisher.Flux;

@Repository
public interface TaskRepo extends ReactiveMongoRepository<TaskInfo, Long> {
    Flux<TaskInfo> findByTaskNameAllIgnoreCase(String taskName);

    Flux<TaskInfo> findByTaskIdAndTaskNameAllIgnoreCase(Long taskId, String taskName);

    Flux<TaskInfo> findByTaskStatus(String taskStatus);
}
