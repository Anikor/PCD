package pcd;

public class Producer extends Thread {
    static final String CONSONANTS = "BCDFGHJKLMNPQRSTVWXZ";

    @Override
    public void run() {
        try {
            while (!Main.depot.isDone()) {
                char a = CONSONANTS.charAt((int) (Math.random() * CONSONANTS.length()));
                char b = CONSONANTS.charAt((int) (Math.random() * CONSONANTS.length()));
                Main.depot.put(getName(), a, b);
                Thread.sleep((int) (Math.random() * 600));
            }
        } catch (InterruptedException e) {
            return;
        }
        Main.window.showProducer(getName() + " s-a oprit.");
    }
}
