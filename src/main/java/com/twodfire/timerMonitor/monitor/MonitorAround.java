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
        try {
            QuestInfo questInfo = myThreadLocal.get();
            if (questInfo == null) {
                questInfo = new QuestInfo(new LinkedList<MethodInfo>());
                questInfo.setStartTime(System.nanoTime());
                myThreadLocal.set(questInfo);
            }

            questInfo.increaseLevel();
            long startTime = System.nanoTime();
            result = joinpoint.proceed();
            long endTime = System.nanoTime();
            questInfo.decreaseLevel();
            MethodInfo methodInfo=new MethodInfo(
                    questInfo.getLevel(),/*层次*/
                    joinpoint.getSignature().toString(),/*方法名字*/
                    startTime,/*开始时间*/
                    endTime);/*结束时间*/

            questInfo.getLinkedList().add(methodInfo);
            if (questInfo.getLevel() == 0) {
                myThreadLocal.remove();
                if (( endTime-startTime) > maxTime*1000000) {
                    StringBuilder sbOut=new StringBuilder("TimeMonitor Warn:TimeOut:\n");
                    sbOut.append(questInfo.toString());
                    loggerMonitor.warn(sbOut.toString());
                }
            }
        } catch (Throwable e) {
            myThreadLocal.remove();
            throw e;
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
