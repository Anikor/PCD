package Labs;

public class Lab3 {

    static Andrei andrei;
    static Th3 th3;
    static Th4 th4;

    public static void main(String[] args) throws InterruptedException {
        andrei = new Andrei();
        th3 = new Th3();
        th4 = new Th4();

        // Порядок вывода текстов: Th2 -> Th4 -> Th1 -> Th3.
        andrei.th2.start();
        th4.start();
        andrei.th1.start();
        th3.start();

        andrei.th1.join();
        andrei.th2.join();
        th3.join();
        th4.join();
    }

    static String disciplina = "Programarea Concurentă și Distribuită";
    static String grupa = "grupa - R-241";

    // Th3: обходит [0, 798] от начала, потом ждёт сигнал interrupt() от Th1
    static class Th3 extends Thread {
        public void run() {
            for (int i = 0; i <= 798; i++) {
                System.out.print("Th3: " + i + " ");
            }
            waitForInterrupt();
            try {
                printSlowly(disciplina);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Th4 extends Thread {
        public void run() {
            for (int i = 2111; i >= 1456; i--) {
                System.out.print("Th4: " + i + " ");
            }
            try {
                // Метод Thread 2: isAlive()
                while (andrei.th2.isAlive()) {
                    Thread.sleep(10);
                }
                printSlowly(grupa);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static void waitForInterrupt() {
            while (!Thread.interrupted()) {
                Thread.yield();
            }

    }

    static void printSlowly(String text) throws InterruptedException {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            Thread.sleep(100);
        }
        System.out.println();
    }
}




class Andrei {
    int[] a;
    int count;

    String nameofstudents = "Сirlan, Pileaghin";     //выводит Th1
    String name = "Andrei, Alexandr";     //выводит Th2

    Thread1 th1 = new Thread1();
    Thread2 th2 = new Thread2();

    Andrei() {
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

    static void printSlowly(String text) throws InterruptedException {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            Thread.sleep(100);
        }
        System.out.println();
    }

    class Thread1 extends Thread {
        public void run() {
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
                while (th2.getPriority() != 9) {
                    Thread.sleep(10);
                }
                // Метод 1: сигнал "расчёты закончены" через приоритет
                //setPriority(9);

                // Метод 2: ждём, пока Th4 полностью завершится (выведет группу)
                Lab3.th4.join();

                printSlowly(nameofstudents );

                // Сигнал для Th3: теперь твоя очередь
                Lab3.th3.interrupt();
            } catch (InterruptedException e) {
            }
        }
    }

    class Thread2 extends Thread {
        public void run() {
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
//                while (th1.getPriority() != 9) {
//                    Thread.sleep(10);
//                }
                setPriority(9);

                printSlowly(name);
            } catch (InterruptedException e) {
            }
        }
    }
}