public class Balka {
    private final int[] mas;
    private Thread th1, th2;
    private int sum1 = 0;
    private int sum2 = 0;

    public Balka(int[] mas) {
        this.mas = mas;
    }


    public void start() {
        th1 = new Thread(new Runnable() {
            @Override
            public void run() { fromStart(); }   // код Th1
        }, "Th1");

        th2 = new Thread(new Runnable() {
            @Override
            public void run() { fromEnd(); }     // код Th2
        }, "Th2");

        th1.start();
        th2.start();
    }


    public int getSum1() { return sum1; }
    public int getSum2() { return sum2; }


    private void fromStart() {
        int i = 0;
        while (i < mas.length) {

            while (i < mas.length && mas[i] % 2 != 0) i++;
            if (i >= mas.length) break;   // чётные закончились
            int a = mas[i];
            i++;

            // ищем второе чётное число пары
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