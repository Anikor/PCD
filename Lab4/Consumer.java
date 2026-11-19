public class Consumer extends Thread {
    int[] cabineti = new int[2];
    Depozit depozit;

    public Consumer(Depozit depozit) {
        this.depozit = depozit;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < cabineti.length; i++) {
                cabineti[i] = depozit.extragere();
                System.out.println(getName() + " a consumat " + cabineti[i]);
            }
            System.out.println(getName() + " a consumat 2 obiecte si a terminat.");
        } catch (InterruptedException e) {
            System.out.println("Eroare la consumator");
        }
        depozit.firTerminat();
    }
}
