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
        Alexandr th2 = new Alexandr(mas, 2);
        th1.start();
        th2.start();

        String info = "\nЛабораторную работу выполнили:\nPileaghin Alexandr, R-241\nCirlan Andrei, R-241\n";
        PrinterThread p = new PrinterThread(info);
        p.start();
    }

}

class Alexandr extends Thread{
    private int[] mas;
    int sum = 0;

    Alexandr(int [] mas, int number_of_Thread){
        this.mas = mas;
        super("Thread" + number_of_Thread);
    }

    public void run(){
        for(int i = 0; i < mas.length; i++){
            System.out.println("Поток: " + getName() + " Текущее число: " + mas[i]);
            sum += mas[i];
            System.out.println("Поток: " + getName() + " Текущая СУММА: " + sum);
        }
        System.out.println(getName() + " Сумма = " + sum);
    }
}


class Andrei {
    int[] array;

    Andrei(int[] array) {
        this.array = array;
    }

    int sumOddFromStart() {
        int summa = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] % 2 != 0) {
                summa += array[i];
            }
        }
        return summa;
    }

    int sumOddFromEnd() {
        int summa = 0;
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] % 2 != 0) {
                summa += array[i];
            }
        }
        return summa;
    }

    class Th3 extends Thread {
        Th3() {
            super("Th3");
        }

        @Override
        public void run() {
            System.out.println(getName() + " (с начала): сумма = " + sumOddFromStart());
        }
    }

    class Th4 extends Thread {
        Th4() {
            super("Th4");
        }

        @Override
        public void run() {
            System.out.println(getName() + " (с конца): сумма = " + sumOddFromEnd());
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
