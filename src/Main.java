import java.util.Random;
public class Main {
    public static void main(String[] args){
        int[] mas = new int[10];
        Random randomnumber = new Random();
        for(int i = 0; i < mas.length; i++){
            mas[i] = randomnumber.nextInt(10) + 1;
        }
        Andrei th3 = new Andrei(mas,3);
        Andrei th4 = new Andrei(mas,4);
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


class Andrei extends Thread {
        int[] mas;

        Andrei (int[] mas, int numberThreads){
            super("Thread " + numberThreads);
            this.mas = mas;
        }

        @Override
        public void run() {
            int sum = 0;
            for (int i = mas.length - 1; i >= 0; i--) {
                System.out.println("Поток: " + getName() + " Текущее число: " + mas[i]);
                if (mas[i] % 2 != 0) {
                    sum += mas[i];
                    System.out.println("Поток: " + getName() + " Текущая СУММА: " + sum);
                }
            }
            System.out.println(getName() + " Сумма = " + sum);
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
