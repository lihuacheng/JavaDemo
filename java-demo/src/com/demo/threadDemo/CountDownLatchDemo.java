package com.demo.threadDemo;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch: 允许一个或多个线程等待直到在其他线程中执行的一组操作完成的同步辅助。
 * countDownLatch.countDown()：减少锁存器的计数，如果计数达到零，释放所有等待的线程。
 * countDownLatch.await(): 阻塞当前线程等到锁存器计数到零。
 */
public class CountDownLatchDemo {

    public static void main(String[] args){
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 国，被灭");
                countDownLatch.countDown();
            },CountryEnum.forEach(i).getMessage()).start();
        }
        try {
            //阻塞Main线程 直至计数 为 0
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("***********秦帝国，一统华夏");
    }

    public static void closeDoor() {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 上完自习，离开教室");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
        try {
            //阻塞Main线程 直至计数 为 0
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("***********班长最后关门走人");
    }
}
