package com.demo.threadDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 实现线程池的常用的几种方式：
 *  1.Executors.newFixedThreadPool(int corePoolSize):
 *      1.1 创建一个定长线程池，可控制线程最大并发数，超过的线程会在队列中等待；
 *      1.2 newFixedThreadPool创建的线程池corePoolSize和maxmunPoolSize值是
 *          相等的，使用的LinkedBlockingQueue链表结构的阻塞队列；
 *
 *  2.Executors.newSingleThreadExecutor():
 *      2.1 创建一个单线程化的线程池，只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序执行；
 *      2.2 newSingleThreadExecutor将corePoolSize和maxmunPoolSize都设置为1，
 *          使用的LinkedBlockingQueue链表结构的阻塞队列；
 *
 *  3.Executors.newCachedThreadPool():
 *      3.1 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程；
 *      3.2 newCachedThreadPool将corePoolSize设置为0，将maxmunPoolSize设置为Integer.MAX_VALUE,
 *          使用SynchronousQueue不存储元素的阻塞队列，也就是说来了任务就创建线程，当前线程空闲超过60秒，
 *          就销毁线程；
 *
 *  4.Executors.newScheduledThreadPool(int corePoolSize);
 *      4.1 创建一个定期或延迟线程池，可以设置固定时间执行，或者延长多少时间后执行；
 *      4.2 newScheduledThreadPool是使用的DelayedWorkQueue优先级阻塞队列；
 *
 * 以上的几种常用的线程池创建方式，底层都是通过 ThreadPoolExecutor 类来实现的；
 *
 */
public class ThreadPoolDemo {

    public static void main(String[] args){
        //一池5线程：适合长期执行的任务；
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //一池1线程：适合一个任务一个任务的执行；
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //一池N线程：适合执行很多短的任务线程；
        ExecutorService executorService = Executors.newCachedThreadPool();
        //一池5线程：适合给定延迟后运行命令或者定期地执行；
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

        //模拟10个用户来执行任务，每个用户就是一个来自外部的请求线程
        try{
            for (int i = 0; i < 10; i++) {
                executorService.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 执行任务");
                });
                scheduledExecutorService.schedule(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 定时执行任务");
                }, 3, TimeUnit.SECONDS);
            }
        }finally{
            executorService.shutdown();
            scheduledExecutorService.shutdown();
        }



    }
}
