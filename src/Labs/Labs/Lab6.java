package Labs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/*
 * ЛР6, вариант 3: X = 4 производителя, Y = 3 потребителя,
 * Z = 50 объектов всего, D = 10 — ёмкость склада, объекты — гласные, F = 1.
 *
 * Цикл: склад пуст -> производители наполняют до D -> потребители
 * забирают всё до нуля -> снова производители ... пока не пройдёт Z объектов.
 * Синхронизация: только ReentrantLock, Semaphore, CyclicBarrier.
 */
public class Lab6 {

    static final int X = 4;   // производители
    static final int Y = 3;   // потребители
    static final int Z = 50;  // всего объектов
    static final int D = 10;  // ёмкость склада

    // =====================================================================
    // ОБЩИЙ СКЛАД — контракт между частью производителя и частью потребителя
    // =====================================================================
    static class Depozit {
        final Deque<Character> stoc = new ArrayDeque<>(D);
        // Взаимное исключение: любое обращение к stoc — только под этим замком
        final ReentrantLock lacat = new ReentrantLock(true);

        // Производитель -> потребители: «склад полон, можно забирать».
        // Производитель делает release() ОДИН раз за цикл, когда наполнение закончено.
        final Semaphore semPlin = new Semaphore(0);

        // Потребители -> производитель: «склад пуст, можно наполнять».
        // 1 разрешение на старте (склад изначально пуст); потребители
        // делают release() ОДИН раз за цикл, когда склад опустошён.
        final Semaphore semGol = new Semaphore(1);
    }

    // =====================================================================
    // ЧАСТЬ: ПОТРЕБИТЕЛЬ
    // =====================================================================
    // «Цех потребления»: сам владеет своими потоками (пул) и своими барьерами.
    static class SectiaConsum {
        private final Depozit dep;
        private final int nrConsumatori;
        private final int totalDeConsumat;

        private final ExecutorService pool;
        // Все потребители собрались -> последний ждёт сигнал «склад полон»
        private final CyclicBarrier laStart;
        // Все потребители увидели пустой склад -> сообщаем производителю
        private final CyclicBarrier laFinal;

        // Изменяются только под dep.lacat
        private int consumatTotal = 0;
        private final int[] consumatDe;
        private int ciclu = 0;

        SectiaConsum(Depozit dep, int nrConsumatori, int totalDeConsumat) {
            this.dep = dep;
            this.nrConsumatori = nrConsumatori;
            this.totalDeConsumat = totalDeConsumat;
            this.consumatDe = new int[nrConsumatori];
            this.pool = Executors.newFixedThreadPool(nrConsumatori);
            // Действие барьера выполняет последний пришедший поток,
            // остальные в это время стоят на барьере.
            this.laStart = new CyclicBarrier(nrConsumatori, this::asteaptaDepozitPlin);
            this.laFinal = new CyclicBarrier(nrConsumatori, this::anuntaDepozitGol);
        }

        void porneste() {
            for (int i = 1; i <= nrConsumatori; i++) {
                pool.execute(new Consumator(i));
            }
            pool.shutdown(); // новых задач не будет; потоки завершатся сами
        }

        void asteaptaSfarsit() throws InterruptedException {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            dep.lacat.lock();
            try {
                System.out.println("\n=== Consumatorii au terminat ===");
                for (int i = 0; i < nrConsumatori; i++) {
                    System.out.printf("Consumator-%d a consumat %d obiecte%n", i + 1, consumatDe[i]);
                }
                System.out.printf("Total consumat: %d/%d | cicluri: %d | stoc final: %d%n",
                        consumatTotal, totalDeConsumat, ciclu, dep.stoc.size());
            } finally {
                dep.lacat.unlock();
            }
        }

        // Действие барьера laStart: ждём, пока производитель скажет «склад полон»
        private void asteaptaDepozitPlin() {
            try {
                dep.semPlin.acquire();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                // исключение ломает барьер -> все потребители выйдут, а не зависнут
                throw new IllegalStateException("Intrerupt in asteptarea depozitului plin");
            }
            dep.lacat.lock();
            try {
                ciclu++;
                System.out.printf("%n>>> [Ciclul %d] Depozitul e plin (%d/%d) - consumatorii incep%n",
                        ciclu, dep.stoc.size(), D);
            } finally {
                dep.lacat.unlock();
            }
        }

        // Действие барьера laFinal: склад пуст -> отдаём ход производителю
        private void anuntaDepozitGol() {
            dep.lacat.lock();
            try {
                System.out.printf("<<< [Ciclul %d] Depozitul e gol (%d/%d) | total consumat=%d/%d%n%n",
                        ciclu, dep.stoc.size(), D, consumatTotal, totalDeConsumat);
            } finally {
                dep.lacat.unlock();
            }
            dep.semGol.release();
        }

        // Одна попытка взять объект. null — склад уже пуст.
        private Character iaUnObiect(int id) {
            dep.lacat.lock();
            try {
                Character c = dep.stoc.pollFirst();
                if (c != null) {
                    consumatTotal++;
                    consumatDe[id - 1]++;
                    System.out.printf("Consumator-%d a consumat '%c' | stoc=%d/%d | total consumat=%d/%d%n",
                            id, c, dep.stoc.size(), D, consumatTotal, totalDeConsumat);
                }
                return c;
            } finally {
                dep.lacat.unlock();
            }
        }

        private boolean totulConsumat() {
            dep.lacat.lock();
            try {
                return consumatTotal >= totalDeConsumat;
            } finally {
                dep.lacat.unlock();
            }
        }

        private class Consumator implements Runnable {
            private final int id;

            Consumator(int id) {
                this.id = id;
            }

            @Override
            public void run() {
                try {
                    while (true) {
                        laStart.await();             // ждём полный склад
                        // забираем, пока склад не станет пустым
                        while (iaUnObiect(id) != null) {
                            // «обработка» объекта — вне замка, чтобы другие тоже брали
                            Thread.sleep(ThreadLocalRandom.current().nextInt(30, 120));
                        }
                        laFinal.await();             // ждём, пока все увидят пустой склад
                        if (totulConsumat()) break;  // все Z объектов забраны
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (BrokenBarrierException e) {
                    System.out.printf("Consumator-%d: bariera a fost rupta, ies%n", id);
                }
            }
        }
    }

    // =====================================================================
    // ЧАСТЬ: ПРОИЗВОДИТЕЛЬ — пишет коллега
    // Контракт: класть в dep.stoc только под dep.lacat; перед наполнением
    // взять dep.semGol; когда склад полон (D объектов) и все производители
    // остановились — один раз dep.semPlin.release().
    // =====================================================================

    public static void main(String[] args) throws InterruptedException {
        Depozit depozit = new Depozit();

        SectiaConsum consum = new SectiaConsum(depozit, Y, Z);
        consum.porneste();

        // TODO (producator): запустить X производителей

        consum.asteaptaSfarsit();
    }
}
