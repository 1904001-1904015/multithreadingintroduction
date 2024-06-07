public class Main {
	public static void main(String[] args) {
		System.out.println("Hello and welcome!");
		System.out.println("Initializing banking system...");

		int totalNumberOfSimulations = 10;
		OperationsQueue operationsQueue = new OperationsQueue();
		Bank bank = new Bank("123", operationsQueue);

		System.out.println("Initializing simulation...");
		Thread simulationThread = new Thread(() -> operationsQueue.addSimulation(totalNumberOfSimulations));
		simulationThread.start();

		System.out.println("Initializing deposit system...");
		Thread depositThread = new Thread(() -> {
			try {
				bank.deposit();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt(); // Restore interrupted status
				System.err.println("Deposit thread interrupted");
			}
		});
		depositThread.start();

		System.out.println("Initializing withdraw system...");
		Thread withdrawThread = new Thread(() -> {
			try {
				bank.withdraw();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt(); // Restore interrupted status
				System.err.println("Withdraw thread interrupted");
			}
		});
		withdrawThread.start();

		try {
			simulationThread.join();
			operationsQueue.add(-9999);
			depositThread.join();
			withdrawThread.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // Restore interrupted status
			System.err.println("Main thread interrupted");
		}

		System.out.println("Completed");
	}
}
