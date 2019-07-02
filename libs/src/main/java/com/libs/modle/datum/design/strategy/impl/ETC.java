package com.libs.modle.datum.design.strategy.impl;


import com.libs.modle.datum.design.strategy.IStrategy;

/**
 * @author：mo
 * @data：2018/6/1 0001
 * @功能： 购买ETC的策略，1块钱能买2个
 */

public class ETC implements IStrategy {
    @Override
    public int getCount(int money) {
        return money*2;
    }
}
