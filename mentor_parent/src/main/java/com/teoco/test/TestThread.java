package com.teoco.test;

/**
 * Created by roysha on 4/4/2016.
 */
public class TestThread {

    public synchronized  static void testA(){
        try {
            System.out.println("I am here ");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public   synchronized  static    void testB(){
        try {
            System.out.println("I am here in b ");

            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
