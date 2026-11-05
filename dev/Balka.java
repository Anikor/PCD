import java.util.Random;

public class Balka implements Runnable {
    private final int[] mas;
    private int sum1 = 0;
    private int sum2 = 0;


    public Balka(int[] mas) {
        this.mas = mas;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        if (name.equals("Th1")) {
            fromStart();
        } else
        {
            fromEnd();
        }
        System.out.println("Finish");
    }



    private void fromStart() {
        int i = 0;
        while (i < mas.length) {
            while (i < mas.length && mas[i] % 2 != 0) i++;
            if (i >= mas.length) break;
            int a = mas[i];
            i++;

            while (i < mas.length && mas[i] % 2 != 0) i++;
            if (i >= mas.length) break;
            int b = mas[i];
            i++;

            sum1 += a + b;
            System.out.println("Th1: " + a + " + " + b + " = " + (a + b));

        }
    }

    private void fromEnd() {
        int i = mas.length - 1;
        while (i >= 0) {
            while (i >= 0 && mas[i] % 2 != 0) i--;
            if (i < 0) break;
            int a = mas[i];
            i--;

            while (i >= 0 && mas[i] % 2 != 0) i--;
            if (i < 0) break;
            int b = mas[i];
            i--;

            sum2 += a + b;
            System.out.println("Th2: " + a + " + " + b + " = " + (a + b));

        }
    }
}