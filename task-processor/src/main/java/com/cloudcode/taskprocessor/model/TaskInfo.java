package com.cloudcode.taskprocessor.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document
@Getter
@Setter
@ToString
public class TaskInfo {

    @Id
    @JsonProperty("task-id")
    private Long taskId;

    @JsonProperty("task-name")
    private String taskName;

    @JsonProperty("task-desc")
    private String taskDesc;

    @JsonProperty("task-status")
    private String taskStatus;

    @JsonProperty("update-time")
    private String updateTime;
}
