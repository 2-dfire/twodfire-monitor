package com.twodfire.timerMonitor.monitor;

import com.twodfire.timerMonitor.util.MethodInfo;
import com.twodfire.timerMonitor.util.QuestInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;


/**
 * User: edagarli
 * Email: lizhi@edagarli.com
 * 输出格式为
 * A[0-50]50ms
 *---B[20-30]30ms
 *-----C[25-28]3ms
 */
public class MonitorAround {

    Logger loggerMonitor = LoggerFactory.getLogger("com.twodfire.timerMonitor");//打印超时的方法栈的日志

    private int maxTime = 300;
    private ThreadLocal<QuestInfo> myThreadLocal = new ThreadLocal();

    public Object watchPerformance(ProceedingJoinPoint joinpoint) throws Throwable {
        Object result = null;
        QuestInfo questInfo = null;
        long startTime = 0L;
        long endTime = 0L;
        try {
            questInfo = (QuestInfo)this.myThreadLocal.get();
            if (questInfo == null) {
                questInfo = new QuestInfo(new LinkedList());
                questInfo.setStartTime(System.nanoTime());
                this.myThreadLocal.set(questInfo);
            }
            questInfo.increaseLevel();
            startTime = System.nanoTime();
        } catch (Throwable e) {
            this.loggerMonitor.error(e.getMessage(), e);
            this.myThreadLocal.remove();
        }
        try
        {
            result = joinpoint.proceed();
        } catch (Throwable e) {
            throw e;
        }

        try
        {
            endTime = System.nanoTime();
            questInfo.decreaseLevel();

            MethodInfo methodInfo = new MethodInfo(questInfo
                    .getLevel(), joinpoint
                    .getSignature().toString(),
                    Long.valueOf(startTime),
                    Long.valueOf(endTime));

            questInfo.getLinkedList().add(methodInfo);
            if (questInfo.getLevel() == 0) {
                this.myThreadLocal.remove();
                if (endTime - startTime > this.maxTime * 1000000)
                {
                    StringBuilder sbOut = new StringBuilder("TimeMonitor Warn:LongTime:\n");
                    sbOut.append(questInfo.toString());
                    this.loggerMonitor.warn(sbOut.toString());
                }
            }
        } catch (Throwable e) {
            this.loggerMonitor.error(e.getMessage(), e);
            this.myThreadLocal.remove();
        }
        return result;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTimer) {
        this.maxTime = maxTimer;
    }
}
