package com.libs.modle.datum.design.strategy;


import com.libs.modle.datum.design.strategy.impl.DTA;

/**
 * @author：mo
 * @data：2018/6/1 0001
 * @功能： 策略模式测试
 */

public class TestStrategy {
    public static void main(String[] args) {
        BitStrategy bitStrategy=new BitStrategy(new DTA());
        bitStrategy.getBitCount(10);
    }
}
