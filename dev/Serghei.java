public class Serghei {

    private final int[] mas;

    public Serghei(int[] mas) {
        this.mas = mas;
    }

    public void start() {
        new Thread(() -> work(0, 1), "Th1").start();                 // с начала
        new Thread(() -> work(mas.length - 1, -1), "Th2").start();   // с конца
    }

    private void work(int startIdx, int step) {
        String name = Thread.currentThread().getName();
        long total = 0;
        int first = -1;

        for (int i = startIdx; i >= 0 && i < mas.length; i += step) {
            if (mas[i] % 2 == 0) {
                if (first == -1) {
                    first = i;
                } else {
                    int pairSum = mas[first] + mas[i];
                    total += pairSum;
                    System.out.println(name + ": " + mas[first] + " + " + mas[i]
                            + " = " + pairSum + " (индексы " + first + ", " + i + ")");
                    first = -1;
                    pause();
                }
            }
        }

        if (first != -1) {
            System.out.println(name + ": чётное " + mas[first]
                    + " (индекс " + first + ") осталось без пары");
        }

        System.out.println(name + ": сумма = " + total);

    }

    private void pause() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}