public class Main1 {
    public static void main(String[] args) {
        ThreadGroup main = Thread.currentThread().getThreadGroup();

        Runnable r = () -> {
            try { Thread.sleep(2000); } catch (InterruptedException e) { }
        };

        Thread th1 = new Thread(main, r, "Th1");
        th1.setPriority(2);
        th1.start();

        Thread th2 = new Thread(main, r, "Th2");
        th2.setPriority(6);
        th2.start();

        ThreadGroup GE = new ThreadGroup(main, "GE");

        Thread ThA = new Thread(GE, r, "ThA");
        ThA.setPriority(2);
        ThA.start();

        ThreadGroup GH = new ThreadGroup(GE, "GH");

        Thread Tha = new Thread(GH, r, "Tha");
        Tha.setPriority(3);
        Tha.start();

        Thread Thb = new Thread(GH, r, "Thb");
        Thb.setPriority(2);
        Thb.start();

        Thread Thc = new Thread(GH, r, "Thc");
        Thc.setPriority(1);
        Thc.start();

        Thread Thd = new Thread(GH, r, "Thd");
        Thd.setPriority(1);
        Thd.start();

        ThreadGroup GK = new ThreadGroup(main, "GK");

        Thread gkTh1 = new Thread(GK, r, "Th1");
        gkTh1.setPriority(2);
        gkTh1.start();

        Thread gkTh2 = new Thread(GK, r, "Th2");
        gkTh2.setPriority(5);
        gkTh2.start();

        Thread gkTh3 = new Thread(GK, r, "Th3");
        gkTh3.setPriority(2);
        gkTh3.start();

        main.list();
    }
}