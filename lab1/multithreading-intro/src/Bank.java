import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
	@SuppressWarnings("unused")
	private final String accountNumber;
	private final OperationsQueue operationsQueue;
	private final AtomicInteger balance = new AtomicInteger(0);
	private final ReentrantLock lock = new ReentrantLock();

	public Bank(String accountNumber, OperationsQueue operationsQueue) {
		this.accountNumber = accountNumber;
		this.operationsQueue = operationsQueue;
	}

	public void deposit() throws InterruptedException {
		while (true) {
			int amount = operationsQueue.getNextItem();
			if (amount == -9999) {
				break;
			}
			lock.lock();
			try {
				if (amount > 0) {
					balance.addAndGet(amount);
					System.out.println(
							Thread.currentThread().getName() + " Deposited: " + amount + " Balance: " + balance);
				} else {
					operationsQueue.add(amount);
					System.out.println(Thread.currentThread().getName() + " Operation added back: " + amount);
				}
				Thread.yield();
			} finally {
				lock.unlock();
			}
		}
	}

	public void withdraw() throws InterruptedException {
		while (true) {
			int amount = operationsQueue.getNextItem();
			if (amount == -9999) {
				break;
			}
			lock.lock();
			try {
				if (amount > balance.get()) {
					balance.addAndGet(amount); // Revert the balance change
					System.out.println(Thread.currentThread().getName() + " Not enough balance to withdraw: " + amount);
					Thread.yield();
					continue;
				}
				if (amount < 0) {
					System.out.println(
							Thread.currentThread().getName() + " Withdrawn: " + amount + " Balance: " + balance);
				} else {
					operationsQueue.add(amount);
					System.out.println(Thread.currentThread().getName() + " Operation added back: " + amount);
				}
				Thread.yield();
			} finally {
				lock.unlock();
			}
		}
	}
}
