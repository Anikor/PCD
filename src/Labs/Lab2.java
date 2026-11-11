package Labs;

public class Lab2 {
    public static void main(String[] args) {
        Thread.currentThread().setPriority(4);

        ThreadGroup main = Thread.currentThread().getThreadGroup();


        Thread th1 = new Thread("th1");
        th1.start();

        Thread th2 = new Thread("th2");
        th2.setPriority(9);
        th2.start();

        main.list();

        ThreadGroup GM = new     ThreadGroup(main, "MG");

        Thread Th1 = new Thread(GM, "Th1");
        Th1.setPriority(3);
        Th1.start();

        Thread Th2 = new Thread(GM, "Th2");
        Th2.start();

        Thread Th3 = new Thread(GM, "Th3");
        Th3.start();

        GM.list();


        ThreadGroup GN = new ThreadGroup(main, "GN");

        Thread ThA = new Thread(GN, "ThA");
        ThA.setPriority(5);
        ThA.start();

        ThreadGroup GH = new ThreadGroup(GN, "GH");

        Thread Tha = new Thread(GH, "Tha");
        Tha.start();

        Thread Thb = new Thread(GH, "Thb");
        Thb.start();

        Thread Thc = new Thread(GH, "Thc");
        Thc.setPriority(7);
        Thc.start();

        Thread Thd = new Thread(GH, "Thd");
        Thd.start();

        GH.list();
        GN.list();
    }
}

