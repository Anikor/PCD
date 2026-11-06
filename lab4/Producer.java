import java.util.concurrent.ThreadLocalRandom;
// Производитель: штампует чётные числа и отправляет их на склад пачками по F штук.
// Крутится, пока Main не скажет stop (через interrupt()).
public class Producer extends Thread {
    private static final int MAX_PAUSE_MS = 300;  // макс. пауза между пачками
    private static final int MAX_FACTOR = 10;     // чётные числа: 2 * (1..10) = 2..20
    private final Main.Store warehouse;           // куда складываем
    private final int perTrip;                    // F — сколько штук за один заход
    public Producer(Main.Store store, String name, int batchSize) {
        super(name);
        this.warehouse = store;
        this.perTrip = batchSize;
        setDaemon(true);                          // не мешает завершению программы
    }
    // Собирает одну пачку из чётных чисел
    private int[] makeBatch() {
        int[] batch = new int[perTrip];
        for (int i = 0; i < batch.length; i++) {
            batch[i] = nextEven();
        }
        return batch;
    }
    // Одно случайное чётное число
    private int nextEven() {
        return 2 * ThreadLocalRandom.current().nextInt(1, MAX_FACTOR + 1);
    }
    // Небольшая пауза между заходами (вне монитора склада)
    private void rest() throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextInt(MAX_PAUSE_MS));
    }
    @Override
    public void run() {
        try {
            while (true) {
                warehouse.put(getName(), makeBatch()); // ждёт сам, если склад полон
                rest();
            }
        } catch (InterruptedException stop) {
            // сигнал остановки от Main — просто завершаем поток
        }
    }
}