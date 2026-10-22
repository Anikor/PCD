import java.util.Random;
public class Main {
    public static void main(String[] args){
        int[] mas = new int[10];
        Random randomnumber = new Random();
        for(int i = 0; i < mas.length; i++){
            mas[i] = randomnumber.nextInt(10) + 1;
        }
        Andrei s = new Andrei(mas);
        Andrei.Th3 th3 = s.new Th3();
        Andrei.Th4 th4 = s.new Th4();
        th3.start();
        th4.start();
        Alexandr th1 = new Alexandr(mas, 1);
        Alexandr2 th2 = new Alexandr2(mas, 2);
        th1.start();
        th2.start();

        String info = "\nЛабораторную работу выполнили:\nPileaghin Alexandr, R-241\nCirlan Andrei, R-241\n";
        PrinterThread p = new PrinterThread(info);
        p.start();
    }

}

class Alexandr extends Thread
{
    private int[] masive;
    int summa = 0;



    Alexandr(int [] mas, int number_of_Thread)
    {
        this.masive = mas;
        super("Thread" + number_of_Thread);
    }



    public void run()
    {
        for(int i = 0; i < masive.length; i++)
        {
            System.out.println("Поток: " + getName() + " Текущее число: " + masive[i]);

            if (masive[i] % 2 != 0) {
                summa += masive[i];
                System.out.println("Поток: " + getName() + " Текущая СУММА: " + summa);
            }
        }
        System.out.println(getName() + " Сумма = " + summa);
    }
}

class Alexandr2 extends Thread
{
    private int[] masive;
    int summa = 0;

    Alexandr2(int [] mas, int number_of_Thread)
    {
        this.masive = mas;
        super("Thread" + number_of_Thread);
    }

    public void run()
    {
        for(int i = masive.length - 1; i >= 0; i--)
        {
            System.out.println("Поток: " + getName() + " Текущее число: " + masive[i]);
            if (masive[i] % 2 != 0) {
                summa += masive[i];
                System.out.println("Поток: " + getName() + " Текущая СУММА: " + summa);
            }
        }
        System.out.println(getName() + " Сумма = " + summa);
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
            System.out.println("Поток: " + name + " Текущее число: " + array[i]);
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
            System.out.println("Поток: " + name + " Текущее число: " + array[i]);
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
