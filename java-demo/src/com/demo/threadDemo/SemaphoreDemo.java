package com.demo.threadDemo;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Semaphore :是一个计数信号量，必须由获取它的线程释放。
 *            信号量主要用于两个目的：
 *                  1.用于多个共享资源的互斥使用；
 *                  2.用户并发线程数的控制。
 * Semaphore(int permits, boolean fair):
 *            创建一个 Semaphore与给定数量的许可证和给定的公平设置,fair默认是false非公平锁。
 *
 * acquire() ：从该信号量获取许可证，阻止直到可用，或线程释放许可证；
 *
 * release() : 释放许可证，将其返回到信号量。
 */
public class SemaphoreDemo {

    public static void main(String[] args){
        Semaphore semaphore = new Semaphore(3, false);//模拟3个停车位

        for (int i = 0; i < 6; i++) {//模拟6部汽车
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"\t 抢到车位");
                    int sleep = new Random().nextInt(5000);
                    Thread.sleep(sleep);
                    System.out.println(Thread.currentThread().getName()+"\t 停车"+sleep+"毫    秒，离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }
}
