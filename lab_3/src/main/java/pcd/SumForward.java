package pcd;

public class SumForward extends Thread {
    @Override
    public void run() {
        int first = -1;
        try {
            for (int i = 0; i < Main.mas.length; i++) {
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
            Main.th4.join();
        } catch (InterruptedException e) {
            return;
        }
        Main.window.type(getName() + " – prenumele: Karina, Nica, Denis");
    }
}
