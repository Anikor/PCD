public class Producer extends Thread {
    private final Store store;

    private static final int[] EVEN = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20};

    public Producer(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        while (true) {
            store.put(getName(),
                    EVEN[(int) (Math.random() * EVEN.length)],
                    EVEN[(int) (Math.random() * EVEN.length)]);
        }
    }
}
