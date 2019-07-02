package com.libs.modle.datum.design.strategy;


/**
 * @author：mo
 * @data：2018/6/1 0001
 * @功能：购买代币策略
 */

public class BitStrategy {
    private IStrategy iStrategy;
    /**
     * 设置要用哪种算法
     *
     * @param iStrategy
     */
    public BitStrategy(IStrategy iStrategy) {
        this.iStrategy = iStrategy;
    }

    /**
     * 设置要用哪种算法
     *
     * @param iStrategy
     */
    public void setiStrategy(IStrategy iStrategy) {
        this.iStrategy = iStrategy;
    }

    /**
     * 计算能买多少个代币
     *
     * @param money
     * @return
     */
    public int getBitCount(int money) {
        return iStrategy.getCount(money);
    }
}
