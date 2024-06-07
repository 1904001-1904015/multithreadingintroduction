import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public final double t1 = 3;
    public final double t2 = 7;
    public static final int noOfServers = 4;
    public static final int timeLimit = 3000;
    public static final int maxQueueLength = 100;
    public static ArrayBlockingQueue<Elements> mainQueue = new ArrayBlockingQueue<Elements>(maxQueueLength,true);
    public static Integer[] servicedCount = new Integer[noOfServers+1];
    public static final AtomicInteger threadNumber = new AtomicInteger(1);


    public static void main(String[] args) {
        ElementAdderQueue elementAdderQueue = new ElementAdderQueue(timeLimit);

        Thread elementsThread = new Thread(elementAdderQueue);
        ExecutorService serve = Executors.newFixedThreadPool(noOfServers);

        elementsThread.start();
        for (int i = 0; i < noOfServers; i++) {
            Servers s = new Servers();
            serve.execute(s);
        }
//        serve.shutdown();
//        while (!serve.isTerminated()) {
//            // Wait until all threads are finished
//        }

    }
}

