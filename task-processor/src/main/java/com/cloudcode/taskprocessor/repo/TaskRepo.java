package com.cloudcode.taskprocessor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudcode.taskprocessor.model.TaskInfo;
import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<TaskInfo, Integer> {
    List<TaskInfo> findByTaskNameAllIgnoreCase(String taskName);
    List<TaskInfo> findByTaskIdAndTaskNameAllIgnoreCase(Integer taskId, String taskName);
}
