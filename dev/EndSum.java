public class EndSum implements Runnable {
    private final int[] mas;
    private final Thread th;
    private int total = 0;

    public EndSum(int[] mas) {
        this.mas = mas;
        this.th = new Thread(this, "Th2");
    }

    public void start() {
        th.start();
    }

    public void join() throws InterruptedException {
        th.join();
    }

    public long getSum() {
        return total;
    }

    @Override
    public void run() {
        int first = -1;

        // идём с конца массива к началу
        for (int i = mas.length - 1; i >= 0; i--) {
            if (mas[i] % 2 == 0) {
                if (first == -1) {
                    first = i;
                } else {
                    int pairSum = mas[first] + mas[i];
                    total += pairSum;
                    System.out.println(Thread.currentThread().getName() + ": "
                            + mas[first] + " + " + mas[i] + " = " + pairSum
                            + " (индексы " + first + ", " + i + ")");
                    first = -1;
                }
            }
        }

        if (first != -1) {
            System.out.println(Thread.currentThread().getName() + ": чётное "
                    + mas[first] + " (индекс " + first + ") осталось без пары");
        }
    }
}