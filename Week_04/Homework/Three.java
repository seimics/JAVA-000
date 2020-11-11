package Homework;

public class Three {
    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();

        Object o = new Object();
        Thread t = new Thread(() -> {
            synchronized(o){
                Result.res=sum();
                o.notify();
            }
        });
        t.start();

        synchronized(o){
            o.wait();
        }
        System.out.println("异步计算结果为："+ Result.res);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    static class Result{
        static int res;
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}