package wxmp.ql;

public class Test2 {

private static int num = 0;
    
    /**
     * 在静态(static)方法上加synchronized关键字，表示锁定class类，类级别的锁
     *
     * 关键字synchronized取得的锁都是对象锁，而不是把一段代码（方法）当成锁，
     * 所以代码中哪个线程先执行synchronized关键字的方法，哪个线程就持有该方法所属对象的锁
     * 
     * @param tag 参数
     */
    public static synchronized void printNum(String tag){
        try {
            if(tag.equals("a")){
                num = 100;
                Thread.sleep(1000);
            } else {
                num = 200;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        // 两个不同的对象
        final Test2 m1 = new Test2();
        final Test2 m2 = new Test2();
        
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                m1.printNum("a");
            }
        });
        
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                m1.printNum("b");
            }
        });
        
        t1.start();
        t2.start();
    }
}
