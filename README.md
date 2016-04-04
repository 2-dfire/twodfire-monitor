# twodfire-monitor
调用超时监控组件

> 这是我公司内部使用的超时监控组件，现在开源出来。对于中小型企业来说，已经完全满足了。


## 介绍

当某个action或接口执行超过设定阈值时将把调用栈的各个步骤开销打印出来。


## 使用

* 你可以直接下载源码或者直接使用jar (https://github.com/2-dfire/twodfire-monitor/releases/tag/1.0)
* 配置文件（具体表达式自己根据项目需要配置）

``` 

    <bean id="monitorAround" class="com.twodfire.timerMonitor.monitor.MonitorAround">
        <property name="maxTime" value="300"/><!-- 最大的超时时间-->
    </bean>

    <aop:config>
        <aop:aspect id="testAspect" ref="monitorAround">
            <aop:pointcut id="timeMonitorPointcut"
                          expression="execution(* *..service*..*(..))"/>
            <aop:around pointcut-ref="timeMonitorPointcut" method="watchPerformance"/>
        </aop:aspect>
    </aop:config> 
        
```

## 开源协议

[MIT](https://github.com/2-dfire/twodfire-monitor/blob/master/LICENSE)


