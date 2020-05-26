package com.demo.blockingQueueDemo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * SynchronousQueue: 是一个不存储元素的阻塞队列
 *                   每一个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，
 *                   吞吐量通常要高于LinkBlockingQueue.
 */
public class SynchronousQueueDemo {

    public static void main(String[] args){
        BlockingQueue blockingQueue = new SynchronousQueue();

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"\t put 1");
                blockingQueue.put(1);

                System.out.println(Thread.currentThread().getName()+"\t put 2");
                blockingQueue.put(2);

                System.out.println(Thread.currentThread().getName()+"\t put 3");
                blockingQueue.put(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"AAA").start();

        new Thread(()->{
            try {
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName()+"\t"+blockingQueue.take());

                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName()+"\t"+blockingQueue.take());

                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName()+"\t"+blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"BBB").start();

    }
}
