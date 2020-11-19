# Fkink Support

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

## 交流

　　有想一起交流的同学可以扫下面的二维码添加我的微信，一起学习~~一起写代码~~谢谢！。

<div align="center"><img width="150" height="150" src="https://github.com/WeiWan5675/FlinkSupport/blob/master/docs/pictures/wx.jpg"/></div>

## 简介

　　FlinkSupport是一个基于Flink框架，采用Java语言进行开发的支持类框架，框架主要分为几个模块，包含快速开发模块、数据ETL模块、FlinkSql交互式任务模块、FlinkUtils模块，通过对日常工作中Flink框架常用场景进行封装，能够提高框架使用的便利性，提供插件化的Flink开发能力。

　　FlinkSupport提供了一些快速开发的能力，能够帮助开发者快速进行程序开发，极大的降低了开发成本。并且框架提供了ETL数据插件的相关接口，使用ETL模块，能够快速将数据ETL任务通过配置文件+ETL插件的形式进行数据落地。

　　目前FlinkSupport仅支持Flink-1.11.1以上版本，后期会支持Flink1.9+

## 快速开始

- **要求**

  - hadoop相关环境（运行） 2.6+ 
  - flink (运行) 1.9+
  - jdk 1.8+
  - maven 3.4.5+

- **Clone本项目**

  ```shell
  git clone git@github.com:WeiWan5675/FlinkSupport.git
  ```

- **打包**

  ```shell
  clean package install -Dmaven.test.skip=true -f pom.xml
  ```

- **解压**

  ```
  tar -zxvf FlinkSupport-1.0.tar.gz -C FlinkSupport-1.0
  ```

- **部署运行**

  - 初始化

    第一次运行，请执行以下命令，需要配置FLINK_HOME HADOOP_HOME 环境变量，如未配置，可以手动

    ```shell
    $FLINK_SUPPORT_HOME/bin/flink-support init [-hadoopHome "" -flinkHome ""]
    ```

  - 任务提交

    ```shell
    $FLINK_SUPPORT_HOME/bin/support-submit -c ./resource_dir/app-conf.yaml -resources resource_dir
    ```

  - 任务停止

    ```shell
    $FLINK_SUPPORT_HOME/bin/flink-support stop jobId
    ```

- **程序开发**

  - 引入依赖

    ```xml
        <dependency>
        	<groupId>com.weiwan</groupId>
        	<artifactId>support-core</artifactId>
        </dependency>
        <dependency>
        	<groupId>com.weiwan</groupId>
        	<artifactId>etl-framework</artifactId>
        </dependency>
        <dependency>
        	<groupId>com.weiwan</groupId>
        	<artifactId>support-utils</artifactId>
        </dependency>
    ```

  - 开发

    具体的程序开发相关说明请查看快速开发[说明文档](docs/ApplicationDeveloper.md)

    　　[普通Flink程序Demo](support-test/src/main/java/com/weiwan/tester/run/TesterApp.java)

    　　[ETL插件](support-etl-framework/etl-plugins/example_plugin/src/main/java/com/weiwan/support/plugins/reader/ExampleReader.java)

- 其它

  FlinkSupport支持相关运行命令请查看[命令手册](docs/Commands.md)

# ETL模式

　　ETL模式，通过对Flink相关能力进行封装，基于InputFormatSource、OutputFormatSink、MapFunction 抽象出三大数据插件 Reader、Processer、Writer。

　　通过插件化，可以快速的在异构数据源中进行数据同步，数据处理等操作。

 - **现阶段计划支持的插件**

   | 数据源类型    | 说明文档                               | 备注            |
   | ------------- | -------------------------------------- | --------------- |
   | MySql         | [Reader]()    [Writer]()    [BinLog]() | 支持Binlog      |
   | Mongo         | [Reader]()    [Writer]()    [Oplog]()  | 支持Oplog       |
   | File          | [Reader]()    [Writer]()               | 支持断点续传    |
   | HDFS          | [Reader]()    [Writer]()               |                 |
   | FTP           | [Reader]()    [Writer]()               |                 |
   | Hbase         | [Reader]()    [Writer]()               | 支持Bulkload    |
   | Kafka         | [Reader]()    [Writer]()               | 支持1对1，多对1 |
   | JDBC          | [Reader]()    [Writer]()               | 通用JDBC        |
   | Hive          | [Reader]()    [Writer]()               | 自动创建表      |
   | Oracle        | [Reader]()    [Writer]()               |                 |
   | ElasticSearch | [Reader]()    [Writer]()               |                 |
   | ClickHouse    | [Reader]()    [Writer]()               |                 |

   由于插件开发比较费时间，所以插件的支持是一个长期且耗时的事情，关于自定义插件请查看[插件开发说明](docs/PluginDeveloper.md)

# 模块

　　FlinkSupport模块划分主要分为两部分，包括面向用户快速开发模块以及FlinkSupport自身的运行时模块。

 - **面向用户(快速开发模块)**
    - support-core 
    - support-etl-framework
    - support-utils
 - **FlinkSupport(运行时模块)**
    - support-runtime
    - support-launcher
    - support-monitor

　　具体的模块说明及相关的设计文档请查看[设计文档](docs/FlinkSupportDesign.md)

## 应用配置

- **默认配置**

  ```yml
  ##########################################################
  # FlinkSupport默认配置文件,包含以下内容:
  # 1. Support相关变量配置
  # 2. 大数据组件的ENV变量配置
  # 3. Flink任务相关参数设置
  # 4. 其它
  # 默认配置文件中，所有选项都可以在app-conf.yaml中进行覆盖，也可以通过修改默认配置文件，达到全局任务配置修改的目的
  ##########################################################
  
  HADOOP_HOME:
  YARN_HOME:
  HIVE_HOME:
  FLINK_HOME:
  FLINK_VERSION: 1.11.1
  SCALA_VERSION: 2.11
  HADOOP_USER_NAME: easylife
  
  SUPPORT_TASK_LOGDIR: /tmp/flink_support/logs
  
  flink:
    task:
      type: stream
      name: FlinkApplication
      common:
        parallelism: 1 #并行度
        restartMode: none #fixed-delay | failure-rate | none  默认fixed-delay
        restartNum: 1  #重启次数  默认3
        restartInterval: 30000  #重启延迟  默认30S
        restartFailMaxNum: 1 #最大重启失败次数
        queue: root.default
      batch:
        sessionTimeout:     #保存作业的中间结果的超时时间 暂未启用
      stream:
        timeCharacteristic:    #流处理的事件模式  默认processTime eventTime
      checkpoint:
        enable: false       #是否启用检查点
        interval: 60000         #检查点间隔  单位毫秒
        timeout: 60000       #检查点超时时间 单位毫秒
        mode: EXACTLY_ONCE #检查点模式: AT_LEAST_ONCE  EXACTLY_ONCE
        minInterval: 500 #最小检查点间隔 单位毫秒
        maxConcurrent: 1   #z最多有多少checkpoint可以在运行
        externalized:
          enable: false    #是否开启checkpoint的外部持久化
          cleanUp: DELETE_ON_CANCELLATION #DELETE_ON_CANCELLATION  自动删除   RETAIN_ON_CANCELLATION 保留
        onFail: true  #当checkpoint发生错误时,是否认为任务失败 true 失败  false 拒绝checkpoint继续任务
      stateBackend:
        type: Memory #三种 Memory  FileSystem  RocksDB
        async: true #仅在配置为Memory FileSystem 时生效 RocksDB默认为异步
        path:  #支持hdfs路径 或者本地文件路径 hdfs://namenode:40010/flink/checkpoints  file:///data/flink/checkpoints
  
  
  app:
    class: com.weiwan.tester.run.TesterApp
    name: Support Application
    etlMode: false
    sqlMode: false
  
  ```

  

- **ETL配置**

  ```yaml
  ###########################################
  # FlinkSupport-Etl模式模式配置文件
  # 1. 定义插件
  # 2. 重写默认应用配置文件
  ###########################################
  flink:
    task: #Flink相关任务配置
      type: stream
  
  app: #应用相关配置
    name: SupportEtlApplication
    etlMode: true
    sqlMode: false
  
  etl:
    reader:
      name: ExampleReader #Reader插件
      class: com.weiwan.support.plugins.reader.ExampleReader
      parallelism: 1
      example:
        readereVar: 1000
    processer:
      name: ExampleProcesser #Processer插件
      class: com.weiwan.support.plugins.processer.ExampleProcesser
      parallelism: 1
      example:
        channelVar: channel_var
    writer:
      name: ExampleWriter #Writer插件
      class: com.weiwan.support.plugins.writer.ExampleWriter
      parallelism: 1
      example:
        writerVar: writer_var
  ```

  

- **其它**

  　　配置文件由三部分组成，Flink配置、Application配置、ETL模块配置，关于配置文件详细的内容以及支持的参数等可以查看[应用配置文档](docs/AppConfig.md)。


# 注解支持

　　FlinkSupport支持注解主要集中自动化接入数据方面，详见FlinkSupport[注解说明](docs/Annotations,md)

## 关于FlinkSupport

　　关于该框架以后的维护，主要考虑从几个方向进行，框架代码优化、数据插件、注解支持、监控及控制台支持Flink1.9+等。

　　在开发这个框架过程中，由于能力有限，代码写的比较简陋，我会继续努力，如果有大佬可以指点下，万分感激。

## License
Apache License 2.0, see [LICENSE](LICENSE).

