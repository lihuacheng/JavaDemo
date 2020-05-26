package com.demo.singletorDemo;

public class SingletorDemo {

    private static SingletorDemo instance = null;

    private SingletorDemo(){
        System.out.println(Thread.currentThread().getName()+"\t 我是构造方法SingletorDemo()");
    }

    //synchronized 实现方法同步
    public static synchronized SingletorDemo getInstace1(){
        if(instance == null){
            instance = new SingletorDemo();
        }
        return instance;
    }

    // synchronized 实现代码端同步
    // DCL (double check lock 双端检验锁机制)
    public static SingletorDemo getInstace2(){
        if(instance == null){
            synchronized(SingletorDemo.class){
                if(instance == null){
                    instance = new SingletorDemo();
                }
            }
        }
        return instance;
    }
    public static void main(String[] args){
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                SingletorDemo.getInstace2();
            },String.valueOf(i)).start();
        }
    }
}
