package Homework;


public class One {
    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();

        Thread t= new Thread(() -> Result.res=sum());
        t.start();
        t.join();
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