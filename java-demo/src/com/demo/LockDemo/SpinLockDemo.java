package com.demo.LockDemo;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁：是指尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁，
 *        这样的好处是减少线程上下文切换的消耗，缺点是循环会消耗CPU
 *
 * 题目：实现一个自旋锁
 *
 * 自旋锁的好处：循环比较获取直到成功为止，没有类似wait的阻塞
 *
 * 通过CAS 操作往常自旋锁，A线程先进来调用myLock 方法自己持有锁5秒钟，B随后进来后发现
 * 当前有线程持有锁，不是NULL，所以只能通过自旋等待，直到A释放锁后B随后抢到
 */
public class SpinLockDemo {

    //原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName()+"\t 线程 正在自旋等待中...");
        //使用cas 循环 比较交换
        while(!atomicReference.compareAndSet(null,thread)){
//            System.out.println(thread.getName()+"\t 线程 正在自旋等待中...");
        }
        System.out.println(thread.getName()+"\t 线程 已经获取锁");
    }

    public void unLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(thread.getName()+"\t 线程 已经释放锁");
    }

    public static void main(String[] args){
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(()->{
            spinLockDemo.lock();
            //暂时线程5秒钟 模拟线程等待执行过程
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unLock();
        },"AA").start();

        //暂定线程1秒钟 保证AA线程先获取锁
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            spinLockDemo.lock();
            //暂时线程5秒钟 模拟线程等待执行过程
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unLock();
        },"BB").start();
    }
}
