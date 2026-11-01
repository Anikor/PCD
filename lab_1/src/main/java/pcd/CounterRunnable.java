package pcd;

public class CounterRunnable implements Runnable {
    private final int startIndex;
    private final int endIndex;
    private final int step;
    private final int[] data;
    private final Controller controller;

    public CounterRunnable(int startIndex, int endIndex, int step, int[] data, Controller controller) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.step = step;
        this.data = data;
        this.controller = controller;
    }

    @Override
    public void run() {
        String threadTag = Thread.currentThread().getName();
        Integer firstIndex = null;

        for (int i = startIndex; step > 0 ? i <= endIndex : i >= endIndex; i += step) {
            if (isOdd(data[i])) {
                if (firstIndex == null) {
                    firstIndex = i;
                } else {
                    logPair(threadTag, firstIndex, i);
                    firstIndex = null;
                }
            }
        }
    }

    private boolean isOdd(int number) {
        return number % 2 != 0;
    }

    private void logPair(String tag, int idx1, int idx2) {
        int indexSum = idx1 + idx2;
        String message = String.format("%s: %d + %d = %d  (%d, %d)",
                tag, idx1, idx2, indexSum, data[idx1], data[idx2]);
        controller.show(message);
    }
}
