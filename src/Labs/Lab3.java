package Labs;

public class Lab3 {

    public static void main(String[] args) throws InterruptedException {
        Th3 th3 = new Th3();
        Th4 th4 = new Th4();

        // Здесь коллега добавит запуск Th1 и Th2

        th3.start();
        th4.start();

        th3.join();
        th4.join();
    }

    // Тексты, которые потоки выведут в конце
    static String disciplina = "Programarea Concurentă și Distribuită";
    static String grupa = "TODO: grupa";

    // Метод 1: монитор (synchronized + wait + notifyAll)
    static Object lock = new Object();
    // Сколько потоков уже закончили задачу
    static int finished = 0;

    // Метод 2: бинарная переменная (флаг)
    // false - группа ещё не выведена, true - группа выведена
    static volatile boolean grupaPrinted = false;

    // Th3: задача 3 - пройти интервал [0, 798] от начала
    static class Th3 extends Thread {
        public void run() {
            for (int i = 0; i <= 798; i++) {
                System.out.println("Th3: " + i);
            }

            try {
                // Метод 1: ждём, пока Th4 тоже закончит обход
                waitForBoth();
                // Метод 2: ждём, пока Th4 выведет группу (флаг станет true)
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

    // Th4: задача 4 - пройти интервал [1456, 2111] от конца
    static class Th4 extends Thread {
        public void run() {
            for (int i = 2111; i >= 1456; i--) {
                System.out.println("Th4: " + i);
            }

            try {
                // Метод 1: ждём, пока Th3 тоже закончит обход
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
}