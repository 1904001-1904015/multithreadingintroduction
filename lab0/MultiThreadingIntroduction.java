class SumThread extends Thread {
    private int lo, hi;
    public static int[] arr;
    private double ans = 0;

    public SumThread( int lo, int hi) {
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    public void run() {
        for (int i = lo; i < hi; i++) {
            ans += arr[i];
        }
    }

    public double getAns() {
        return ans;
    }
}

public class MultiThreadingIntroduction {
    public static void main(String[] args) throws InterruptedException {
    
        SumThread.arr = new int[10000000];
        for (int i = 0; i < SumThread.arr.length; i++) {
            SumThread.arr[i] = (int) (Math.random() * 100000);
        }
        // calculate total time
        long startTime, endTime;
        

        startTime = System.currentTimeMillis();
        long sum = 0;
        for (int i = 0; i < SumThread.arr.length; i++) {
            sum += SumThread.arr[i];
        }
        System.out.println(sum);
        endTime = System.currentTimeMillis();
        System.out.println("Total time: " + (endTime - startTime) + " ms");


        startTime = System.currentTimeMillis();
        System.out.println(sum((int)Math.log(SumThread.arr.length)));
        endTime = System.currentTimeMillis();
        System.out.println("Total time: " + (endTime - startTime) + " ms");
    }

    public static double sum(int numThreads) throws InterruptedException {
        int len = SumThread.arr.length;
        double ans = 0;

        // Create and start threads.
        SumThread[] ts = new SumThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            ts[i] = new SumThread((i * len) / numThreads, ((i + 1) * len / numThreads));
            ts[i].start();
        }

        // Wait for the threads to finish and sum their results.
        for (int i = 0; i < numThreads; i++) {
            ts[i].join();
            ans += ts[i].getAns();
        }
        return ans;
    }
}
