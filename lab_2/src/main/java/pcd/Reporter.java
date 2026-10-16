package pcd;

public class Reporter extends Thread {
    private final Controller window;

    public Reporter(ThreadGroup group, String name, Controller window) {
        super(group, name);
        this.window = window;
    }

    @Override
    public void run() {
        window.show("run(): firul " + getName() + " din grupul " + getThreadGroup().getName()
                + ", prioritate " + getPriority());
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            interrupt();
        }
    }
}
