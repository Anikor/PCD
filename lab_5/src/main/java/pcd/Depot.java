package pcd;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Depot {
    static final int SIZE = 11;
    static final int TOTAL = Main.CONSUMERS * Consumer.NEEDED;
    private final BlockingQueue<Character> queue = new ArrayBlockingQueue<Character>(SIZE);
    private final AtomicInteger produced = new AtomicInteger(0);

    public boolean put(String who, char a, char b) throws InterruptedException {
        if (produced.getAndAdd(2) >= TOTAL) {
            return false;
        }
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
        return true;
    }

    public char take(String who) throws InterruptedException {
        if (queue.isEmpty()) {
            Main.window.showConsumer(who + " așteaptă: depozitul e gol");
        }
        Character c = queue.poll();
        while (c == null) {
            c = queue.poll();
        }
        Main.window.showConsumer(who + " a luat " + c + "   (" + Thread.currentThread().getName() + ")");
        Main.window.showDepot("Depozit " + queue.size() + "/" + SIZE + ": " + queue);
        if (queue.isEmpty()) {
            Main.window.showConsumer("Depozitul e gol");
        }
        return c;
    }
}
