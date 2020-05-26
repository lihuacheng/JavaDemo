package com.demo.LockDemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：
 * Synchronized 和 Lock 有什么区别？用新的Lock有什么好处？
 *
 * 1.原始构成
 * 1.1 synchronized 是关键字属于JVM层面。
 * monitorenter 进入同步方法块，也就是加锁
 * monitorexit 退出同步方法块，也就是解锁
 * （底层是通过monitor对象来完成，其实wait/notify等方法也依赖于monitor对象只有在同步块或方法中才能调用wait/notify等方法）
 * 1.2 lock 是具体类（java.util.concurrent.locks.lock）是API层面的锁
 *
 * 2.使用方法
 * 2.1 synchronized 不需要用户去手动释放锁，当synchronized代码执行完后系统会自动让现场释放对锁的占用；
 * 2.2 ReentrantLock 则需要用户去手动释放锁若没有主动释放锁，就有可能导致出现死锁现象。
 * 需要lock() 和 unlock() 方法配合try/finally语句块来完成
 *
 * 3.等待是否可中断
 * 3.1 synchronized 不可中断，除非抛出异常或者正常运行完成。
 * 3.2 ReentrantLock 可中断：
 * 3.2.1 设置超时方法 try lock(long timeout, TImeUnit unit)
 * 3.2.2 lock Interruptibly() 方法代码块中，调用interrupt()方法可中断
 *
 * 4.加锁是否公平
 * 4.1 synchronized 非公平锁
 * 4.2 ReentrantLock 两者都可以，默认非公平锁，构造方法可以传入boolean值，true为公平锁，false为非公平锁
 *
 * 5.锁绑定多个条件Condition(条件对象)
 * 5.1 synchronized 没有，随机唤醒一个线程或者全部线程；
 * 5.2 ReentrantLock 用来实现分组唤醒需要唤醒的线程们，可以精确唤醒；
 */
//资源类
class ShareResouce{

    private int number = 1;//执行计数器

    private Lock lock = new ReentrantLock();

    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5(){
        lock.lock();
        try{
            while(number != 1){
                c1.await();
            }
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName()+" 线程打印第"+i+"次");
            }
            number++;
            c2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    public void print10(){
        lock.lock();
        try{
            while(number != 2){
                c2.await();
            }
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName()+" 线程打印第"+i+"次");
            }
            number++;
            c3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    public void print15(){
        lock.lock();
        try{
            while(number != 3){
                c3.await();
            }
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName()+" 线程打印第"+i+"次");
            }
            number = 1;
            c1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
}

/**
 * 题目：多线程之间按顺序调用，实现A->B->C 三个线程循环交替执行，要求如下：
 * AA 打印5次，BB 打印10次，CC 打印15次
 * 紧接着
 * AA 打印5次，BB 打印10次，CC 打印15次
 * .....
 * 来10轮
 * 实现方式：
 * 1.通过Condition条件对象 进行分组控制；
 * 2.设置线程执行计数器；
 * 3.循环判断是否需要等待(await)；
 * 4.执行完成之后，唤醒指定线程继续工作（signal）
 * 5.最后一个线程指定唤醒第一线程，形成回环；
 */
public class SyncAndLockDemo {

    public static void main(String[] args){
        ShareResouce shareResouce = new ShareResouce();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareResouce.print5();
            }
        },"AA").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareResouce.print10();
            }
        },"BB").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareResouce.print15();
            }
        },"CC").start();
    }
}
