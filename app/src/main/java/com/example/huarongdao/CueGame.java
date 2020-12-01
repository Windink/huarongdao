package com.example.huarongdao;

import android.graphics.Path;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CueGame {
    private ArrayList<OperateSort> stepCount;
    private ArrayList<String> fullResult;
    private HashMap<String,Integer> numCount;
    private boolean isFinish=false;//判断是否完成
    public ArrayList<String> getFullResult() {
        return fullResult;
    }

    public CueGame(ArrayList<Integer> originalStep)
    {
        fullResult=new ArrayList<>();
        stepCount=new ArrayList<>();
        numCount=new HashMap<>();
        OperateSort operateSort=new OperateSort();
        operateSort.infor=originalStep;
        operateSort.parent=null;
        ArrayList<Integer> exchange=new ArrayList<>(originalStep);
        displaceStep(exchange);
        stepCount.add(operateSort);
        numCount.put(exchange.toString(),0);
        operateList();
        stepCount.clear();
        numCount.clear();
    }
    //初始化移动的步骤
    private void initializeResult(OperateSort operateSort)
    {
        while (true)
        {
            fullResult.add(operateSort.infor.toString());
            operateSort=operateSort.parent;
            if(operateSort==null)
            {
                Collections.reverse(fullResult);
                return;
            }
        }

    }
    //算法主方法
    private void operateList()
    {
        int index=0;
        while (true)
        {
            if(isFinish)
            {
                initializeResult(stepCount.get(stepCount.size()-1));
                return;
            }
            if(stepCount.size()==index)
            {
                Log.i("TAG", "无解");
                return;
            }
            ArrayList<Integer> exchange=stepCount.get(index).infor;
            if(exchange.lastIndexOf(7)-exchange.indexOf(7)==4||exchange.lastIndexOf(7)-exchange.indexOf(7)==1)
            {
                checkNum(exchange, exchange.indexOf(7), exchange.lastIndexOf( 7), index);
            }
            checkNum(exchange, exchange.indexOf(7), index);
            checkNum(exchange, exchange.lastIndexOf( 7), index);
            index++;
        }
    }

    //判断棋子是否符合移动前提
    private   void checkNum(ArrayList<Integer> num, int start, int flag) {
        //System.out.println(flag);
        if (checkNumUp(start) == -4 &&!isFinish) {
            moveNum(num, start, -4, flag);
        }
        if (checkNumLeft(start) == -1 &&!isFinish) {
            moveNum(num, start, -1, flag);
        }
        if (checkNumDown(start) == 4&&!isFinish) {
            moveNum(num, start, 4, flag);
        }
        if (checkNumRight(start) == 1 &&!isFinish) {
            moveNum(num, start, 1, flag);
        }
    }
    private   void checkNum(ArrayList<Integer> num, int start, int end, int flag) {
        if (checkNumDown(start) == 4 && checkNumDown(end) == 4 &&!isFinish) {
            moveNum(num, start, end, 4, flag);
        }
        if (checkNumUp(start) == -4 && checkNumUp(end) == -4 &&!isFinish) {
            moveNum(num, start, end, -4, flag);
        }
        if (checkNumLeft(start) == -1 && checkNumLeft(end) == -1&&!isFinish)  {
            moveNum(num, start, end, -1, flag);
        }
        if (checkNumRight(start) == 1 && checkNumRight(end) == 1&&!isFinish) {
            moveNum(num, start, end, 1, flag);
        }
    }
    private  byte checkNumDown(int start) {
        if (start - 16 < 0) {
            return (byte) 4;
        }
        return (byte) 0;
    }
    private  byte checkNumUp(int start) {
        if (start - 4 >= 0) {
            return (byte) -4;
        }
        return (byte) 0;
    }
    private  byte checkNumRight(int start) {
        if ((start + 5) % 4 != 0) {
            return (byte) 1;
        }
        return (byte) 0;
    }

    private  byte checkNumLeft(int start) {
        if ((start + 4) % 4 != 0) {
            return (byte) -1;
        }
        return (byte) 0;
    }
    //模拟移动
    private  void moveNum(ArrayList<Integer> num, int start, int count, int flag) {
        int flags = num.get(start + count);
        if (num.get(start).equals(flags)) {
            return;
        }
        ArrayList<Integer> newSteps = new ArrayList<>(num);
        ArrayList<Integer> jug;
        OperateSort checkType;
        switch (flags) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 3:
                if (((count == 4 || count == -4)&&newSteps.lastIndexOf(flags)-newSteps.indexOf(flags)==4)||
                        ((count == 1 || count == -1)&&newSteps.lastIndexOf(flags)-newSteps.indexOf(flags)==1)) {
                    int b = newSteps.get(start);
                    newSteps.set(start, newSteps.get(start + 2 * count));
                    newSteps.set(start + 2 * count, b);
                    if (!judgeNumOne(newSteps)||!judgeNumTwo(newSteps)) {
                        return;
                    }
                     checkType = new OperateSort();
                    checkType.infor = newSteps;
                    checkType.parent = stepCount.get(flag);
                    jug=new ArrayList<>(newSteps);
                    displaceStep(jug);
                    numCount.put(jug.toString(),count);
                    stepCount.add(checkType);
                }
                break;
            case 6:
                int a = newSteps.get(start);
                newSteps.set(start, newSteps.get(start + count));
                newSteps.set(start + count, a);
                if (!judgeNumOne(newSteps)||!judgeNumTwo(newSteps)) {
                    return;
                }
                 checkType = new OperateSort();
                checkType.infor = newSteps;
                checkType.parent = stepCount.get(flag);
                jug=new ArrayList<>(newSteps);
                displaceStep(jug);
                numCount.put(jug.toString(),count);
                stepCount.add(checkType);
                break;
            default:
                break;
        }
    }
    //模拟移动
    private  void moveNum(ArrayList<Integer> num, int start, int end, int count, int flag) {
        //System.out.println(start+" "+end+" "+count);
        int flags = num.get(start + count);
        ArrayList<Integer> newSteps = new ArrayList<>(num);
        OperateSort checkType;
        ArrayList<Integer> jug;
        switch (flags) {
            case 0:
                if (num.get(end + count)==flags) {
                    int a = newSteps.get(start);
                    int b = newSteps.get(end);
                    newSteps.set(start, newSteps.get(start + 2 * count));
                    newSteps.set(end, newSteps.get(end + 2 * count));
                    newSteps.set(start + 2 * count, a);
                    newSteps.set(end + 2 * count, b);
                    if (!judgeNumOne(newSteps)||!judgeNumTwo(newSteps)) {
                        return;
                    }
                    if(newSteps.lastIndexOf(0)==18)
                    {
                        isFinish=true;
                    }
                    checkType = new OperateSort();
                    checkType.infor = newSteps;
                    checkType.parent = stepCount.get(flag);
                    stepCount.add(checkType);
                    jug=new ArrayList<>(newSteps);
                    displaceStep(jug);
                    numCount.put(jug.toString(),count);
                }
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                if ((end - start == 4 && num.get(end + count)==flags&& (count == 1 || count == -1))||
                        (end - start == 1 && num.get(end + count)==flags && (count == 4 || count == -4))) {
                    int a = newSteps.get(start);
                    int b = newSteps.get(end);
                    newSteps.set(start, newSteps.get(start + count));
                    newSteps.set(end, newSteps.get(end + count));
                    newSteps.set(start + count, a);
                    newSteps.set(end + count, b);
                    if (!judgeNumOne(newSteps) ||!judgeNumTwo(newSteps)) {
                        return;
                    }
                    checkType = new OperateSort();
                    checkType.infor = newSteps;
                    checkType.parent = stepCount.get(flag);
                    stepCount.add(checkType);
                    jug=new ArrayList<>(newSteps);
                    displaceStep(jug);
                    numCount.put(jug.toString(),count);
                }
                break;
            default:
                break;
        }
    }

    //查询方法，重复
    private  boolean judgeNumOne(ArrayList<Integer> nums) {
        ArrayList<Integer> jugeg=new ArrayList<>(nums);
        displaceStep(jugeg);
        String i;
        i = String.valueOf(numCount.get(jugeg.toString()));
        if(i.equals("null"))
        {
            return true;
        }
        return false;
    }
    //相似、对称
    private  boolean judgeNumTwo(ArrayList<Integer> nums) {
        ArrayList<Integer> judge1 = new ArrayList<>(nums);
        displaceStep(judge1);
        exchangeStep(judge1);
        String i;
        i = String.valueOf(numCount.get(judge1.toString()));
        if(i.equals("null"))
        {
            return true;
        }
        return false;
    }

    //置换和排序方法
    private void displaceStep(ArrayList<Integer> nums)
    {
    for (int i=0;i<nums.size();i++)
    {
        if(nums.get(i)==2||nums.get(i)==4||nums.get(i)==5)
        {
            nums.set(i,1);
        }
    }

    }
    public  void exchangeStep(ArrayList<Integer> nums)
    {
        for (int i=0;i<=16;i+=4)
        {
            int b=nums.get(i);
            nums.set(i,nums.get(i+3));
            nums.set(i+3,b);
            b=nums.get(i+1);
            nums.set(i+1,nums.get(i+2));
            nums.set(i+2,b);
        }
    }

    private class OperateSort{
        ArrayList<Integer> infor;
        OperateSort parent;
    }
}
