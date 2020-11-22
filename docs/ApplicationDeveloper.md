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



## 其它

