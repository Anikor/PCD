public class Consumer extends Thread {
    private final Store store;
    private final int need;

    public Consumer(Store store, int need) {
        this.store = store;
        this.need = need;
    }

    @Override
    public void run() {
        for (int i = 0; i < need; i++) {
            store.get(getName());
        }
        System.out.println(getName() + " взял " + need + " числа. Поток завершён");
    }
}
