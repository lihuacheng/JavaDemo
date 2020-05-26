package com.demo.LockDemo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  非公平锁和公平锁
 *  1.是什么
 *      公平锁：是指多个线程按照申请锁的顺序来获取锁，类似于排队打饭，先来后到。（维护一个线程锁的队列）
 *      非公平锁：是指多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后申请的线程比先申请的线程优先
 *               获取此锁在高并发的情况下有可能会找出优先级反转或者饥饿现象。
 *  2.在哪里如何使用
 *      非公平锁和公平锁：java.util.concurrent.locks（并发包）中 ReentrantLock 的创建可以指定构
 *                      造函数的boolean类型来得到公平锁或非公平锁，默认是false即（非公平锁）。
 *  3.两者区别
 *      公平锁：就很公平，在并发的环境中，每一个线程再获取锁时会先查看此锁维护的等待队列，如果为空或者
 *             当前线程是等待队列的第一个，就占有锁，否则就会加入到等待队列中，以后会安装FIFO的规则从
 *             队列中取到自己；
 *      非公平锁：比较粗鲁，上来就直接尝试占有锁，如果失败，就再采用类似公平锁那种方式，进行排队，
 *               优点在于吞吐量比公平锁大；
 *
 *  4.实际的使用例子
 *      Synchronized 也是一种非公平锁；
 *      CopyOnWriteArrayList：并发包中的工具类型CopyOnWriteArrayList 中的add方法里面，实现ArrayList
 *                            线程安全也是通过非公平锁（ReentrantLock）,进行加锁和释放的；
 *
 */
public class ReentrantLockDemo {

    public static void main(String[] args){
        //默认：false 非公平锁 NonfairSync
        Lock nonfairLock = new ReentrantLock();

        //传值：true  公平锁  FairSync
        Lock fairLock = new ReentrantLock(true);
    }
}
