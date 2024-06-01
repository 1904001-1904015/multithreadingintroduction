public class Bank {
	@SuppressWarnings("unused")
	private final String accountNumber;
	private final OperationsQueue operationsQueue;
	private int balance = 0;
	private final Object lock = new Object();

	public Bank(String accountNumber, OperationsQueue operationsQueue) {
		this.accountNumber = accountNumber;
		this.operationsQueue = operationsQueue;
	}

	public void deposit() {
		while (true) {
			int amount = operationsQueue.getNextItem();
			if (amount == -9999) {
				break;
			}
			synchronized (lock) {
				if (amount > 0) {
					balance += amount;
					System.out.println("Deposited: " + amount + " Balance: " + balance);
				} else {
					operationsQueue.add(amount);
					System.out.println("Operation added back: " + amount);
				}
			}
		}
	}

	public void withdraw() {
		while (true) {
			int amount = operationsQueue.getNextItem();
			if (amount == -9999) {
				break;
			}
			synchronized (lock) {
				if (balance + amount < 0) {
					System.out.println("Not enough balance to withdraw: " + amount);
					continue;
				}
				if (amount < 0) {
					balance += amount;
					System.out.println("Withdrawn: " + amount + " Balance: " + balance);
				} else {
					operationsQueue.add(amount);
					System.out.println("Operation added back: " + amount);
				}
			}
		}
	}
}
