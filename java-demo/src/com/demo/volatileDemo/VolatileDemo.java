package com.demo.volatileDemo;


import java.util.concurrent.atomic.AtomicInteger;

class MyData{
//    int number = 0;
    volatile  int number = 0;
    void addT060(){
        this.number = 60;
    }

    //请注意number前面是加了volatile关键字修饰的
    void addOne(){
        this.number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void addAtomic(){
        atomicInteger.getAndIncrement();
    }
}

/**
 * 1.验证volatile的可见性
 *     1.1 假如 int number = 0 ;number变量之前根本没有添加volatile关键字修饰，没有可见性；
 *     1.2 添加了volatile，可以解决可见性；
 *
 * 2.验证volatile不保证原子性
 *     2.1 原子性指的是什么意思？
 *         不可分割，完整性，也即某个线程正在做某个具体业务时，中间不可以被加塞或者被分割，需要整体完整
 *         要么同时成功，要么同时失败，保证最近结果输出的一致性。
 *     2.2 volatile不保证原子性
 *     2.3 why
 *     2.4 如何解决原子性？
 *      * 加synchronized
 *      * 使用JUC下的 AtomicInteger
 */
public class VolatileDemo {


    public static void main (String[] args){
         MyData myData = new MyData();

         for (int i = 0;i < 20; i++){
             new Thread(()->{
                 for (int j = 0; j < 1000; j++){
                     myData.addOne();
                     myData.addAtomic();
                 }
             },String.valueOf(i)).start();
         }

         //需要等待上面20个线程都全部计算完成后，在用main线程取得最终的结果值
        while (Thread.activeCount() > 2){
            Thread.yield();
        }

        //最终结果不等于20000 ,则证明volatile修饰的变量不保证原子一致性 没有保证最终输出结果的一致性
        System.out.println(Thread.currentThread().getName()+"\t int finally number value:"+myData.number);
        System.out.println(Thread.currentThread().getName()+"\t AtomicInteger finally number value:"+myData.atomicInteger);
    }

    //volatile可以保证可见性，及时通知其他线程，主物理内存的值已经被修改。
    private static void seeOkByVolatile() {
        MyData myData = new MyData();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t come in");
            //暂停线程3秒钟
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addT060();
            System.out.println(Thread.currentThread().getName()+"\t updated number value:"+myData.number);
        },"AAA").start();
        while (myData.number == 0){
            //main线程就一直循环等待，直到number值不等于0
        }
        System.out.println(Thread.currentThread().getName()+"\t main number value:"+myData.number);
        System.out.println(Thread.currentThread().getName()+"\t miss is over");
    }
}
