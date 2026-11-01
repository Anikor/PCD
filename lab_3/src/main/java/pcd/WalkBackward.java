package pcd;

public class WalkBackward extends Thread {
    @Override
    public void run() {
        for (int start = 890; start >= 123; start -= 10) {
            String row = getName() + ":";
            for (int i = start; i > start - 10 && i >= 123; i--) {
                row += " " + i;
            }
            Main.window.show(row);
        }
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            Main.window.type(getName() + " – grupa: R-241");
        }
    }
}
