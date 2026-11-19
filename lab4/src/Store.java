import java.util.ArrayList;

public class Store {
    private final int capacity;

    private final ArrayList<Integer> stockList = new ArrayList<Integer>();

    public Store(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(String name, int a, int b) {
        while (stockList.size() + 2 > capacity) {
            System.out.println("Склад полон. " + name + " ждёт");
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        stockList.add(a);
        stockList.add(b);
        System.out.println(name + " поместил на склад два числа: " + a + ", " + b);
        printState();
        notifyAll();
    }

    public synchronized void get(String name) {
        while (stockList.isEmpty()) {
            System.out.println("Склад пуст. " + name + " ждёт");
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        int value = stockList.remove(stockList.size() - 1);
        System.out.println(name + " взял со склада: " + value);
        printState();
        notifyAll();
    }

    private void printState() {
        if (stockList.isEmpty()) {
            System.out.println("Склад пуст");
        } else {
            System.out.print("На складе " + stockList.size() + " единиц -> ");
            for (int x : stockList) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }
}
