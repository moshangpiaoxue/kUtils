package com.libs.modle.datum.algorithm.sort;

/**
 * @author：mo
 * @data：2018/3/1 0001
 * @功能：排序算法
 */

public class Sort {
    /**
     * 冒泡排序:，双重遍历，外层遍历整个数组，内层遍历还没排序好的一部分
     * 相邻元素两两比较，大的往后放，第一次完毕，最大值出现在了最大索引处
     * 找到相对最大的值，放到数组最后，然后继续循环，直到找不到了
     * 如需从大到小排列，修改if语句的大于符号即可
     *
     * @param arr
     */
    public static void maoPaoSort(int[] arr) {
        boolean flag = true;
        while (flag) {
            flag = false;
            //循环整个数组 外层循环控制排序趟数
            for (int i = 0; i < arr.length; i++) {
                //二次循环，把最大的数值置换到数组最后的位置，arr.length - 1 - i意味着，下次循环的时候最后一个位置已经是最大的了，就不算他了
                //内层循环控制每一趟排序多少次
                for (int j = 0; j < arr.length - 1 - i; j++) {
                    //两个相邻数值对比，>的作用是找到最大的放后面  两两数值判断
                    if (arr[j] > arr[j + 1]) {
                        //生成临时变量保存数据
                        int temp = arr[j];
                        //置换
                        //把小的值交换到前面
                        arr[j] = arr[j + 1];
                        //把大的值交换到后面
                        arr[j + 1] = temp;
                    }

                }
                //优化判断
                if (!flag) {// 若没有交换则排序完成，直接跳出
                    break;
                }
            }
        }
        for (int k : arr) { //foreach循环输出
            System.out.println(k);
        }
//        Integer[] ints = new Integer[]{82, 69, 75, 89, 90, 87, 55, 67, 99, 100, 80};
//        //双重遍历
//        for (int i = 0; i < ints.length - 1; i++) {
//            for (int j = i + 1; j < ints.length; j++) {
//                if(ints[i] > ints[j]){
//                    //不通过介质，改变两个变量的值
//                    ints[i] = ints[i] ^ ints[j];
//                    ints[j] = ints[i] ^ ints[j];
//                    ints[i] = ints[i] ^ ints[j];
//                }
//            }
//        }
    }

    /**
     * 选择排序
     *
     * @param arr
     */
    public static void xuanZeSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    /**
     * 插入排序
     * @param a
     */
    public static void insertSort(int[] a) {
        int i, j, insertNote;// 要插入的数据
        for (i = 1; i < a.length; i++) {// 从数组的第二个元素开始循环将数组中的元素插入
            insertNote = a[i];// 设置数组中的第2个元素为第一次循环要插入的数据
            j = i - 1;
            while (j >= 0 && insertNote < a[j]) {
                a[j + 1] = a[j];// 如果要插入的元素小于第j个元素,就将第j个元素向后移动
                j--;
            }
            a[j + 1] = insertNote;// 直到要插入的元素不小于第j个元素,将insertNote插入到数组中
        }
    }


}
