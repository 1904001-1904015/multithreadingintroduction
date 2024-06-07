import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class OperationsQueue {
	private final List<Integer> operations = new ArrayList<>();
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition condition = lock.newCondition();

	public void addSimulation(int totalSimulations) {
		lock.lock();
		try {
			for (int i = 0; i < totalSimulations; i++) {
				int random = (int) (Math.random() * 200) - 100;
				if (random != 0) {
					operations.add(random);
					System.out.println(i + ". New operation added: " + random);
				}
				try {
					Thread.sleep((int) (Math.random() * 80));
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt(); // Restore interrupted status
					System.err.println("Simulation thread interrupted");
				}
			}
			operations.add(-9999);
			condition.signalAll(); // Notify all threads
		} finally {
			lock.unlock();
		}
	}

	public void add(int amount) {
		lock.lock();
		try {
			operations.add(amount);
			condition.signalAll(); // Notify all threads
		} finally {
			lock.unlock();
		}
	}

	public int getNextItem() throws InterruptedException {
		lock.lock();
		try {
			while (operations.isEmpty()) {
				condition.await(); // Wait for a notification
			}
			return operations.remove(0);
		} finally {
			lock.unlock();
		}
	}
}
