import java.util.ArrayList;
import java.util.List;

public class Main {
    static final int X = 2; // Producer
    static final int Y = 5; // Consumer
    static final int Z = 3; // Objects
    static final int D = 12; // Store

    public static void main(String[] args) throws InterruptedException {
        Store store = new Store(D);

        List<Producer> producers = new ArrayList<Producer>();
        List<Consumer> consumers = new ArrayList<Consumer>();

        for (int i = 1; i <= X; i++) {
            Producer p = new Producer(store);
            p.setDaemon(true);
            p.setName("Производитель №" + i);
            producers.add(p);
        }
        for (int i = 1; i <= Y; i++) {
            Consumer c = new Consumer(store, Z);
            c.setName("Потребитель №" + i);
            consumers.add(c);
        }

        for (Producer p : producers) {
            p.start();
        }
        for (Consumer c : consumers) {
            c.start();
        }

        for (Consumer c : consumers) {
            c.join();
        }
        System.out.println("Все потоки завершены");
    }
}
