package wxmp.ql;

import java.io.UnsupportedEncodingException;

public class Test extends Thread{

private int count = 5;
    
    // synchronized加锁
    public synchronized void run() {
        count--;
        System.out.println(this.currentThread().getName() + " count = " + count);
    }
    
    public static void main(String[] args) {
        
    	Test test = new Test();
        Thread t1 = new Thread(test, "t1");
        Thread t2 = new Thread(test, "t2");
        Thread t3 = new Thread(test, "t3");
        Thread t4 = new Thread(test, "t4");
        Thread t5 = new Thread(test, "t5");
        
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
