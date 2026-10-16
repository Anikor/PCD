package pcd;

import java.util.ArrayList;
import java.util.List;

public class Structure {
    public static final String FORMULA =
            "Main{GO{GZ{Tha(1), Thb(3), Thc(3), Thd(7)}}, GV{ThA(3)}, GF{Th1(5), Th2(3), Th3(9)}, Th1(3), Th2()}";

    private final ThreadGroup main = new ThreadGroup("Main");
    private final List<Thread> threads = new ArrayList<>();
    private final boolean advanced;
    private final Controller window;

    public Structure(boolean advanced, Controller window) {
        this.advanced = advanced;
        this.window = window;
        ThreadGroup go = new ThreadGroup(main, "GO");
        ThreadGroup gz = new ThreadGroup(go, "GZ");
        ThreadGroup gv = new ThreadGroup(main, "GV");
        ThreadGroup gf = new ThreadGroup(main, "GF");
        add(gz, "Tha", 1);
        add(gz, "Thb", 3);
        add(gz, "Thc", 3);
        add(gz, "Thd", 7);
        add(gv, "ThA", 3);
        add(gf, "Th1", 5);
        add(gf, "Th2", 3);
        add(gf, "Th3", 9);
        add(main, "Th1", 3);
        add(main, "Th2", 5);
    }

    private void add(ThreadGroup group, String name, int priority) {
        Thread thread = advanced ? new Reporter(group, name, window) : new Thread(group, new Waiting(), name);
        thread.setPriority(priority);
        threads.add(thread);
    }

    public ThreadGroup getMain() {
        return main;
    }

    public void start() {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void join() throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
