package com.demo.DeadLockDemo;
class HoldLockThred implements Runnable{

    private String lockA;
    private String lockB;

    public HoldLockThred(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"\t 线程，持有"+lockA+"锁，准备获取"+lockB+"锁");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"\t 线程，持有"+lockB+"锁");
            }
        }
    }
}

/**
 *  1.死锁：
 *      是指两个或两个以上的进程在执行过程中，因争夺资源而造成的一种互相等待的现象，诺无外力干涉那么它们都将无法推行下去；
 *
 *  2.产生死锁的主要原因：
 *      2.1 系统资源不足；
 *      2.2 进程运行推进的顺序不合适；
 *      2.3 资源分配不当；
 *
 *  3.解决方式：
 *      3.1 jps命令定位进程号；
 *      3.2 jstack找到死锁查看具体打印信息；
 *
 *  jps 查看java进程
 *  jinfo 查看java运行程序配置属性
 *  jstack 查看java栈信息
 *  java -XX:+PrintFlagsInitial 查看JVM默认参数
 *  java -XX:+PrintFlagsFinal 查看JVM参数,出现冒号：则是已经修改过了的
 *
 *  常用参数：
 *      -Xms:
 *          1.初始堆大小内存，默认为物理内存1/64
 *          2.等价于 -XX:InitialHeapSize
 *      -Xmx:
 *          1.最大分配堆内存，默认物理内存1/4
 *          2.等价于 -XX:MaxHeapSize
 *      -Xss：
 *          1.设置单个线程栈的大小，一般默认为512k~1024k
 *          2.等价于 -XX:ThreadStackSize
 *      -Xmn: 设置年轻代大小
 *      -XX:MetaspaceSize:
 *          1.设置元空间大小：
 *              元空间的本质和永久代类似，都是对JVM规范中方法区，不过元空间与永久代之间最大的区别在于：
 *              元空间并不在虚拟机中，而是使用本地内存。因此，默认情况下，元空间的大小仅受本地内存限制。
 *          2. -Xms10m -Xmx10m -XX:MetaspaceSize=1024m -XX:+PrintFlagsFinal
 *      -XX:PrintGCdetail:输出详细的GC日志 新生代、老年代、元空间的内存信息和Young GC和Full GC的回收内存信息
 *      -XX:SurvivoRatio:
 *          设置新生代中eden和S0/S1空间的比例
 *          默认
 *          -XX:SurvivorRatio=8,Eden:S0:S1=8:1:1
 *          假如
 *          -XX:SurvivorRatio=4,Eden:S0:S1=4:1:1
 *          SurvivorRatio值就是设置Eden区的比例占多少，S0/S1相同
 *      -XX:NewRatio:
 *      -XX:MaxTenuringThreshold:
 *
 *
 *
 *
 */
public class DeadLockDemo {

    public static void main(String[] args){
        new Thread(new HoldLockThred("100","200"),"AAA").start();
        new Thread(new HoldLockThred("200","100"),"BBB").start();
    }
}
