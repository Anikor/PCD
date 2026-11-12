import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Mircea {
    static List<Integer> pozitiiPare = new ArrayList<>();
    static void genereazaVector() {
        Random aleator = new Random();
        int[] vector = new int[100];

        System.out.println("Vectorul:");
        for (int i = 0; i < vector.length; i++) {
            vector[i] = aleator.nextInt(101) + 15;
            System.out.print(vector[i] + " ");
            if (vector[i] % 2 == 0) {
                pozitiiPare.add(i + 1);
            }
        }
        System.out.println("\n");
    }

    static class Th1 extends Thread {
        @Override
        public void run() {
            System.out.println("Starting Thread 1");

            for (int i = 0; i + 1 < pozitiiPare.size(); i += 2) {
                int prima = pozitiiPare.get(i);
                int aDoua = pozitiiPare.get(i + 1);
                System.out.println("Th1: " + prima + " + " + aDoua
                        + " = " + (prima + aDoua));
            }

            Lab3.asteaptaToateSarcinile();

            try {
                Lab3.t4.join();
            } catch (InterruptedException e) {
                return;
            }

            Lab3.afiseazaLitere("Th1 (prenume): " + Lab3.PRENUME);
        }
    }

    static class Th2 extends Thread {
        @Override
        public void run() {
            System.out.println("Starting Thread 2");

            for (int i = pozitiiPare.size() - 1; i > 0; i -= 2) {
                int ultima = pozitiiPare.get(i);
                int anterioara = pozitiiPare.get(i - 1);
                System.out.println("Th2: " + ultima + " + " + anterioara
                        + " = " + (ultima + anterioara));
            }

            Lab3.asteaptaToateSarcinile();
            Lab3.afiseazaLitere("Th2 (nume): " + Lab3.NUME);
        }
    }
}