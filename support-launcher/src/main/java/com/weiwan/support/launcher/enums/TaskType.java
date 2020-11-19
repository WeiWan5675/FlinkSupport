/*
 *      Copyright [2020] [xiaozhennan1995@gmail.com]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *      http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
