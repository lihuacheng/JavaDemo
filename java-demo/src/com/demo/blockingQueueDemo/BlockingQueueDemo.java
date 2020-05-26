package com.demo.blockingQueueDemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * ArrayBlockingQueue: 是一个基于数据结构的有界阻塞对象，
 *                     此队列按照FIFO(先进先出)原则对元素进行排序。
 *
 * LinkedBlockingQueue: 是一个基于链表结构的阻塞队列，
 *                      此队列按照FIFO（先进先出）排序元素，
 *                      吞吐量通过要高于ArrayBlockingQueue.
 *
 * SynchronousQueue: 是一个不存储元素的阻塞队列
 *                   每一个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，
 *                   吞吐量通常要高于LinkBlockingQueue.
 *
 *
 * 1 队列
 *
 *
 * 2 阻塞队列
 *   2.1 阻塞队列有没有好的一面
 *
 *   2.2 不得不阻塞，你如何管理
 */
public class BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        showTimeOut();
    }

    /**
     * 方法类型：超时
     * @throws InterruptedException
     */
    public static void showTimeOut() throws InterruptedException {
        BlockingQueue blockingQueue = new ArrayBlockingQueue(3);
        System.out.println(blockingQueue.offer("a", 2L , TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("b", 2L , TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("c", 2L , TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("x", 2L , TimeUnit.SECONDS));
    }

    /**
     * 方法类型：阻塞
     * @throws InterruptedException
     */
    public static void showBlocking() throws InterruptedException {
        BlockingQueue blockingQueue = new ArrayBlockingQueue(3);

        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
        blockingQueue.put("x");


        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
    }

    /**
     * 方法类型：特殊值
     */
    public static void showValue() {
        BlockingQueue blockingQueue = new ArrayBlockingQueue(3);

        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("x"));

        System.out.println(blockingQueue.peek());

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
    }

    /**
     * 方法类型：抛出异常
     */
    public static void showException() {
        BlockingQueue blockingQueue = new ArrayBlockingQueue(3);

        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        System.out.println(blockingQueue.add("x"));

        System.out.println(blockingQueue.element());

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
    }
}
