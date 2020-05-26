package com.demo.prodConsumerDemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class ShareResouce{
    private volatile boolean PROD_FLAG = true;

    private volatile boolean CONS_FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    BlockingQueue<String> blockingQueue = null;

    public ShareResouce(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void prod () throws InterruptedException {
        String data = null;
        boolean retVal;
        while (PROD_FLAG){
            data = atomicInteger.incrementAndGet()+"";
            retVal = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if(retVal){
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data+"成功");
            }else{
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data+"失败");
            }
        }
        System.out.println(Thread.currentThread().getName()+"\t 已经停止生产");
    }

    public void Cons() throws InterruptedException {
        String result = null;
        while (CONS_FLAG){
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if(result==null||result.equalsIgnoreCase("")){
                CONS_FLAG = false;
                System.out.println(Thread.currentThread().getName()+"\t 主动停止消费");
            }
            System.out.println(Thread.currentThread().getName()+"\t 消费队列"+result+"成功");
        }
        System.out.println(Thread.currentThread().getName()+"\t 已经停止消费");
    }

    public void stopProd(){
        this.PROD_FLAG = false;
        System.out.println(Thread.currentThread().getName()+"\t 手动停止生产");
    }

    public void stopCons(){
        this.CONS_FLAG = false;
        System.out.println(Thread.currentThread().getName()+"\t 手动停止消费");
    }
}

/**
 * volatile/CAS/AtomicInteger/BlockQueue/线程交互/原子引用
 */
public class ProdConsumer_BlockQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        ShareResouce shareResouce = new ShareResouce(new ArrayBlockingQueue<String>(5));
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 线程，启动成功");
            try {
                shareResouce.prod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"生产者").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 线程，启动成功");
            try {
                shareResouce.Cons();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"消费者").start();

        Thread.sleep(5000);
        shareResouce.stopProd();

        Thread.sleep(5000);
        shareResouce.stopCons();
    }


}
