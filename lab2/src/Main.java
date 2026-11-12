public class Main {

    public static void main(String[] args) {

        ThreadGroup main = Thread.currentThread().getThreadGroup();

        Runnable r = () -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        PriorityGroup.start(main, r);
        GroupPriority.start(main, r);

        main.list();
    }
}