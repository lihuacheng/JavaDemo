package com.demo.casDemo;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题：
 *  1.模拟ABA问题场景；
 *  2.ABA解决方式：AtomicStampedReference
 */
public class ABADemo {

    static AtomicReference<String> atomicReference = new AtomicReference<>("A");

    static AtomicStampedReference<String> atomicStampedReference = new AtomicStampedReference<>("A", 1);

    public static void main(String[] args){
        System.out.println("------------ABA问题的产生--------------");
        new Thread(()->{
            atomicReference.compareAndSet("A", "B");
            atomicReference.compareAndSet("B", "A");
        },"T1").start();

        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet("A", "C")+"\t"+atomicReference.get());
        },"T2").start();

        System.out.println("------------ABA问题的解决--------------");
        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第1次版本号："+stamp);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet("A","B",atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t第2次版本号："+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet("B","A",atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t第3次版本号："+atomicStampedReference.getStamp());
        },"T3").start();

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第1次版本号："+stamp);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result =  atomicStampedReference.compareAndSet("A", "C", stamp, stamp+1);
            System.out.println(Thread.currentThread().getName()+"\t 修改成否："+result+"\t 当前最新实际版本号："+atomicStampedReference.getStamp());
        },"T4").start();
    }
}
