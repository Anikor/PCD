import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.locks.ReentrantLock;

public class ProdConsImpare {
    static final int CAPACITY = 12;
    static final int TARGET_TOTAL = 60;
    static final int PRODUCERS = 2;
    static final int CONSUMERS = 5;

    static class Depot {
        final Deque<Integer> buffer = new ArrayDeque<>(CAPACITY);
        final ReentrantLock lock = new ReentrantLock(true);
        final Random rnd = new Random();
        int producedTotal = 0;
        int consumedTotal = 0;
        volatile boolean done = false;

        void printFullIfNeeded() {
            if (buffer.size() == CAPACITY) {
                System.out.printf(">>> Depozitul e plin (%d/%d)%n", buffer.size(), CAPACITY);
            }
        }

        void printEmptyIfNeeded() {
            if (buffer.isEmpty()) {
                System.out.printf("<<< Depozitul e gol (0/%d)%n", CAPACITY);
            }
        }

        boolean tryProduce(String name) {
            lock.lock();
            try {
                if (producedTotal >= TARGET_TOTAL) return false;
                if (buffer.size() == CAPACITY) return false;
                int value = (rnd.nextInt(45) + 1) * 2;
                buffer.addLast(value);
                producedTotal++;
                System.out.printf("%s a produs %d | stoc=%d/%d | totalProd=%d%n",
                        name, value, buffer.size(), CAPACITY, producedTotal);
                if (buffer.size() == CAPACITY) printFullIfNeeded();
                return true;
            } finally {
                lock.unlock();
            }
        }

        boolean tryConsume(String name) {
            lock.lock();
            try {
                if (buffer.isEmpty()) return false;
                int value = buffer.removeFirst();
                consumedTotal++;
                System.out.printf("%s a consumat %d | stoc=%d/%d | totalCons=%d%n",
                        name, value, buffer.size(), CAPACITY, consumedTotal);
                if (buffer.isEmpty()) printEmptyIfNeeded();
                if (consumedTotal >= TARGET_TOTAL && buffer.isEmpty()) {
                    done = true;
                }
                return true;
            } finally {
                lock.unlock();
            }
        }
    }

    static class Producer extends Thread {
        private final Depot depot;
        private final Phaser phaser;

        Producer(Depot depot, Phaser phaser, String name) {
            super(name);
            this.depot = depot;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                while (!depot.done) {
                    int phase = phaser.getPhase();
                    if (phase % 2 == 0) {
                        boolean madeSomething = true;
                        while (!depot.done) {
                            madeSomething = depot.tryProduce(getName());
                            if (!madeSomething) break;
                        }
                        phaser.arriveAndAwaitAdvance();
                    } else {
                        phaser.arriveAndAwaitAdvance();
                    }
                }
            } catch (Exception ignored) {
            } finally {
                try {
                    phaser.arriveAndDeregister();
                } catch (IllegalStateException ignored) {
                }
            }
        }
    }

    static class Consumer extends Thread {
        private final Depot depot;
        private final Phaser phaser;

        Consumer(Depot depot, Phaser phaser, String name) {
            super(name);
            this.depot = depot;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                while (!depot.done) {
                    int phase = phaser.getPhase();
                    if (phase % 2 == 1) {
                        boolean tookSomething = true;
                        while (!depot.done) {
                            tookSomething = depot.tryConsume(getName());
                            if (!tookSomething) break;
                        }
                        phaser.arriveAndAwaitAdvance();
                    } else {
                        phaser.arriveAndAwaitAdvance();
                    }
                }
            } catch (Exception ignored) {
            } finally {
                try {
                    phaser.arriveAndDeregister();
                } catch (IllegalStateException ignored) {
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Depot depot = new Depot();
        Phaser phaser = new Phaser(PRODUCERS + CONSUMERS);
        Thread[] producers = new Thread[PRODUCERS];
        Thread[] consumers = new Thread[CONSUMERS];

        for (int i = 0; i < PRODUCERS; i++) {
            producers[i] = new Producer(depot, phaser, "Producator-" + (i + 1));
        }
        for (int i = 0; i < CONSUMERS; i++) {
            consumers[i] = new Consumer(depot, phaser, "Consumator-" + (i + 1));
        }

        for (Thread t : producers) t.start();
        for (Thread t : consumers) t.start();

        for (Thread t : producers) t.join();
        for (Thread t : consumers) t.join();

        System.out.println("=== Gata: au fost produse și consumate " + TARGET_TOTAL + " obiecte în cicluri FILL DRAIN. ===");
    }
}