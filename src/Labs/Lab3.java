package Labs;

public class Lab3 {

    public static void main(String[] args) throws InterruptedException {
        // Один общий объект-монитор на оба потока (как Depozit в лабе про производителей)
        Bariera bariera = new Bariera();

            Andrei a = new Andrei();
        Andrei.Thread1 th1 = a.new Thread1();
        Andrei.Thread2 th2 = a.new Thread2();

        Th3 th3 = new Th3(bariera); // передаём монитор потоку через конструктор
        Th4 th4 = new Th4(bariera); // тот же самый объект, поэтому потоки "видят" друг друга

        // Здесь коллега добавит запуск Th1 и Th2
        th1.start();
        th2.start();
        th3.start();
        th4.start();

        th1.join();
        th2.join();
        th3.join();
        th4.join();
//        try {
//            th1.join();
//            th2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        while (th1.isAlive()) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    // Тексты, которые потоки выведут в конце
    static String disciplina = "Programarea Concurentă și Distribuită";
    static String grupa = "grupa - R-241";

    // Метод 2: бинарная переменная (флаг)
    // false - группа ещё не выведена, true - группа выведена
    static volatile boolean grupaPrinted = false;

    // Метод 1: монитор. Класс устроен так же, как Depozit:
    // synchronized-метод + wait() + notifyAll()
    // (Bariera = "барьер", asteapta = "ждёт" по-румынски)
    static class Bariera {
        int finished = 0; // сколько потоков уже закончили обход своего интервала

        // synchronized на методе: берётся "ключ" от объекта Bariera (this).
        // Одновременно в метод может войти только один поток
        public synchronized void asteapta() throws InterruptedException {
            finished = finished + 1; // отмечаемся: я закончил обход
            notifyAll();             // будим тех, кто уже спит в wait()
            while (finished < 2) {   // если второй поток ещё не закончил...
                wait();              // ...отдаём ключ и засыпаем, пока не разбудят
            }
        }
    }

    // Th3: задача 3 - пройти интервал [0, 798] от начала
    static class Th3 extends Thread {
        Bariera bariera; // ссылка на общий монитор

        public Th3(Bariera bariera) {
            this.bariera = bariera;
        }

        public void run() {
            for (int i = 0; i <= 798; i++) {
                System.out.println("Th3: " + i);
            }

            try {
                // Метод 1: ждём, пока Th4 тоже закончит обход
                bariera.asteapta();
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
        Bariera bariera; // ссылка на общий монитор

        public Th4(Bariera bariera) {
            this.bariera = bariera;
        }

        public void run() {
            for (int i = 2111; i >= 1456; i--) {
                System.out.println("Th4: " + i);
            }

            try {
                // Метод 1: ждём, пока Th3 тоже закончит обход
                bariera.asteapta();
                // Выводим группу по буквам
                printSlowly(grupa);
                // Метод 2: поднимаем флаг, Th3 может выводить своё
                grupaPrinted = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
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
class Andrei {

    int[] a;        // нечётные числа
    int count;

    String nume = "Familia";     // <-- впишите свою фамилию (выводит Th2)
    String prenume = "Imya";     // <-- впишите своё имя (выводит Th1)

    Thread1 th1 = new Thread1();
    Thread2 th2 = new Thread2();

    Andrei() {
        // случайный массив, как в методичке, из него берём только нечётные
        int[] b = new int[100];
        count = 0;
        System.out.println("Исходный массив:");
        for (int i = 0; i < b.length; i++) {
            b[i] = (int) Math.round(Math.random() * 100) + 15;
            System.out.print(b[i] + " ");
            if (b[i] % 2 != 0) count++;
        }
        System.out.println();

        a = new int[count];
        int k = 0;
        for (int x : b) {
            if (x % 2 != 0) a[k++] = x;
        }
    }

    // Вывод текста по буквам с паузой 100 мс
    static void printSlowly(String text) throws InterruptedException {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            Thread.sleep(100);
        }
        System.out.println();
    }

    // Th1: суммы нечётных чисел по два, с начала массива
    class Thread1 extends Thread {
        public void run() {
            System.out.println("Старт потока 1");
            try {
                for (int i = 0; i + 3 < count; i += 4) {
                    int pairone = a[i] + a[i + 1];
                    int pairtwo = a[i + 2] + a[i + 3];
                    int result = pairone + pairtwo;

                    System.out.println("Th1: (" + a[i] + " + " + a[i + 1] + ") + ("
                            + a[i + 2] + " + " + a[i + 3] + ") = "
                            + pairone + " + " + pairtwo + " = " + result);
                    Thread.sleep(20);
                }

                // Метод 1: сигнал "расчёты закончены" через приоритет
                setPriority(9);

                // Метод 2: ждём, пока Th2 полностью завершится (выведет Фамилию)
                th2.join();

                printSlowly(prenume);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Th2: суммы нечётных чисел по два, с конца массива
    class Thread2 extends Thread {
        public void run() {
            System.out.println("Старт потока 2");
            try {
                for (int i = count - 1; i - 3 >= 0; i -= 4) {
                    int pairone = a[i] + a[i - 1];
                    int pairtwo = a[i - 2] + a[i - 3];
                    int result = pairone + pairtwo;

                    System.out.println("Th2: (" + a[i] + " + " + a[i - 1] + ") + ("
                            + a[i - 2] + " + " + a[i - 3] + ") = "
                            + pairone + " + " + pairtwo + " = " + result);
                    Thread.sleep(20);
                }

                // Метод 1: ждём сигнал от Th1 (приоритет стал 9)
                while (th1.getPriority() != 9) {
                    Thread.sleep(10);
                }

                printSlowly(nume);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}