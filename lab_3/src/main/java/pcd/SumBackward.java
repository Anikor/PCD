package pcd;

public class SumBackward extends Thread {
    @Override
    public void run() {
        int first = -1;
        try {
            for (int i = Main.mas.length - 1; i >= 0; i--) {
                if (Main.mas[i] % 2 != 0) {
                    if (first == -1) {
                        first = i;
                    } else {
                        Main.window.show(getName() + ": " + first + " + " + i + " = " + (first + i)
                                + "  (" + Main.mas[first] + ", " + Main.mas[i] + ")");
                        first = -1;
                        Thread.sleep(100);
                    }
                }
            }
        } catch (InterruptedException e) {
            return;
        }
        Main.window.type(getName() + " – numele: Drangoi, Ghenova, Chisacov");
        Main.th4.interrupt();
    }
}
