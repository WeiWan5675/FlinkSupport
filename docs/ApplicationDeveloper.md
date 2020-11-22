# FlinkSupport应用开发文档

## 简介

FlinkSupport对Flink程序开发中一些重复性的工作进行了简单的封装，通过FlinkSupport提供的脚手架，能够快速的进行Flink程序的开发。

## 接口说明

- FlinkSupport

  FlinkSupport程序顶层接口，定义了任务初始化及提交相关方法。

- SupportDataFlow

  FlinkSupport数据处理接口，主要目的为方便注解开发及插件化开发。

- StreamAppSupport<I_OUT,P_OUT>

  FlinkStream程序的Base类，在用户开发自己的Flink程序时，需要继承该类，并实现该类的三个抽象方法

    - open
    - process
    - output

- BatchAppSupport

  FlinkBatch程序的Base类，在用户开发Flink批处理程序时，需要继承该类。

- SupportAppContext

  FlinkSupport程序的上下文环境。

   - JobConfig
   - FlinkEnvConfig
   - RunOptions



## 开发

- 引入依赖

  ```xml
  <dependency>
      <groupId>com.weiwan</groupId>
      <artifactId>support-core</artifactId>
      <version>1.0</version>
      <scope>provided</scope> <!-- 打包时需要修改为provided -->
  </dependency>
  ```

- 实现脚手架抽象方法

  ```java
  public class TestSupportApp extends StreamAppSupport<String, String> {
  
      public DataStream<String> open(StreamExecutionEnvironment env, SupportAppContext context) {
          return env.readTextFile("support-app.yaml");
      }
  
      public DataStream<String> process(DataStream<String> stream, SupportAppContext context) {
          return stream;
      }
  
      public DataStreamSink output(DataStream<String> stream, SupportAppContext context) {
          return stream.print();
      }
  }
  ```

## 调试

FlinkSupport调试，需要创建FlinkSupport框架提供的SupportTestConsole进行调试，具体的调试代码如下：

```java
SupportTestConsole console = SupportTestConsole.newBuilder()
    .waitTestClass(TestSupportApp.class)
    .jobFile("support-app.yaml")
    .build();
console.run();
```

也可以通过注解SupportTest直接声明配置文件地址

```java
@SupportTest(jobFile = "support-app.yaml")
public class TestSupportApp extends StreamAppSupport<String, String> {
	。。。
	
    public static void main(String[] args) throws Exception {
    	SupportTestConsole console = new SupportTestConsole(TestSupportApp.class);
   		console.run();
    }
}
```

## 打包

下面为FlinkSupport程序打包最佳打包方式，由于FlinkSupport程序在提交时会将Flink相关的依赖提前准备好，所以用户程序在打包时，应将support-core、support-utils等依赖作用域调整为provided，避免打包出来体积过大。

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.2.0</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <shadedClassifierName>shaded</shadedClassifierName>
            </configuration>
        </execution>
    </executions>
</plugin>
```

## 其它
 暂无
 
