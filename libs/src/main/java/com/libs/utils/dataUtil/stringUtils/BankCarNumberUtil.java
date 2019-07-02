package com.libs.utils.dataUtil.stringUtils;

import com.libs.utils.ResUtil;

import java.util.ArrayList;
import java.util.List;



/**
 * @ author：mo
 * @ data：2019/4/2：15:50
 * @ 功能：银行卡工具类
 */
public class BankCarNumberUtil {
    /**
     * 通过输入的卡号获得银行卡信息
     * 根据银行卡号判断是哪个银行的卡，依据是银行卡号的前6位数，称之为bin号。
     * * 我们把bin号转化为长整形，再把各个银行卡的bin号做成有序表。通过二分查找的方法，找到bin号在有序表的位置，然后读出银行卡的信息。
     */
    public static String getBankName(long binNum) {
        int index = 0;
        index = binarySearch(getBinNum(), binNum);
        if (index == -1) {
            return " ";
        }
        return getBinName().get(index);
    }

    /**
     * 获取所有银行卡前缀list
     */
    private static List<Long> getBinNum() {
        String binNum = ResUtil.getgetAssetsString("binNum.txt");
        String[] binArr = binNum.split(",");
        List<Long> lon = new ArrayList<>();
        for (int i = 0; i < binArr.length; i++) {
            if (i % 2 == 0) {
                lon.add(Long.parseLong(binArr[i]));
            }
        }
        return lon;
    }

    /**
     * 获取所有银行卡名称list
     */
    private static List<String> getBinName() {
        String binNum = ResUtil.getgetAssetsString("binNum.txt");
        String[] binArr = binNum.split(",");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < binArr.length; i++) {
            if (i % 2 != 0) {
                list.add(binArr[i]);
            }
        }
        return list;
    }


    //数量有上千条，利用二分查找算法来进行快速查找法
    public static int binarySearch(List<Long> srcArray, long des) {
        int low = 0;
        int high = srcArray.size() - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (des == srcArray.get(middle)) {
                return middle;
            } else if (des < srcArray.get(middle)) {
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }
        return -1;
    }
}