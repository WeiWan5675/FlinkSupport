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
 * @Date: 2020/11/17 9:07
 * @Package: com.weiwan.support.launcher.enums.RunCmd
 * @ClassName: RunCmd
 * @Description:
 **/
public enum RunCmd {
    RUN,
    STOP,
    CANAL,
    INFO,
    LIST,
    SAVEPOINT;

    public static String print() {
        StringBuffer sb =new StringBuffer();

        RunCmd[] values = RunCmd.values();
        for (RunCmd value : values) {
            sb.append(value.name());
            sb.append("|");
        }
        String s = sb.toString();
        String substring = s.substring(0, s.lastIndexOf("|"));
        return substring;
    }
}
