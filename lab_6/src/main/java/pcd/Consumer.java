package pcd;

public class Consumer extends Thread {
    @Override
    public void run() {
        try {
            while (Main.depot.get(getName())) {
                Thread.sleep((int) (Math.random() * 300));
            }
        } catch (InterruptedException e) {
            return;
        }
        Main.window.showConsumer(getName() + " s-a oprit.");
    }
}
