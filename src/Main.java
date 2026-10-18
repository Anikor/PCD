import java.util.Random;
public class Main {
    public static void main(String[] args){
        int[] mas = new int[10];
        Random randomnumber = new Random();
        for(int i = 0; i < mas.length; i++){
            mas[i] = randomnumber.nextInt(10) + 1;
        }
        Andrei th3 = new Andrei();
        Andrei th4 = new Andrei();
        th3.start();
        th4.start();
        Alexandr th1 = new Alexandr(mas, 1);
        Alexandr th2 = new Alexandr(mas, 2);
        th1.start();
        th2.start();
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
        @Override
        public void run() {
            int sum = 0;
            for (int i = mas.length - 1; i >= 0; i--) {
                if (mas[i] % 2 != 0) {
                    sum += mas[i];
                }
            }
            System.out.println("Th: сумма = " + sum);
        }

}

