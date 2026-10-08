package pcd;

public class CounterRunnableAuto implements Runnable {
    private final int from, to, step;
    private final int[] mas;
    private final Controller window;
    private final Thread thread;

    public CounterRunnableAuto(String name, int from, int to, int step, int[] mas, Controller window) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.mas = mas;
        this.window = window;
        thread = new Thread(this, name);
        thread.start();
    }

    @Override
    public void run() {
        int first = -1;

        for (int i = from; i != to + step; i += step) {
            if (mas[i] % 2 != 0) {
                if (first == -1) {
                    first = i;
                } else {
                    window.show(thread.getName() + ": " + first + " + " + i + " = " + (first + i)
                            + "  (" + mas[first] + ", " + mas[i] + ")");
                    first = -1;
                }
            }
        }
    }

    public void join() throws InterruptedException {
        thread.join();
    }
}
