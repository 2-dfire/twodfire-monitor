package com.twodfire.timerMonitor.util;

/**
 * User: edagarli
 * Email: lizhi@edagarli.com
 */
public class MethodInfo {
    private int level;
    private String methodName;
    private Long startTime;
    private Long endTime;

    public MethodInfo(int level, String methodName, Long startTime, Long endTime) {
        this.level = level;
        this.methodName = methodName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
