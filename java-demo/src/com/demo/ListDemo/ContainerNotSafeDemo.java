package com.demo.ListDemo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 集合类线程不安全问题
 *  1.ArrayList：数组 线程不安全；
 *  2.Vector：数组 线程安全（synchronize）；
 *  3.Collections.synchronizedList: 集合工具类 里面保证List集合线程安全的方法；
 *  4.CopyOnWriteArrayList: concurrent多线程工具包下的类，
 *      写时复制：先加锁 然后获取Object[]数组之后复制新的Object[]数据 操作完成之后
 *      重新设置修改引用指向 新的Object[]数组 设置完成之后 释放锁，这样做的好处是保
 *      证了copyOnWrite容器进行并发读而不需要加锁，也是一种读写分离的思想；
 *
 *  1.HashSet: 底层实现是HashMap 线程不安全（HashSet 的add方法就是把 传过去的值做完KEY, VALUE是一个Object常量对象）；
 *  2.Collections.synchronizedSet： 集合工具类 里面保证Set集合线程安全的方法；
 *  3.CopyOnWriteArraySet: concurrent多线程工具包下的类，底层实现就是CopyOnWriteArrayList；
 *
 *  1.HashMap: java7 使用的 数组+链表,java8 使用的 数据+链表+红黑树 默认初始化大小 16 负载因子 0.75；
 *  2.Hashtable: 线程不安全 数组+链表 线程安全（使用了 synchronize） 默认初始化大小 11 负载因子 0.75；
 *  3.Collections.synchronizedMap：集合工具类 里面保证Map集合线程安全的方法；
 *  4.ConcurrentHashMap: 线程安全 使用了分段锁；
 */
public class ContainerNotSafeDemo {

    public static void main(String[] args){
//        Map<String,String> map = new HashMap<>();
//        Map<String,String> map = new Hashtable<>();
//        Map<String,String> map = Collections.synchronizedMap(new HashMap<>());
        Map<String,String> map = new ConcurrentHashMap<>();
        for(int i = 0; i < 30; i++){
            new Thread(()->{
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }

    /**
     * Set
     */
    public static void setNotSafe() {
//        Set<String> set = new HashSet<>();
//        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet<>();
        for(int i = 0; i < 30; i++){
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
            },String.valueOf(i)).start();
        }
        new HashSet<>().add("a");
    }


    /**
     * List
     */
    public static void listNotSafe() {
//        List<String> list = new ArrayList<>();
//        List<String> list = new Vector<>();
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();
        for(int i = 0; i < 30; i++){
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
        //java.util.ConcurrentModificationException 并发修改异常
        /**
         * 1 故障现象
         *      java.util.ConcurrentModificationException 并发修改异常
         *
         * 2 导致原因
         *      并发争抢修改导致读写异常，参考我们的花名册签名情况。
         *      一个人正在写入，另外一个同学过来抢夺，导致数据不一致异常（也就是并发修改异常）；
         *
         * 3 解决方案
         *      3.1> new Vector();
         *      3.2> Collections.synchronizedList(new ArrayList<>());
         *      3.3> new CopyOnWriteArrayList<>();
         *
         * 4 优化建议
         *
         *
         */}
}
