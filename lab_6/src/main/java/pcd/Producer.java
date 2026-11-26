package pcd;

public class Producer extends Thread {
    static final String CONSONANTS = "BCDFGHJKLMNPQRSTVWXZ";

    @Override
    public void run() {
        try {
            char c = CONSONANTS.charAt((int) (Math.random() * CONSONANTS.length()));
            while (Main.depot.put(getName(), c)) {
                Thread.sleep((int) (Math.random() * 300));
                c = CONSONANTS.charAt((int) (Math.random() * CONSONANTS.length()));
            }
        } catch (InterruptedException e) {
            return;
        }
        Main.window.showProducer(getName() + " s-a oprit.");
    }
}
