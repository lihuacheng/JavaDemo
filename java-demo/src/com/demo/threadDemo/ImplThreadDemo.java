package com.demo.threadDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

class ThreadDemo extends Thread{

    @Override
    public void run() {
        System.out.println("这是继承Thread类实现多线程");
    }
}

class RunnableDemo implements Runnable{

    @Override
    public void run() {
        System.out.println("这是runnable接口实现线程，没有返回参数和异常捕获");
    }
}

class CallableDemo implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println("这是callable接口实现线程，有返回参数和异常捕获");
        return "1111";
    }
}

/**
 * 实现线程的三种方式：
 *  1.继承 Thread 类：重写run方法,Thread类也是实现Runable接口；
 *  2.实现 Runable 接口：重写run方法,实现Runnable接口的实现类的实例对象作为Thread构造函数的target；
 *  3.实现 Callnable 接口：通过FutureTask实现类配合创建线程
 */
public class ImplThreadDemo {

    public static void main(String[] args){

        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.setName("threadDemo");
        threadDemo.start();

        RunnableDemo runnableDemo = new RunnableDemo();
        Thread thread = new Thread(runnableDemo);
        thread.setName("runnableDemo");
        thread.start();

        CallableDemo callableDemo = new CallableDemo();
        FutureTask future = new FutureTask(callableDemo);
        new Thread(future,"callableDemo").start();

    }
}
