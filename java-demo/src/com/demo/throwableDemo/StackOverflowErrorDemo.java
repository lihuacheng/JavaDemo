package com.demo.throwableDemo;

/**
 * StackOverflowError：java栈溢出错误，java栈默认是512kb-1mb之间，超过栈深度之后会报此错误；
 */
public class StackOverflowErrorDemo {

    public static void main(String[] args){
        sofe();
    }

    private static void sofe() {
        sofe();
    }
}
