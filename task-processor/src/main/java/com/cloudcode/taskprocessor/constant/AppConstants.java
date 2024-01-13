package com.cloudcode.taskprocessor.constant;

public final class AppConstants {

    private AppConstants() {

    }

    public enum TASK_STATUS {
        CREATED, PROCESSING, DELETED, COMPLETED, EXPIRED;
    }

}
