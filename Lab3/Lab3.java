import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lab3 {

    static final String NUME     = "Beșleagă și Stegărescu";
    static final String PRENUME  = "Mircea și Ion";
    static final String GRUPA    = "R-241";
    static final String DISCIPL  = "Programarea Concurenta si Distribuita";

    static final int DONE = 9;

    static int[] a = new int[100];
    static List<Integer> poz = new ArrayList<>();

    static Thread t1 = new Mircea.Th1();
    static Thread t2 = new Mircea.Th2();
    static Thread t3 = new Ion.Th3();
    static Thread t4 = new Ion.Th4();


    static void marcheazaTerminat() {
        Thread.currentThread().setPriority(DONE);
    }

    static void asteaptaToateSarcinile() {
        while (!(t1.getPriority() == DONE && t2.getPriority() == DONE
                && t3.getPriority() == DONE && t4.getPriority() == DONE)) {
            try { Thread.sleep(50); } catch (InterruptedException e) { }
        }
    }

    static void afiseazaLitere(String text) {
        for (int i = 0; i < text.length(); i++) {
            try { Thread.sleep(100); } catch (InterruptedException e) { }
            System.out.print(text.charAt(i));
        }
        System.out.println();
    }


    public static void main(String[] args) {
        Random r = new Random();
        System.out.println("Vectorul:");
        for (int i = 0; i < a.length; i++) {
            a[i] = r.nextInt(101) + 15;
            System.out.print(a[i] + " ");
            if (a[i] % 2 == 0) poz.add(i + 1);
        }
        System.out.println("\n");

        t1.start(); t2.start(); t3.start(); t4.start();

        try {
            t1.join(); t2.join(); t3.join(); t4.join();
        } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("\nToate firele s-au terminat.");
    }
}