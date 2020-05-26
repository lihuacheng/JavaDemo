package com.demo.casDemo;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 1. Cas 底层原来是什么？
 *    1.1 比较交换(AtomicInteger.compareAndSet)
 *    1.2 引用了unSafe 类；
 *
 * 2. UnSafe 类是什么？
 *    2.1 是 Cas 的核心类，由于JAVA方法无法直接访问底层系统，需要通过本地（native）方法来访问，
 *        Unsafe 相当于一个后门，基于该类可以只直接操作特点的内存数据。
 *    2.2 UnSafe 类存在于 rt.jar sun.misc包中，其内部方法操作可以向C的指针一样操作内存，
 *        因为java中Cas操作的执行依赖于UnSafe类的方法
 *    2.3 UnSafe 类中的所有方法都是 native修饰的，也就是说UnSafe 类中的方法都直接调用操作系统
 *        底层资源执行相应任务；
 *
 * 3.  谈谈如何的理解UnSafe类；
 *      2.1 变量 valueOffset : 表示该变量值在内存中的偏移地址，因为UnSafe就是根据内存偏移地址获取数据；
 *      2.2 变量 value ：使用了 volatile 修饰，多线程中保证可见性和禁止指令重排；
 *
 * 4. 扩展
 *      4.1 Cas 全称 Compare-And-Swap,它是一条CPU并发原语，它的功能是判断内存某个位置的值是否为预期值，
 *          如果是则更改为新的值，这个过程是原子的（保证了原子一致性）；
 *      4.2 Cas 并发原语体现在JAVA语言中就是 sun.misc.UnSafe类中的各个方法。调用UnSafe类中的Cas方法，
 *          JVM会帮我们实现出Cas汇编指令。这是一种完全依赖于硬件的功能，通过它实现了原子操作。由于Cas是
 *          一种系统原语，原语属于操作系统用词范畴，是由若干个指令组成，用于完成某一个功能的一个过程，
 *          并且原语的执行必须是连续的，在执行过程中不允许被中断，也就是说Cas是一条CPU的原子指令，不会造
 *          成所谓的数据不一致问题。
 *      4.3 AtomicInteger.getAndIncrement()的实现：首先是do while循环，先在 do{} 通过valueOffset
 *          内存偏移地址获取value值,然后通过while() 条件里面去进行比较交换（compareAndSet），如果取得
 *          值等于我们的预期值时，则返回 true(!false) 结束循环，否则不等于预期值时，则再次通过
 *          valueOffset重新取值，直至取得的结果值和预期值一样,然后进行比较交换并返回对应的结果值。
 *          （由于使用了volatile修饰value变量和compareAndSet[cas]进行比较交换，所以保证的在多线程中变
 *          量的可见性和原子性）；
 *
 */
public class CASDemo {

    public static void main(String[] ars){
        AtomicInteger atomicInteger = new AtomicInteger(5);
        atomicInteger.getAndIncrement();
//        Map<String,String> hashmap = new ConcurrentHashMap<>();

        System.out.println(atomicInteger.compareAndSet(5,2020)+"\t current data:"+atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 1024)+"\t current data:"+atomicInteger.get());

    }
}
