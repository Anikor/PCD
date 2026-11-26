package pcd;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Depot {
    static final int SIZE = 11;
    private final BlockingQueue<Character> queue = new ArrayBlockingQueue<Character>(SIZE);

    public void put(String who, char a, char b) throws InterruptedException {
        if (queue.remainingCapacity() < 2) {
            Main.window.showProducer(who + " așteaptă: depozitul e plin");
        }
        queue.put(a);
        queue.put(b);
        Main.window.showProducer(who + " a pus " + a + ", " + b + "   (" + Thread.currentThread().getName() + ")");
        Main.window.showDepot("Depozit " + queue.size() + "/" + SIZE + ": " + queue);
        if (queue.remainingCapacity() == 0) {
            Main.window.showProducer("Depozitul e plin");
        }
    }
}
