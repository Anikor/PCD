public class PairOne {
    private Thread th1;
    private Thread th2;

    public PairOne(final String lastName, final String firstName, final Thread pairTwoLast) {

        th1 = new Thread() {
            public void run() {
                try {
                    for (int i = 2; i + 2 <= 100; i += 4) {
                        System.out.println("Th1: " + i + "+" + (i + 2) + "=" + (i + i + 2));
                        Thread.sleep(10);
                    }
                    th2.join();
                    Main.printText("Th1 Prenumele: " + firstName);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        th2 = new Thread() {
            public void run() {
                try {
                    for (int i = 100; i - 2 >= 2; i -= 4) {
                        System.out.println("Th2: " + i + "+" + (i - 2) + "=" + (i + i - 2));
                        Thread.sleep(10);
                    }
                    pairTwoLast.join(60000);
                    Main.printText("Th2 Numele: " + lastName);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void start() {
        th1.start();
        th2.start();
    }
}