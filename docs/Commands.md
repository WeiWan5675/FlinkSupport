# FlinkSupport命令手册



## 主要命令

- run
- stop
- cancel
- info
- list
- savepoint

## 通用参数

通用参数适用任意类型的客户端,具体支持的参数如下

| 参数名称        | 默认值 | description                              | 备注             |
| --------------- | ------ | ---------------------------------------- | ---------------- |
| [-m , -mode]    | JOB    | support framework client runing env mode | 运行模式         |
| [-n , -name]    | 无     | job name                                 | 任务名称         |
| -logLevel       | INFO   | log level                                | 日志级别         |
| -flinkHome      | 无     | Flink Home                               | 默认环境变量获取 |
| -hadoopHome     | 无     | Hadoop Home                              | 默认环境变量获取 |
| -yarnHome       | 无     | Yarn Home                                | 默认环境变量获取 |
| -hiveHome       | 无     | Hive Home                                | 默认环境变量获取 |
| -myHome         | 无     | Flink Support HOme                       | 自动获取         |
| [-h , -help]    | 无     | help info                                | 帮助信息         |
| [-v , -version] | 无     | version info                             | 版本信息         |
| -D              | 无     | dynamic parameters                       | 动态参数         |

## 任务参数

包含任务运行必要参数.具体内容如下：

| 参数名称      | 默认值 | description                     | 备注         |
| ------------- | ------ | ------------------------------- | ------------ |
| -cmd          | 无     | run action                      | 执行的操作   |
| [-c , -conf]  | 无     | job conf file                   | 任务配置文件 |
| -resources    | 无     | job resources dir               | 任务资源目录 |
| -ow           | 无     | overwriter remote resources dir | 覆盖远程资源 |
| [-sp , -s]    | 无     | savepoint path                  | 检查点目录   |
| [-q , -queue] | 无     | yarn queue name                 | 资源队列名称 |
| -jid          | 无     | job id                          | 任务ID       |

## ETL模块参数

暂无,待完善

## 其它

暂无

