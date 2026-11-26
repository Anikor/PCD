//кол-во производителей X = 4
//генерирует F объектов = 1
//кол-во потребителей Y = 3
//размер хранилища D = 10
//Все операции выполняются до тех пор, пока не будет произведено и потреблено Z объектов = 50.

package Labs.Labs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

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
    static class Producator extends Thread {
        static int totalProdus = 0;
        static final char[] VOCALE= {'A', 'I', 'O', 'E', 'U'};
        static final char[] obiect = new char[X]; // объект каждого производителя
        static final CyclicBarrier bariera = new CyclicBarrier(X, Producator::depune);
        static Depozit dep;
        static int nr = 0;

        private final int id;

        Producator(String nume, Depozit d) {
            super(nume);
            this.id = nr++;
            dep = d;
        }

        // выполняет один поток, когда все X производителей дошли до барьера
        static void depune() {
            if (dep.plin) return; // склад полный, ждём, пока потребители его опустошат
            for (int i = 0; i < X && dep.stoc.size() < D; i++) {
                dep.stoc.addLast(obiect[i]);
                totalProdus++;
                System.out.println("Producator-" + (i + 1) + " a produs " + obiect[i]
                        + " | stoc=" + dep.stoc.size() + "/" + D + " | total=" + totalProdus);
            }
            if (dep.stoc.size() == D) {
                dep.plin = true; // ход потребителям
                System.out.println(">>> Depozitul e plin\n");
            }
        }

        @Override
        public void run() {
            try {
                while (totalProdus < Z) {
                    obiect[id] = VOCALE[(int) (Math.random() * VOCALE.length)]; // F = 1 гласная
                    bariera.await();
                    Thread.sleep((int) (Math.random() * 100));
                }
            } catch (InterruptedException e) {
                return;
            } catch (BrokenBarrierException e) {
                return;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Depozit d = new Depozit();

        Producator[] producatori = new Producator[X];
        for (int i = 0; i < X; i++) {
            producatori[i] = new Producator("Producator-" + (i + 1), d);
            producatori[i].start();
        }

        Consumator[] consumatori = new Consumator[Y];
        for (int i = 0; i < Y; i++) {
            consumatori[i] = new Consumator("Consumator-" + (i + 1), d);
            consumatori[i].start();
        }

        for (Consumator c : consumatori) c.join();
        System.out.println("Gata: consumate " + Consumator.totalConsumat
                + ", in depozit au ramas " + d.stoc.size());
    }
}
