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
package com.weiwan.support.utils.flink.conf;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/6 10:38
 * @Package: com.hopson.dc.flink.common.operator
 * @ClassName: FlinkEnum
 * @Description:
 **/
public enum FlinkEnum {

    CHECKPOINT_RETAIN_ON_CANCELLATION("RETAIN_ON_CANCELLATION", "保留"),
    CHECKPOINT_DELETE_ON_CANCELLATION("DELETE_ON_CANCELLATION", "自动删除"),


    //    fixed-delay #fixed-delay | failure-rate | none
    TASK_RESTART_MODE_FIXED_DELAY("fixed-delay", "固定间隔重启"),
    TASK_RESTART_MODE_FAILURE_RATE("failure-rate", "失败率重启策略"),
    TASK_RESTART_MODE_NONE("none", "不重启,直接失败");

    private String code;
    private String msg;

    FlinkEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static FlinkEnum valueOfCode(String code) {
        FlinkEnum[] values = FlinkEnum.values();
        for (FlinkEnum value : values) {
            String tc = value.getCode();
            if (tc.equals(code)) {
                return value;
            }
        }
        return null;
    }

}
