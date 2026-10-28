import java.util.Arrays;
import java.util.Random;

public class Lab1 {
    public static void main(String[] args) throws InterruptedException {
        int[] mas = new int[100];
        Random r = new Random();

        for (int i = 0; i < mas.length; i++) {
            mas[i] = r.nextInt(100) + 1;
        }

        System.out.println("Массив: " + Arrays.toString(mas));

        Balka balka = new Balka(mas);



        balka.start();
        Sergei sergei.start();



        System.out.println("Th1 сумма: " + balka.getSum1());
        System.out.println("Th2 сумма: " + balka.getSum2());
        System.out.println("Th3 сумма: " + Sergei sergei.getSum1());
        System.out.println("Th4 сумма: " + Sergei sergei.getSum2());

        String info = " Лабораторная работа 1. Студенты: Гордей Сергей, Балан Александр. Группа R-241";

        for (char c : info.toCharArray()) {
            System.out.print(c);
            Thread.sleep(100L);
        }

        System.out.println();
    }
}