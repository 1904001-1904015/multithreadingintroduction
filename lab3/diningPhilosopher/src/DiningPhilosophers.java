package lab3.diningPhilosopher.src;

import java.util.ArrayList;
import java.util.List;

public class DiningPhilosophers {
    private final List<Table> tables = new ArrayList<>();

    public DiningPhilosophers() {
        for (int i = 0; i < 5; i++) {
            tables.add(new Table(i));
        }
    }

    public void startDining() {
        for (Table table : tables) {
            table.startDining();
        }
    }

    public static void main(String[] args) {
        DiningPhilosophers diningPhilosophers = new DiningPhilosophers();
        diningPhilosophers.startDining();
    }
}
