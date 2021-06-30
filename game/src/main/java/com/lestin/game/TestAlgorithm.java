package com.lestin.game;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: TestAlgorithm
 * @Description: 排序算法
 * @Author: lestin.yin
 * @CreateDate: 5/20/21 4:24 PM
 * @Version:
 */
class TestAlgorithm {

    public static void main(String[] args) {
//        insertSort(new int[]{3, 54, 2, 7, 22, 6, 21, 10});

        getStatistics("AAbbee");
    }

    //选择排序 遍历完再交换 记住位置
    public static void choiceRank(int[] list) {
        for (int j = 0; j <= list.length - 1; j++) {
            int temp = list[j];
            int choicePosition = j;
            //遍历记住位置
            for (int k = j + 1; k <= list.length - 1; k++) {
                if (list[k] < temp) {
                    temp = list[k];
                    choicePosition = k;
                }
            }
            //赋值给头一个位置
            list[choicePosition] = list[j];
            list[j] = temp;
        }
        System.out.println(Arrays.toString(list) + " selectSort");
    }

    //选择排序 遍历过程中交换
    public static void choiceRanks(int[] list) {
        for (int j = 0; j <= list.length - 1; j++) {
            int temp;
            for (int k = j + 1; k <= list.length - 1; k++) {
                if (list[k] < list[j]) {
                    temp = list[k];
                    list[k] = list[j];
                    list[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(list) + " selectSort");
    }

    //冒泡排序
    public static void bubbleSort(int[] list) {
        int temp = 0;
        for (int j = 0; j < list.length; j++) {
            for (int k = 0; k < list.length - 1 - j; k++) {
                if (list[k] > list[k + 1]) {
                    temp = list[k];
                    list[k] = list[k + 1];
                    list[k + 1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(list) + " bubbleSort");
    }
    //插入排序
    public static void insertSort(int[] list) {
        for (int i = 1 ; i < list.length ; i++) {
            int keyPosition = list[i];
            int j = i - 1;
            while (j >= 0 && keyPosition < list[j]) {
                list[j+1] = list[j];
                j--;
            }
            list[j+1] = keyPosition;
        }
        System.out.println(Arrays.toString(list) + " insertSort");
    }

    public static void getStatistics(String normalText) {
//        if (TextUtils.isEmpty(normalText)) {
//            return;
//        }
        Map<Character,Integer> allDataMap = new HashMap<>();

        for (int i = 0; i < normalText.length() ; i++) {
            char charData = normalText.charAt(i);
            int existNumber = allDataMap.get(charData) == null ? 0 : allDataMap.get(charData);
            allDataMap.put(charData,existNumber+1);
//            System.out.println(String.valueOf(charData).toUpperCase());
            System.out.println(Character.isUpperCase(charData));
        }
    }


}
