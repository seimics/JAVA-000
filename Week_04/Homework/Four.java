package Homework;

import java.util.concurrent.CountDownLatch;

public class Four {
    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread t = new Thread(() -> {
            Result.res = sum();
            countDownLatch.countDown();
        });
        t.start();

        countDownLatch.await();
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
