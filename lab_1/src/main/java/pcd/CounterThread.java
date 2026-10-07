package pcd;

public class CounterThread extends Thread {
    private final int from, to, step;
    private final int[] mas;
    private final Controller window;

    public CounterThread(String name, int from, int to, int step, int[] mas, Controller window) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.mas = mas;
        this.window = window;
    }

    @Override
    public void run() {
        int first = -1;

        for (int i = from; i != to + step; i += step) {
            if (mas[i] % 2 != 0) {
                if (first == -1) {
                    first = i;
                } else {
                    window.show(1, getName() + ": " + first + " + " + i + " = " + (first + i)
                            + "  (" + mas[first] + ", " + mas[i] + ")");
                    first = -1;
                }
            }
        }
    }
}
