//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.Arrays;
import java.util.Random;

public class Lab1 {
    public Lab1() {
    }

    public static void main(String[] args) throws InterruptedException {
        int[] mas = new int[100];
        Random r = new Random();

        for(int i = 0; i < mas.length; ++i) {
            mas[i] = r.nextInt(100) + 1;
        }

        System.out.println("Массив: " + Arrays.toString(mas));
        StartSum mine = new StartSum(mas);
        mine.start();
        mine.join();
        System.out.println("Th1 сумма: " + mine.getSum1());
        System.out.println("Th2 сумма: " + mine.getSum2());
        String info = "Лабораторная работа 1. Студенты: Имя Фамилия, Имя Фамилия. Группа R-241";

        for(char c : info.toCharArray()) {
            System.out.print(c);
            Thread.sleep(100L);
        }

        System.out.println();
    }
}
