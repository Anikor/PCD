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

        StartSum startSum = new StartSum(mas);
//        EndSum endSum = new EndSum(mas);

        startSum.start();
//        endSum.start();

        startSum.join();
//        endSum.join();

       // System.out.println("Th1 сумма: " + startSum.getSum());
//git merge --abort        System.out.println("Th2 сумма: " + endSum.getSum());

        String info = "Лабораторная работа 1. Студенты: Гордей Сергей, Балан Александр. Группа R-241";

        for (char c : info.toCharArray()) {
            System.out.print(c);
            Thread.sleep(100);
        }

        System.out.println();
    }
}
