package com.szyooge.util;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 排序
 * @ClassName: SortUtil
 * @author quanyou.chen
 * @date: 2017年5月24日 上午9:25:54
 * @version  v 1.0
 */
public class SortUtil {
    /**
     * 字典排序
     * @author quanyou.chen
     * @date: 2017年5月24日 上午9:26:06
     * @Title: dictionarySort
     * @return String 返回值
     */
    public static String dictionarySort(String... strs) {
        ArrayList<String> list = new ArrayList<String>();
        for(String str : strs){
            list.add(str);
        }
        Collections.sort(list);
        StringBuilder sb = new StringBuilder();
        for(String str : list) {
            sb.append(str);
        }
        return sb.toString();
    }
}
