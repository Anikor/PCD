import java.util.Random;
// Потребитель: берёт со склада по 1 объекту, пока не получит нужное количество (Z).
public class Consumer extends Thread {
    private final Main.Store store;
    private final int needed;     // Z
    private final Random rnd = new Random();
    public Consumer(Main.Store store, String name, int needed) {
        super(name);
        this.store = store;
        this.needed = needed;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < needed; i++) {
                store.get(getName());
                Thread.sleep(rnd.nextInt(400));
            }
            System.out.println(" " + getName() + " получил все " + needed + " объектов, завершён");
        } catch (InterruptedException e) {
            // остановили — выходим
        }
    }
}