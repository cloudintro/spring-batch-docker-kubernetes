package com.cloudcode.taskprocessor.constant;

import java.time.format.DateTimeFormatter;

public final class AppConstants {

    private AppConstants() {

    }

    public enum TASK_STATUS {
        CREATED, PROCESSING, DELETED, COMPLETED, EXPIRED;
    }

    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

}
