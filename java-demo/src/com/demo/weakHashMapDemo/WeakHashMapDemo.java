package com.demo.weakHashMapDemo;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * WeakHashMap:适用于高速缓存
 *      基于哈希表的Map 接口实现，具有弱键 。
 *      WeakHashMap中的条目将在其密钥不再普通使用时自动删除。
 *      更准确地说，给定密钥的映射的存在不会阻止该密钥被垃圾收集器丢弃，
 *      也就是可以最终确定，然后被回收。 当一个密钥被丢弃时，
 *      它的条目被有效地从Map上移除，所以这个类与其他Map实现有所不同。
 */
public class WeakHashMapDemo {

    public static void main(String[] args){
        myHashMap();
        myWeakHashMap();
    }

    public static void myHashMap(){
        HashMap<Integer,String> hashMap = new HashMap<>();
        Integer key = new Integer(1);
        String value = "HashMap";
        hashMap.put(key, value);
        key = null;
        System.out.println(hashMap);
        System.gc();
        System.out.println(hashMap);
    }

    public static void myWeakHashMap(){
        WeakHashMap<Integer,String> weakHashMap = new WeakHashMap<>();
        Integer key = new Integer(2);
        String value = "weakHashMap";
        weakHashMap.put(key, value);
        key = null;
        System.out.println(weakHashMap);
        System.gc();
        System.out.println(weakHashMap);
    }
}
