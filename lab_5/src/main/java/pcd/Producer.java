package pcd;

public class Producer implements Runnable {
    static final String CONSONANTS = "BCDFGHJKLMNPQRSTVWXZ";
    private final String name;

    public Producer(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            char a = CONSONANTS.charAt((int) (Math.random() * CONSONANTS.length()));
            char b = CONSONANTS.charAt((int) (Math.random() * CONSONANTS.length()));
            while (Main.depot.put(name, a, b)) {
                Thread.sleep((int) (Math.random() * 200));
                a = CONSONANTS.charAt((int) (Math.random() * CONSONANTS.length()));
                b = CONSONANTS.charAt((int) (Math.random() * CONSONANTS.length()));
            }
        } catch (InterruptedException e) {
            return;
        }
        Main.window.showProducer(name + " s-a oprit.");
    }
}
