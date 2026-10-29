import java.util.Random;
public class Main {
    public static void main(String[] args){
        int[] mas = new int[100];
        Random randomnumber = new Random();
        for(int i = 0; i < mas.length; i++){
            mas[i] = randomnumber.nextInt(100) + 1;
            System.out.print(mas[i] + " ");
            if(i == 50) {
                System.out.println();
            }
        }
        System.out.println();



        Alexandr a = new Alexandr(mas);
        Alexandr.Thread1 th1 = a.new Thread1();
        Alexandr.Thread2 th2 = a.new Thread2();

        Andrei s = new Andrei(mas);
        Andrei.Th3 th3 = s.new Th3();
        Andrei.Th4 th4 = s.new Th4();

        th1.start();
        th2.start();
        th3.start();
        th4.start();


        try {
            th1.join();
            th2.join();
            th3.join();
            th4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String info = "\nЛабораторную работу выполнили:\nPileaghin Alexandr, R-241\nCirlan Andrei, R-241\n";
        PrinterThread p = new PrinterThread(info);
        p.start();
    }

}

class Alexandr {
    private int[] mas;

    Alexandr(int[] mas) {
        this.mas = mas;
    }

    class Thread1 extends Thread {
        Thread1() {
            super("Thread1");
        }

        @Override
        public void run() {
            int sum = 0;
            int first = 0;
            boolean hasFirst = false;

            for (int i = 0; i < mas.length; i++) {
                System.out.println(getName() + " текущее число: " + mas[i]);

                if (mas[i] % 2 != 0) {
                    if (!hasFirst) {
                        first = mas[i];
                        hasFirst = true;
                    } else {
                        sum += first + mas[i];
                        System.out.println(getName() + " пара: " + first + " + " + mas[i] + ", сумма: " + sum);
                        hasFirst = false;
                    }
                }
            }
            System.out.println(getName() + " Сумма = " + sum);
        }
    }


    class Thread2 extends Thread {
        Thread2() {
            super("Thread2");
        }

        @Override
        public void run() {
            int sum = 0;
            int first = 0;
            int oddCount = 0;
            int i = mas.length - 1;

            while (i >= 0) {
                System.out.println(getName() + " текущее число: " + mas[i]);

                if (mas[i] % 2 != 0) {
                    oddCount++;
                    if (oddCount % 2 == 1) {
                        first = mas[i];
                    } else {
                        sum += first + mas[i];
                        System.out.println(getName() + " пара: " + first + " + " + mas[i] + ", сумма: " + sum);
                    }
                }
                i--;
            }
            System.out.println(getName() + " Сумма = " + sum);
        }
    }
}


class Andrei {
    int[] array;

    Andrei(int[] array) {
        this.array = array;
    }

    int sumOddFromStart(String name) {
        int summa = 0;
        int first = 0;
        boolean hasFirst = false;

        for (int i = 0; i < array.length; i++) {
            if (array[i] % 2 != 0) {
                if (!hasFirst) {
                    first = array[i];      // запоминаем первое число пары
                    hasFirst = true;
                } else {
                    summa += first + array[i];   // пара собрана, суммируем
                    System.out.println("Поток: " + name + " Пара: " + first + " + " + array[i] + ", сумма: " + summa);
                    hasFirst = false;      // корзина снова пуста
                }
            }
        }
        return summa;
    }

    int sumOddFromEnd(String name) {
        int summa = 0;
        int first = 0;
        boolean hasFirst = false;

        for (int i = array.length - 1; i >= 0; i--) {   // отличие только в направлении
            if (array[i] % 2 != 0) {
                if (!hasFirst) {
                    first = array[i];
                    hasFirst = true;
                } else {
                    summa += first + array[i];
                    System.out.println("Поток: " + name + " Пара: " + first + " + " + array[i] + ", СУММА: " + summa);
                    hasFirst = false;
                }
            }
        }
        return summa;
    }

    class Th3 extends Thread {
        Th3() {
            super("Thread3");
        }

        @Override
        public void run() {
            int result = sumOddFromStart(getName());
            System.out.println(getName() + " Сумма = " + result);
        }
    }

    class Th4 extends Thread {
        Th4() {
            super("Thread4");
        }

        @Override
        public void run() {
            int result = sumOddFromEnd(getName());
            System.out.println(getName() + " Сумма = " + result);
        }
    }
}

class PrinterThread extends Thread {
    String text;

    PrinterThread(String text) {
        this.text = text;
    }

    @Override
    public void run() {
        try {
            for (char c : text.toCharArray()) {
                System.out.print(c);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
