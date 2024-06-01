import java.util.List;
import java.util.ArrayList;

public class OperationsQueue {
	private final List<Integer> operations = new ArrayList<>();
	private final Object lock = new Object();

	public void addSimulation(int totalSimulations) {
		synchronized (lock) {
			for (int i = 0; i < totalSimulations; i++) {
				int random = (int) (Math.random() * 200) - 100;
				if (random != 0) {
					operations.add(random);
					lock.notifyAll(); // Notify waiting threads
				}
				System.out.println(i + ". New operation added: " + random);
				try {
					Thread.sleep((int) (Math.random() * 80));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			operations.add(-9999);
			lock.notifyAll(); // Notify waiting threads
		}
	}

	public void add(int amount) {
		synchronized (lock) {
			operations.add(amount);
			lock.notifyAll(); // Notify waiting threads
		}
	}

	public int getNextItem() {
		synchronized (lock) {
			while (operations.isEmpty()) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return operations.remove(0);
		}
	}
}
