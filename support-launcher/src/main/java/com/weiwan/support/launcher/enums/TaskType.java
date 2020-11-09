package com.weiwan.support.launcher.enums;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/3 15:17
 * @Package: com.weiwan.support.launcher.enums.TaskType
 * @ClassName: TaskType
 * @Description:
 **/
public enum TaskType {
    STREAM("Flink Support Stream"),
    BATCH("Flink Support Batch"),
    UNKNOWN("Flink Support Unknown");

    private String type;

    public static TaskType getType(String appType) {
        TaskType[] values = values();
        if (appType != null) {
            for (TaskType value : values) {
                if (value.name().equalsIgnoreCase(appType)) {
                    return value;
                }
            }
        }
        return UNKNOWN;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    TaskType(String type) {
        this.type = type;
    }
}
