package Labs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.ReentrantLock;

public class Lab6 {
    static final int X = 4;   // producatori
    static final int Y = 3;   // consumatori
    static final int Z = 50;  // total obiecte
    static final int D = 10;  // capacitatea depozitului

    // Общий склад
    static class Depozit {
        Deque<Character> stoc = new ArrayDeque<>();
        ReentrantLock lacat = new ReentrantLock();
        boolean plin = false; // true - склад заполнен, потребители могут забирать
    }

    // ===== CONSUMATOR =====
    static class Consumator extends Thread {
        static int totalConsumat = 0;
        private final Depozit d;

        Consumator(String nume, Depozit d) {
            super(nume);
            this.d = d;
        }

        @Override
        public void run() {
            while (true) {
                d.lacat.lock();
                try {
                    if (totalConsumat >= Z) break;

                    if (d.plin) {
                        char c = d.stoc.removeFirst();
                        totalConsumat++;
                        System.out.println(getName() + " a consumat " + c
                                + " | stoc=" + d.stoc.size() + "/" + D
                                + " | total=" + totalConsumat);

                        if (d.stoc.isEmpty()) {
                            d.plin = false; // склад пуст - ход производителям
                            System.out.println("<<< Depozitul e gol\n");
                        }
                    }
                } finally {
                    d.lacat.unlock();
                }

                try {
                    Thread.sleep((int) (Math.random() * 100));
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    // ===== PRODUCATOR =====

    public static void main(String[] args) throws InterruptedException {
        Depozit d = new Depozit();

        Consumator[] consumatori = new Consumator[Y];
        for (int i = 0; i < Y; i++) {
            consumatori[i] = new Consumator("Consumator-" + (i + 1), d);
            consumatori[i].start();
        }

        // producatori

        for (Consumator c : consumatori) c.join();
        System.out.println("Gata: consumate " + Consumator.totalConsumat
                + ", in depozit au ramas " + d.stoc.size());
    }
}
