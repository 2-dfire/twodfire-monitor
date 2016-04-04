package com.twodfire.timerMonitor.util;

import java.util.LinkedList;

/**
 * User: edagarli
 * Email: lizhi@edagarli.com
 */
public class QuestInfo {
    private int level=0;
    private long startTime;
    private LinkedList<MethodInfo> linkedList;

    public QuestInfo(LinkedList<MethodInfo> linkedList) {
        this.linkedList = linkedList;
    }

    public LinkedList<MethodInfo> getLinkedList() {
        return linkedList;
    }

    public void setLinkedList(LinkedList<MethodInfo> linkedList) {
        this.linkedList = linkedList;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public void increaseLevel()
    {
        this.level++;
    }
    public void decreaseLevel()
    {
        this.level--;
    }

    @Override
    public String toString(){
        try{
            return printTree(linkedList,0,linkedList.size()-1);
        }catch (Exception e)
        {
            return "MethodInfo.toString() Error! ";
        }
    }

    /**
     * 叶子节点是+-----开头  否则是以-------+开头  并且按层数缩进
     * @param isLeaf
     * @param methodInfo
     * @return
     */
    private String getAappendString(boolean isLeaf/*是否叶子节点*/,MethodInfo methodInfo)
    {
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<methodInfo.getLevel();i++)
        {
            stringBuilder.append("    ");//增加缩进
        }
        if(isLeaf)
        {
            stringBuilder.append("+---[");
        }else {
            stringBuilder.append("---+[");
        }
        stringBuilder.append((methodInfo.getStartTime()-this.getStartTime())/1000000).append("-");
        stringBuilder.append((methodInfo.getEndTime()-this.getStartTime())/1000000).append("]");
        stringBuilder.append((methodInfo.getEndTime()-methodInfo.getStartTime())/1000000).append("ms ");
        stringBuilder.append(methodInfo.getMethodName()).append("\n");
        return stringBuilder.toString();
    }

    /**
     * linkList 是后序遍历树获得的 其中每个节点有层数信息（最顶上的是0层 下面是 1 2..） 可以通过递归它 获得前序遍历的顺序 并打印
     * @param linkedList
     * @param startIndex  开始的标记位 包含
     * @param endIndex  结束的标记位 包含
     * @return
     */
    private String printTree( LinkedList<MethodInfo> linkedList,int startIndex ,int endIndex)//初始为 0   size-1
    {
        StringBuilder stringBuilder=new StringBuilder();
        int headLevel=linkedList.get(endIndex).getLevel();
        if(startIndex==endIndex) {
            stringBuilder.append(getAappendString(true, linkedList.get(endIndex)));//叶子节点
            return  stringBuilder.toString();
        }
        else {
            stringBuilder.append(getAappendString(false,linkedList.get(endIndex)));
        }
        //打印完头结点 从前往后着个扫描level低一级的树
        int nextHeadLevel=headLevel+1;//下一层的头结点的层数  最头上的层数的是0 下一层是1
        int tempStartIndex=startIndex;
        for(int i= startIndex;i<endIndex;i++)
        {
            if(linkedList.get(i).getLevel()==nextHeadLevel)//找到了下一层树的头  ps:如果有跳跃层级的现象 说明原始的list有问题 就不会被打印了
            {
                stringBuilder.append(printTree(linkedList,tempStartIndex,i));//递归
                tempStartIndex=i+1;
            }
        }
        return stringBuilder.toString();
    }
}
