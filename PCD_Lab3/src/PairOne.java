public class PairOne {
    private Thread th1;
    private Thread th2;
    private PairTwo pairTwo;

    public PairOne(final String lastName, final String firstName) {

        th1 = new Thread() {
            public void run() {
                try {
                    for (int i = 2; i + 2 <= 100; i += 4) {
                        System.out.print(" Th1: " + i + "+" + (i + 2) + "=" + (i + i + 2));
                        Thread.sleep(10);
                    }

                    while (pairTwo.getTh4().isAlive()) {
                        Thread.yield();
                    }
                    Main.printText("Th1 Prenumele: " + firstName);
                    pairTwo.getTh3().interrupt();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        th2 = new Thread() {
            public void run() {
                try {
                    for (int i = 100; i - 2 >= 2; i -= 4) {
                        System.out.print(" Th2: " + i + "+" + (i - 2) + "=" + (i + i - 2));
                        Thread.sleep(10);
                    }

                    Thread.sleep(8000);
                    System.out.println();
                    Main.printText("Th2 Numele: " + lastName);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public Thread getTh1() {
        return th1;
    }

    public Thread getTh2() {
        return th2;
    }

    public void setPairTwo(PairTwo pairTwo) {
        this.pairTwo = pairTwo;
    }

    public void start() {
        th1.start();
        th2.start();
    }
}