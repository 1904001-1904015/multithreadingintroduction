package lab3.diningPhilosopher.src;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Table {
    private final Lock[] chopsticks = new ReentrantLock[6];
    private final Philosopher[] philosophers = new Philosopher[5];

    public Table(int tableId) {
        for (int i = 0; i < chopsticks.length; i++) {
            chopsticks[i] = new ReentrantLock();
        }
        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i] = new Philosopher(i + tableId * 5, chopsticks[i], chopsticks[(i + 1) % 6], this);
        }
    }

    public void startDining() {
        for (Philosopher philosopher : philosophers) {
            new Thread(philosopher).start();
        }
    }
}
