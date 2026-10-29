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

        Serghei th3 = new Serghei(mas, true, "Th3");    // с начала
        Serghei th4 = new Serghei(mas, false, "Th4");   // с конца
        th3.start();
        th4.start();

        Thread.sleep(2000);
        String info = "Лабораторная работа 1. Студенты: Гордей Сергей, Балан Александр. Группа R-241";
        for (char c : info.toCharArray()) {
            System.out.print(c);
            Thread.sleep(100L);
        }
        System.out.println();
    }
}