import java.util.LinkedList;

// Общий Main: параметры варианта 1, склад (Store) и запуск потоков. Вывод — в терминал.
public class Main {
    // ===== Параметры варианта 1 =====
    static final int X = 2;   // производителей
    static final int Y = 3;   // потребителей
    static final int Z = 11;  // сколько объектов должен получить каждый потребитель
    static final int D = 8;   // размер склада
    static final int F = 2;   // объектов за раз у производителя

    // ===== Склад (общий ресурс). Вся синхронизация потоков живёт здесь =====
    static class Store {
        private final LinkedList<Integer> items = new LinkedList<>();

        // Потребитель берёт 1 объект
        public synchronized void get(String name) throws InterruptedException {
            boolean warned = false;
            while (items.isEmpty()) {          // while, а не if — защита от ложных пробуждений
                if (!warned) {
                    System.out.println(" Склад ПУСТ — " + name + " ждёт");
                    warned = true;
                }
                wait();                        // отпускаем монитор и засыпаем
            }
            int v = items.removeLast();
            System.out.println(name + " взял: " + v + "  | на складе: " + items);
            notifyAll();                       // будим производителей (освободилось место)
        }

        // Производитель кладёт F объектов за раз
        public synchronized void put(String name, int[] values) throws InterruptedException {
            boolean warned = false;
            while (items.size() + F > D) {     // нужно место сразу под всю пачку
                if (!warned) {
                    System.out.println(" Склад ПОЛОН — " + name + " ждёт");
                    warned = true;
                }
                wait();
            }
            StringBuilder sb = new StringBuilder();
            for (int v : values) {
                items.add(v);
                sb.append(v).append(' ');
            }
            System.out.println(name + " положил: " + sb.toString().trim() + "  | на складе: " + items);
            notifyAll();                       // будим потребителей (появились данные)
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Store store = new Store();

        Producer[] producers = new Producer[X];
        for (int i = 0; i < X; i++) {
            producers[i] = new Producer(store, "Производитель №" + (i + 1), F);
        }

        Consumer[] consumers = new Consumer[Y];
        for (int i = 0; i < Y; i++) {
            consumers[i] = new Consumer(store, "Потребитель №" + (i + 1), Z);
        }

        for (Producer p : producers) p.start();
        for (Consumer c : consumers) c.start();

        // Ждём, пока все потребители получат свои Z объектов
        for (Consumer c : consumers) c.join();

        // Останавливаем производителей
        for (Producer p : producers) p.interrupt();
        System.out.println("Все потребители удовлетворены, работа окончена");
    }
}
