package com.mmall.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanbingdepingguo on 2017/7/31.
 */
public class Test {
    public static void main(String[] args) {
        String str1=new String("123,1213,312");
        String[] str=str1.split(",");
        List<String> lists=new ArrayList<String>();
        for(String item:str){
            lists.add(item);

        }
        System.out.println(lists.get(1));

    }
}
