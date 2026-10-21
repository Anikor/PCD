public class StartSum implements Runnable {
    private final int[] mas;
    private final Thread th;
    private long total = 0L;
    public StartSum(int[] mas) {
        this.mas = mas;
        this.th = new Thread(this, "Th1");
    }

    public void start() {
        th.start();
    }

    public long getSum() {
        return total;
    }

    @Override
    public void run() {
        int first = -1;

        for (int i = 0; i < mas.length; i++) {
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