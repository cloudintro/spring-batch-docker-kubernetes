package com.cloudcode.taskprocessor.model;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class TaskInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("task-id")
    private Integer taskId;

    @JsonProperty("task-name")
    private String taskName;

    @JsonProperty("task-desc")
    private String taskDesc;

    @JsonProperty("task-status")
    private String taskStatus;

    @JsonProperty("update-time")
    private ZonedDateTime updateTime;
}
