package com.demo.prodConsumerDemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//资源类
class ShareDate{

    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws InterruptedException {
        lock.lock();
        try{
            while(number != 0){
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName()+"\t 线程执行 increment()，number="+number);
            condition.signal();
        }finally{
            lock.unlock();
        }

    }

    public void decrement() throws InterruptedException {
        lock.lock();
        try{
            while(number != 1){
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName()+"\t 线程执行 decrement()，number="+number);
            condition.signal();
        }finally{
            lock.unlock();
        }
    }

    public synchronized void add() throws InterruptedException {
        while(number != 0){
            this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName()+"\t 线程执行 add()，number="+number);
        this.notify();
    }

    public synchronized void del() throws InterruptedException {
        while(number != 1){
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName()+"\t 线程执行 del()，number="+number);
        this.notify();
    }

    public int getNumber(){
        return number;
    }

}

/**
 * 题目：一个初始值为零的变量，两个线程对其交替操作，一个加1一个减1，来5轮
 *
 * 1. 线程操作资源类进行加减操作；
 * 2. 判断是否满足条件，满足则执行完成之后则唤醒其他线程，不满足则阻塞线程；
 * 3. 防止虚假线程唤醒  wait() 方法：中断和虚假唤醒是可能的，并且该方法应该始终在循环（while）中使用
 */
public class ProdConsumer_TraditionDemo {

    public static void main(String[] args){
//        lockMethod();
//        synchronizedMethod();

    }

    /**
     * Lock [Condition]await() [Condition]singal() 实现线程之间交替操作同一个变量（线程变量共享）
     */
    public static void lockMethod() {
        ShareDate shareDate = new ShareDate();
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                try {
                    shareDate.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"AAA").start();

            new Thread(()->{
                try {
                    shareDate.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"BBB").start();
        }
    }

    /**
     * Synchronized wait() notify() 实现线程之间交替操作同一个变量（线程变量共享）
     */
    public static void synchronizedMethod() {
        ShareDate shareDate = new ShareDate();
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                try {
                    shareDate.add();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"AAA").start();

            new Thread(()->{
                try {
                    shareDate.del();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"BBB").start();
        }
    }
}
