import java.util.ArrayList;

public class Depozit {
    ArrayList<Integer> cabinet = new ArrayList<>();
    int D = 5;
    int fireTerminate = 0;
    boolean finalizat = false;

    public synchronized void Scriere(int a, int b) throws InterruptedException {
        while (cabinet.size() + 2 > D) {
            System.out.println(Thread.currentThread().getName() +
                    " asteapta: depozitul este plin pentru 2 obiecte (" + cabinet.size() + "/" + D + ")");
            wait();
        }

        cabinet.add(a);
        cabinet.add(b);
        System.out.println(Thread.currentThread().getName() +
                " a pus " + a + " si " + b + " | Depozit: " + cabinet);
        notifyAll();
    }

    public synchronized int extragere() throws InterruptedException {
        while (cabinet.isEmpty()) {
            System.out.println(Thread.currentThread().getName() + " asteapta: depozitul este gol");
            wait();
        }

        int rezultat = cabinet.remove(0);
        System.out.println(Thread.currentThread().getName() +
                " a extras " + rezultat + " | Depozit: " + cabinet);
        notifyAll();
        return rezultat;
    }

    public synchronized void firTerminat() {
        fireTerminate++;
        if (fireTerminate == 7) {
            finalizat = true;
            notifyAll();
        }
    }

    public synchronized void asteaptaFinal() throws InterruptedException {
        while (!finalizat) {
            wait();
        }
    }
}