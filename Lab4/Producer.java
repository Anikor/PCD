public class Producer extends Thread {
    Depozit depozit;
    int perechi;

    public Producer(int perechi, Depozit depozit) {
        this.perechi = perechi;
        this.depozit = depozit;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < perechi; i++) {
                int a = (int) (Math.random() * 450) * 2 + 101;
                int b = (int) (Math.random() * 450) * 2 + 101;
                depozit.Scriere(a, b);
            }
            System.out.println(getName() + " a terminat producerea.");
        } catch (InterruptedException e) {
            System.out.println("Eroare la producator");
        }
        depozit.firTerminat();
    }
}
