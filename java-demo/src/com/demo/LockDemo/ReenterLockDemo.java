package com.demo.LockDemo;

import java.util.concurrent.locks.ReentrantLock;

class Phone implements Runnable{
    public synchronized void sendSMS(){
        System.out.println(Thread.currentThread().getName()+"\t invoked sendSMS()");
        sendEmail();
    }

    public synchronized void sendEmail(){
        System.out.println(Thread.currentThread().getName()+"\t #######invoked sendEmail()");
    }

    @Override
    public void run() {
        get();
    }

    ReentrantLock lock = new ReentrantLock();

    public void get(){
        // lock.lock 和 lock.unlock  无论加几次都必须一一配对
        lock.lock();
//        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t invoked get()");
            set();
        }finally {
            lock.unlock();
//            lock.unlock();
        }
    }

    public void set(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t #######invoked set()");
        }finally {
            lock.unlock();
        }
    }
}

/**
 * 可重入锁（也叫递归锁）
 *     指的是同一线程外层函数获取锁之后，内层递归函数仍然能获取该锁的代码，
 *     在同一个线程再外层方法获取锁的时候，再进入内层方法会自动获取锁
 *
 *     也即是说，线程可以进入任何一个它已经拥有的锁所同步着的代码块
 *
 * synchronized/ReentratnLock 是典型的可重入锁:
 *      线程可进入任何一个它已经拥有的锁，所同步的代码块
 */
public class ReenterLockDemo {

    public static void main(String[] args){

        System.out.println("---------------- Synchronized 可重入锁-----------------");
        Phone phone = new Phone();
        new Thread(()->{
            phone.sendSMS();
        },"T1").start();

        new Thread(()->{
            phone.sendSMS();
        },"T2").start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("---------------- ReentrantLock 可重入锁-----------------");
        Thread t3 = new Thread(phone);
        Thread t4 = new Thread(phone);

        t3.start();
        t4.start();
    }
}
