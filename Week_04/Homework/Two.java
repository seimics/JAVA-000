package Homework;

public class Two {
    public static void main(String[] args) {

        long start=System.currentTimeMillis();

        Thread t= new Thread(() -> Result.res=sum());
        t.start();

        while(Result.res == 0){}

        System.out.println("异步计算结果为："+ Result.res);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    static class Result{
        static volatile int res = 0;
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