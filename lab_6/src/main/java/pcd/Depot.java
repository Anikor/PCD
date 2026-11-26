package pcd;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Depot {
    static final int SIZE = 11;
    static final int TOTAL = 66;
    private final ArrayList<Character> stock = new ArrayList<Character>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition canProduce = lock.newCondition();
    private final Condition canConsume = lock.newCondition();
    private boolean filling = true;
    private int produced = 0;
    private int consumed = 0;

    public boolean put(String who, char c) throws InterruptedException {
        lock.lock();
        try {
            if (!filling && produced < TOTAL) {
                Main.window.showProducer(who + " așteaptă: depozitul e plin");
            }
            while (!filling && produced < TOTAL) {
                canProduce.await();
            }
            if (produced == TOTAL) {
                return false;
            }
            stock.add(c);
            produced++;
            Main.window.showProducer(who + " a produs " + c + "   (" + produced + "/" + TOTAL + ")");
            Main.window.showDepot("Depozit " + stock.size() + "/" + SIZE + ": " + stock);
            if (stock.size() == SIZE) {
                filling = false;
                Main.window.showProducer("Depozitul e plin – consumatorii pot lua");
                canConsume.signal();
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean get(String who) throws InterruptedException {
        lock.lock();
        try {
            if (filling && consumed < TOTAL) {
                Main.window.showConsumer(who + " așteaptă: depozitul se umple (" + stock.size() + "/" + SIZE + ")");
            }
            while (filling && consumed < TOTAL) {
                canConsume.await();
            }
            if (consumed == TOTAL) {
                return false;
            }
            char c = stock.remove(stock.size() - 1);
            consumed++;
            Main.window.showConsumer(who + " a consumat " + c + "   (" + consumed + "/" + TOTAL + ")");
            Main.window.showDepot("Depozit " + stock.size() + "/" + SIZE + ": " + stock);
            if (stock.isEmpty()) {
                filling = true;
                Main.window.showConsumer("Depozitul e gol – producătorii pot produce");
                canProduce.signal();
                canConsume.signal();
            }
            return true;
        } finally {
            lock.unlock();
        }
    }
}
