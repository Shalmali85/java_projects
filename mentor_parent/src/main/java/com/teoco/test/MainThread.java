package com.teoco.test;

/**
 * Created by roysha on 4/4/2016.
 */
public class MainThread {

    public static void main(String args[]){
    final TestThread t = new TestThread();

    Thread t1 = new Thread() { public void run() { t.testA(); } };
    Thread t2 = new Thread() { public void run() { t.testB(); } };
    t1.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        System.out.println(t2.getState());
}

}
