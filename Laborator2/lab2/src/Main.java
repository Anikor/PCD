class ThreadGroup3 {
    public static void main (String[] args){

        Thread.currentThread().setPriority(2);

        ThreadGroup group1 = Thread.currentThread().getThreadGroup();

        Thread th1 = new Thread("th1");
        //th1.setPriority(2);
        th1.start();

        Thread th2 = new Thread("th2");
        //th2.setPriority(2);
        th2.start();
        group1.list();

        ThreadGroup GN = new ThreadGroup(group1,"GN");
        Thread ThA = new Thread(GN,"ThA");
        //ThA.setPriority(2);
        ThA.start();
        GN.list();

        ThreadGroup GH = new ThreadGroup(GN, "GH");
        Thread Tha = new Thread(GH, "Tha");
        Tha.setPriority(3);
        Tha.start();

        Thread Thb = new Thread(GH, "Thb");
        //Thb.setPriority(2);
        Thb.start();

        Thread Thc = new Thread(GH, "Thc");
        Thc.setPriority(5);
        Thc.start();

        Thread Thd = new Thread(GH, "Thd");
        //Thd.setPriority(2);
        Thd.start();
        GH.list();

        ThreadGroup GM = new ThreadGroup(group1,"GM");
        Thread Th1 = new Thread(GM,"Th1");
        Th1.setPriority(1);
        Th1.start();

        Thread Th2 = new Thread(GM,"Th2");
        //Th2.setPriority(2);
        Th2.start();

        Thread Th3 = new Thread(GM,"Th3");
        //Th3.setPriority(2);
        Th3.start();
        GM.list();
    }
}
