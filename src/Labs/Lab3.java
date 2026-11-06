package Labs;

public class Lab3 {

    static String disciplina = "Programarea Concurentă și Distribuită";
    static String grupa = "grupa: R-241";

    // Метод 1: монитор (synchronized + wait + notifyAll)
    static Object lock = new Object();
    // Сколько потоков уже закончили считать
    static int finished = 0;

    // Метод 2: бинарная переменная (флаг)
    // false - группа ещё не выведена, true - группа выведена
    static volatile boolean grupaPrinted = false;

    // Поток Th3: идёт по интервалу [0, 798] от начала
    static class Th3 extends Thread {
        public void run() {
            // --- Задача: суммы нечётных чисел по два ---
            int first = 0;
            boolean haveFirst = false;
            for (int i = 0; i <= 798; i++) {
                if (i % 2 == 1) {
                    if (haveFirst == false) {
                        first = i;
                        haveFirst = true;
                    } else {
                        System.out.println("Th3: " + first + " + " + i + " = " + (first + i));
                        haveFirst = false;
                    }
                }
            }
            if (haveFirst) {
                System.out.println("Th3: " + first + " (fără pereche)");
            }

            try {
                // Метод 1: ждём, пока Th4 тоже закончит считать
                waitForBoth();
                // Метод 2: ждём, пока флаг не станет true (Th4 вывел группу)
                while (grupaPrinted == false) {
                    Thread.sleep(10);
                }
                // Выводим название дисциплины по буквам
                printSlowly(disciplina);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Поток Th4: идёт по интервалу [1456, 2111] от конца
    static class Th4 extends Thread {
        public void run() {
            // --- Задача: суммы нечётных чисел по два ---
            int first = 0;
            boolean haveFirst = false;
            for (int i = 2111; i >= 1456; i--) {
                if (i % 2 == 1) {
                    if (haveFirst == false) {
                        first = i;
                        haveFirst = true;
                    } else {
                        System.out.println("Th4: " + first + " + " + i + " = " + (first + i));
                        haveFirst = false;
                    }
                }
            }
            if (haveFirst) {
                System.out.println("Th4: " + first + " (fără pereche)");
            }

            try {
                // Метод 1: ждём, пока Th3 тоже закончит считать
                waitForBoth();
                // Выводим группу по буквам
                printSlowly(grupa);
                // Метод 2: поднимаем флаг, Th3 может выводить своё
                grupaPrinted = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Метод 1: каждый поток говорит "я закончил" и ждёт второго
    static void waitForBoth() throws InterruptedException {
        synchronized (lock) {
            finished = finished + 1;
            lock.notifyAll();
            while (finished < 2) {
                lock.wait();
            }
        }
    }

    // Выводит текст по одной букве с паузой 100 мс
    static void printSlowly(String text) throws InterruptedException {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            Thread.sleep(100);
        }
        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException {
        Th3 th3 = new Th3();
        Th4 th4 = new Th4();

        // Здесь коллега добавит запуск Th1 и Th2

        th3.start();
        th4.start();

        th3.join();
        th4.join();
    }
}