package pcd;

import java.util.ArrayList;

public class Depot {
    static final int SIZE = 11;
    private final ArrayList<Character> stock = new ArrayList<Character>();
    private boolean done = false;

    public synchronized void put(String who, char a, char b) throws InterruptedException {
        char[] pair = {a, b};
        for (char c : pair) {
            while (stock.size() == SIZE && !done) {
                Main.window.showProducer(who + " așteaptă: depozitul e plin");
                wait();
            }
            if (done) {
                return;
            }
            stock.add(c);
            Main.window.showProducer(who + " a pus " + c);
            Main.window.showDepot("Depozit " + stock.size() + "/" + SIZE + ": " + stock);
            if (stock.size() == SIZE) {
                Main.window.showProducer("Depozitul e plin");
            }
            notifyAll();
        }
    }

    public synchronized char get(String who) throws InterruptedException {
        if (stock.isEmpty()) {
            Main.window.showConsumer(who + " așteaptă: depozitul e gol");
        }
        while (stock.isEmpty()) {
            wait();
        }
        char c = stock.remove(stock.size() - 1);
        Main.window.showConsumer(who + " a luat " + c);
        Main.window.showDepot("Depozit " + stock.size() + "/" + SIZE + ": " + stock);
        if (stock.isEmpty()) {
            Main.window.showConsumer("Depozitul e gol");
        }
        notify();
        return c;
    }

    public synchronized void finish() {
        done = true;
        notifyAll();
    }

    public synchronized boolean isDone() {
        return done;
    }
}
