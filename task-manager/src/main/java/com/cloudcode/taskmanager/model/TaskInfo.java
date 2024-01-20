package com.cloudcode.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskInfo {

    @JsonProperty("task-id")
    private Long taskId;

    @NotBlank(message = "task name is mandatory")
    @Size(min = 3, max = 15, message = "invalid task name")
    @JsonProperty("task-name")
    private String taskName;

    @NotBlank(message = "task description is mandatory")
    @Size(min = 5, max = 50, message = "invalid description")
    @JsonProperty("task-desc")
    private String taskDesc;

    @JsonProperty("task-status")
    private String taskStatus;

    @JsonProperty("update-time")
    private String updateTime;

    public TaskInfo(String taskName, String taskDesc) {
        this.taskName = taskName;
        this.taskDesc = taskDesc;
    }
}
