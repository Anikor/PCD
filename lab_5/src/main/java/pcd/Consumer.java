package pcd;

public class Consumer implements Runnable {
    static final int NEEDED = 12;
    private final String name;

    public Consumer(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        String taken = "";
        try {
            for (int i = 0; i < NEEDED; i++) {
                taken += Main.depot.take(name);
                Thread.sleep((int) (Math.random() * 500));
            }
        } catch (InterruptedException e) {
            return;
        }
        Main.window.showConsumer(name + " a primit " + NEEDED + " obiecte (" + taken + ") și s-a terminat.");
    }
}
