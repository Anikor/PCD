package pcd;

public class Consumer extends Thread {
    static final int NEEDED = 12;

    @Override
    public void run() {
        String taken = "";
        try {
            for (int i = 0; i < NEEDED; i++) {
                taken += Main.depot.get(getName());
                Thread.sleep((int) (Math.random() * 300));
            }
        } catch (InterruptedException e) {
            return;
        }
        Main.window.showConsumer(getName() + " a primit " + NEEDED + " obiecte (" + taken + ") și s-a terminat.");
    }
}
