package com.demo.threadDemo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier : 字面意思是可循环（Cyclic）使用的屏障（Barrier）。
 *                 具体是指让一组线程到达一个屏障（同步点）时被阻塞，直到最后一个线程到达屏障时，
 *                 屏障才会开门，被屏障阻塞的线程才会继续干活。
 *
 * cyclicBarrier.await() : 进入屏障，阻塞线程，等待所以线程到达这里（屏障）时，则通过；
 *
 * CyclicBarrier(int parties, Runnable barrierAction) ：
 *                 构造方法里面的barrierAction是最后一个线程进入屏障时执行的东西；
 */
public class CyclicBarrierDemo {

    public static void main(String[] args){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("开始召回神龙");
        });
        for (int i = 1; i <= 7; i++) {
            final int tempInt = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 收集第"+tempInt+"\t 颗龙珠");
                try {
                    //阻塞线程，等待所有线程到达这里（屏障）时，则通过
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"\t 使用第"+tempInt+"\t 颗龙珠");
            },String.valueOf(i)).start();
        }
    }
}
