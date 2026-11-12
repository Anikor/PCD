public class PairTwo {
    private Thread th3;
    private Thread th4;

    public PairTwo(final String group, final String discipline) {

        th3 = new Thread() {
            public void run() {
                try {
                    for (int i = 100; i <= 500; i++) {
                        System.out.println("Th3: " + i);
                        Thread.sleep(10);
                    }
                    th4.join(20000);
                    Main.printText("Th3 Disciplina: " + discipline);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        th4 = new Thread() {
            public void run() {
                try {
                    for (int i = 700; i >= 300; i--) {
                        System.out.println("Th4: " + i);
                        Thread.sleep(10);
                    }
                    Thread.sleep(500);
                    Main.printText("Th4 Grupa: " + group);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public Thread getLastThread() {
        return th3;
    }

    public void start() {
        th3.start();
        th4.start();
    }
}