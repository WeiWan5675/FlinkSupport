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
package com.weiwan.support.launcher.submit;

import org.apache.hadoop.yarn.client.api.YarnClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author: xiaozhennan
 * @Date: 2020/10/27 11:14
 * @Package: com.weiwan.support.launcher.submit.JobSubmiter
 * @ClassName: JobSubmiter
 * @Description:
 **/
public class JobSubmiterFactory {
    private static Logger logger = LoggerFactory.getLogger(JobSubmiterFactory.class);

    public static JobSubmiter createYarnSubmiter(YarnClient yarnClient) {
        return new YarnJobSubmiter(yarnClient);
    }

    public static JobSubmiter createKubernetesSubmiter() {
        return new KubernetesJobSubmiter();
    }

}
