//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.PrintStream;
import java.util.Objects;

public class StartSum {
    private final int[] mas;
    private Thread th1;
    private Thread th2;
    private Worker w1;
    private Worker w2;

    public StartSum(int[] mas) {
        this.mas = mas;
    }

    public void start() {
        this.w1 = new Worker(0, 1);
        this.w2 = new Worker(this.mas.length - 1, -1);
        this.th1 = new Thread(this.w1, "Th1");
        this.th2 = new Thread(this.w2, "Th2");
        this.th1.start();
        this.th2.start();
    }

    public void join() throws InterruptedException {
        this.th1.join();
        this.th2.join();
    }

    public long getSum1() {
        return this.w1.getTotal();
    }

    public long getSum2() {
        return this.w2.getTotal();
    }

    private class Worker implements Runnable {
        private final int startIdx;
        private final int step;
        private long total;

        Worker(int startIdx, int step) {
            Objects.requireNonNull(StartSum.this);
            super();
            this.total = 0L;
            this.startIdx = startIdx;
            this.step = step;
        }

        public void run() {
            int first = -1;

            for(int i = this.startIdx; i >= 0 && i < StartSum.this.mas.length; i += this.step) {
                if (StartSum.this.mas[i] % 2 == 0) {
                    if (first == -1) {
                        first = i;
                    } else {
                        int pairSum = StartSum.this.mas[first] + StartSum.this.mas[i];
                        this.total += (long)pairSum;
                        PrintStream var10000 = System.out;
                        String var10001 = Thread.currentThread().getName();
                        var10000.println(var10001 + ": " + StartSum.this.mas[first] + " + " + StartSum.this.mas[i] + " = " + pairSum + " (индексы " + first + ", " + i + ")");
                        first = -1;
                    }
                }
            }

            if (first != -1) {
                PrintStream var4 = System.out;
                String var5 = Thread.currentThread().getName();
                var4.println(var5 + ": чётное " + StartSum.this.mas[first] + " (индекс " + first + ") осталось без пары");
            }

        }

        long getTotal() {
            return this.total;
        }
    }
}
