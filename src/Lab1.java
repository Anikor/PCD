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
        Serghei serghei = new Serghei(mas);


        balka.start();
        serghei.start();

        String info = " Лабораторная работа 1. Студенты: Гордей Сергей, Балан Александр. Группа R-241";

        for (char c : info.toCharArray()) {
            System.out.print(c);
            Thread.sleep(100L);
        }

        System.out.println();
    }
}