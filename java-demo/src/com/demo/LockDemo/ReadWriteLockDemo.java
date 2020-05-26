package com.demo.LockDemo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//资源类
class MyCache{

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private volatile Map<String,Object> map = new HashMap<>();

    public void put(String key,Object value){
        readWriteLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 线程 正在写入："+key);
            //暂停线程 0.3秒 模拟网络波动
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName()+"\t 线程 写入完成");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            readWriteLock.writeLock().unlock();
        }

    }

    public void get(String key){
        readWriteLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 线程 正在读取");
            //暂停线程 0.3秒 模拟网络波动
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 线程 读取完成："+result);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            readWriteLock.readLock().unlock();
        }
    }
}

/**
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行。
 * 但是
 * 如果有一个线程想去写共享资源来，就不应该再有其他线程可以对该资源进行读或写
 *
 * 小总结：
 *          读 - 读 能共存
 *          读 - 写 不能共存
 *          写 - 写 不能共存
 *
 *          写操作：原子+独占，整个过程必须是一个完整的统一体，中间不许被分割，被打断
 */
public class ReadWriteLockDemo {

    public static void main(String[] args){
        MyCache myCache = new MyCache();

        for(int i = 0; i < 5; i++){
            final int tempInt = i;
            new Thread(()->{
                myCache.put(tempInt+"",tempInt+"");
            },String.valueOf(i)+"-W").start();
        }

        for(int i = 0; i < 5; i++){
            final int tempInt = i;
            new Thread(()->{
                myCache.get(tempInt+"");
            },String.valueOf(i)+"-R").start();
        }
    }
}
