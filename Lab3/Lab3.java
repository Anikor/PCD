import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lab3 {
    // Datele din codul colegului
    static final String NUME = "Beșleagă";
    static final String PRENUME = "Mircea";
    static final String GRUPA = "R-241";
    static final String DISCIPL = "Programarea Concurenta si Distribuita";

    static final int DONE = 9;
    static int[] a = new int[100];
    static List<Integer> poz = new ArrayList<>();

    static Mircea mircea = new Mircea();
    static Ion ion = new Ion();

    static void marcheazaTerminat() {
        Thread.currentThread().setPriority(DONE);
    }

    static void asteaptaToateSarcinile() {
        while (mircea.t1.getPriority() != DONE || mircea.t2.getPriority() != DONE
                || ion.stegarescu1.getPriority() != DONE || ion.stegarescu2.getPriority() != DONE) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    static void afiseazaLitere(String text) {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Random r = new Random();
        System.out.println("Vectorul:");
        for (int i = 0; i < a.length; i++) {
            a[i] = r.nextInt(101) + 15;
            System.out.print(a[i] + " ");
            if (a[i] % 2 == 0) {
                poz.add(i + 1);
            }
        }
        System.out.println("\n");

        mircea.t1.start();
        mircea.t2.start();
        ion.stegarescu1.start();
        ion.stegarescu2.start();

        try {
            mircea.t1.join();
            mircea.t2.join();
            ion.stegarescu1.join();
            ion.stegarescu2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
