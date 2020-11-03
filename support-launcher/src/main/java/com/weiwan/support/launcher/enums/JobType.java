package com.weiwan.support.launcher.enums;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/3 15:17
 * @Package: com.weiwan.support.launcher.enums.JobType
 * @ClassName: JobType
 * @Description:
 **/
public enum JobType {
    STREAM("Flink Support Stream"),
    BATCH("Flink Support Batch"),
    UNKNOWN("Flink Support Unknown");

    private String type;

    public static JobType getType(String appType) {
        JobType[] values = values();
        if (appType != null) {
            for (JobType value : values) {
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

    JobType(String type) {
        this.type = type;
    }
}
