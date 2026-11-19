public class ProducatorConsumator {
    public static void main(String[] args) throws InterruptedException {
        Depozit d1 = new Depozit();

        Producer p1 = new Producer(2, d1);
        Producer p2 = new Producer(1, d1);
        Producer p3 = new Producer(1, d1);

        Consumer c1 = new Consumer(d1);
        Consumer c2 = new Consumer(d1);
        Consumer c3 = new Consumer(d1);
        Consumer c4 = new Consumer(d1);

        p1.setName("Producator 1");
        p2.setName("Producator 2");
        p3.setName("Producator 3");
        c1.setName("Consumator 1");
        c2.setName("Consumator 2");
        c3.setName("Consumator 3");
        c4.setName("Consumator 4");

        p1.start();
        p2.start();
        p3.start();
        c1.start();
        c2.start();
        c3.start();
        c4.start();

        d1.asteaptaFinal();
        System.out.println("Toate cele 7 fire au terminat. Au fost consumate 8 obiecte.");
    }
}