package lab3.diningPhilosopher.src;

import java.util.concurrent.locks.Lock;

public class Philosopher implements Runnable {
    private final int id;
    private final Lock leftChopstick;
    private final Lock rightChopstick;
    private final Table table;

    public Philosopher(int id, Lock leftChopstick, Lock rightChopstick, Table table) {
        this.id = id;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        this.table = table;
    }

    @Override
    public void run() {
        try {
            while (true) {
                think();
                if (pickUpChopsticks()) {
                    eat();
                    putDownChopsticks();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private boolean pickUpChopsticks() {
        if (leftChopstick.tryLock()) {
            if (rightChopstick.tryLock()) {
                return true;
            } else {
                leftChopstick.unlock();
            }
        }
        return false;
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void putDownChopsticks() {
        rightChopstick.unlock();
        leftChopstick.unlock();
    }
}
