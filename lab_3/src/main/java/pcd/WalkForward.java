package pcd;

public class WalkForward extends Thread {
    @Override
    public void run() {
        for (int start = 234; start <= 987; start += 10) {
            String row = getName() + ":";
            for (int i = start; i < start + 10 && i <= 987; i++) {
                row += " " + i;
            }
            Main.window.show(row);
        }
        try {
            while (Main.th1.isAlive()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            return;
        }
        Main.window.type(getName() + " – disciplina: Programarea concurentă și distribuită");
    }
}
