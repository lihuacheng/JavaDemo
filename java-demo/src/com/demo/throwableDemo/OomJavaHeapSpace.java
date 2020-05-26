package com.demo.throwableDemo;

import java.util.Random;

/**
 * OutOfMemoryError: Java heap space
 *  OOM错误：java堆内存不足
 */
public class OomJavaHeapSpace {

    // 配置 JVM java堆内存初始化大小为10MB 最大值10MB：-Xms=10mb -Xmm=10mb
    public static void main(String[] args){
        String str = "java heap space";
        while(true){
            str += str + new Random().toString();
        }
    }
}
