package com.demo.threadDemo;

import java.util.concurrent.*;

/**
 *  线程池 ThreadPoolExecutor 7大参数：
 *      1.corePoolSize: 线程池中的常驻核心线程数；
 *      2.maxmunPoolSize: 线程池能够容纳同时执行的最大线程数，此值必须大于等于1；
 *      3.keepAliveTime: 多余的空闲线程的存活时间。
 *      （当前线程池数量超过corePoolSize时，当空闲时间达到keepAliveTime值时，
 *      多余空闲线程会被销毁直到只剩下corePoolSize个线程为止）
 *      4.unit: keepAliveTime的单位；
 *      5.workQueue: 任务队列，被提交但尚未被执行的任务；
 *      6.threadFactory: 表示生成线程池中工作线程的线程工厂，用于创建线程一般用默认的即可；
 *      7.handler: 拒绝策略，表示当队列满了并且工作线程大于等于线程池的最大线程数（maxmunPoolSize），就会启动拒绝策略；
 *
 *  线程池的底层工作原理：
 *      1.在创建线程池后，等待提交过来的任务请求；
 *      2.当调用execute()方法添加一个请求任务时，线程池会做如下判断：
 *          2.1 如果正在运行的线程数量小于corePoolSize，那么马上创建线程运行这个任务；
 *          2.2 如果正在运行的线程数量大于或等于corePoolSize，那么将这个任务放入队列；
 *          2.3 如果这时候队列满了且正在运行的线程数量还小于maxmunPoolSize，那么还是要创建非核心线程立刻运行这个任务；
 *          2.4 如果队列满了且正在运行的线程数量大于或等于maxmunPoolSize，那么线程池会启动饱和和拒绝策略来执行；
 *      3.当一个线程完成任务时，它会从队列中取下一个任务来执行；
 *      4.当一个线程无事可做超过一定时间（keepAliveTime）时，线程会判断:
 *          4.1 如果当前运行的线程数大于corePoolSize，那么这个线程就被停掉；
 *          4.2 所以线程的所有任务完成之后，它最终会收缩到corePoolSize的大小；
 *
 *  JDK内置的4种线程池拒绝策略：
 *      1.AbortPolicy(默认)：直接抛出RejectedExecutionException异常阻止系统正常运行；
 *      2.CallerRunsPolicy: "调用者运行"一种调节机制，该策略既不会抛弃任务，也不会抛出异常，而是将某些任务回退到调用者，从而降低新任务的流量；
 *      3.DiscardOldestPolicy: 抛弃队列中等待最久的任务，然后把当前任务加入队列中尝试再次提交当前任务；
 *      4.DiscardPolicy: 直接丢弃任务，不予任何处理也不抛异常，如果允许任务丢失，这是最好的一种方案；
 *
 *  题目：银行有两个核心柜台，根据业务人数最多可以扩容到5个柜台，等候区有3个座位，现在有6个人去办理业务？
 *
 *  理想情况：两个核心柜台正在处理2个业务（1、2），3个业务（3、4、5）进入等候区，
 *          然后创建增加3个柜台办理（3、4、5）的业务，第6个业务进入等候区前面
 *          一个业务处理完之后然后再进去处理；
 *
 *  实际情况：两个核心柜台正在处理2个业务（1、2），3个业务（3、4、5）进入等候区，
 *          第6个业务不会进去等候区，直接扩容1个柜台处理，处理完成之后才会扩容
 *          3个柜台把等候区3个业务（3、4、5）进行处理；
 */
public class ThreadPoolExecutorDemo {

    public static void main(String[] args){

        System.out.println("cpu数量："+Runtime.getRuntime().availableProcessors());
        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                5,
                100L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),//等候区
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        try{
            for (int i = 1; i <= 6; i++) {
                final int tempInt = i;
                executorService.execute(()->{
                    System.out.println(tempInt+"号顾客，请到"+Thread.currentThread().getName()+"窗口办理业务");
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            executorService.shutdown();
        }

    }
}
