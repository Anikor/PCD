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

    static Th1 t1 = new Th1();
    static Th2 t2 = new Th2();
    static Th3 t3 = new Th3();
    static Th4 t4 = new Th4();


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


    static class Th1 extends Thread {
        @Override
        public void run() {
            System.out.println("Starting Thread 1");
            for (int i = 0; i + 1 < poz.size(); i += 2) {
                int suma = poz.get(i) + poz.get(i + 1);
                System.out.println("Th1: " + poz.get(i) + " + " + poz.get(i + 1) + " = " + suma);
                try { Thread.sleep(100); } catch (InterruptedException e) { }
            }
            marcheazaTerminat();
            asteaptaToateSarcinile();

            try { t4.join(); } catch (InterruptedException e) { }   // asteapta Th4 sa termine
            afiseazaLitere("Th1 (prenume): " + PRENUME);
        }
    }

    static class Th2 extends Thread {
        @Override
        public void run() {
            System.out.println("Starting Thread 2");

            for (int i = poz.size() - 1; i - 1 >= 0; i -= 2) {
                int suma = poz.get(i) + poz.get(i - 1);
                System.out.println("Th2: " + poz.get(i) + " + " + poz.get(i - 1) + " = " + suma);
                try { Thread.sleep(100); } catch (InterruptedException e) { }
            }
            marcheazaTerminat();
            asteaptaToateSarcinile();

            afiseazaLitere("Th2 (nume): " + NUME);
        }
    }


    static class Th3 extends Thread {
        @Override
        public void run() {
            System.out.println("Starting Thread 3");
            StringBuilder sb = new StringBuilder();
            int c = 0;
            for (int i = 120; i <= 690; i++) {
                sb.append("Th3:").append(i).append(' ');
                if (++c == 10) { System.out.println(sb); sb.setLength(0); c = 0; }
            }
            if (sb.length() > 0) System.out.println(sb);
            marcheazaTerminat();
            asteaptaToateSarcinile();

            while (t1.isAlive()) {
                try { Thread.sleep(50); } catch (InterruptedException e) { }
            }
            afiseazaLitere("Th3 (disciplina): " + DISCIPL);
        }
    }

    static class Th4 extends Thread {
        @Override
        public void run() {
            System.out.println("Starting Thread 4");
            StringBuilder sb = new StringBuilder();
            int c = 0;
            for (int i = 1567; i >= 1000; i--) {
                sb.append("Th4:").append(i).append(' ');
                if (++c == 10) { System.out.println(sb); sb.setLength(0); c = 0; }
            }
            if (sb.length() > 0) System.out.println(sb);
            marcheazaTerminat();
            asteaptaToateSarcinile();

            while (t2.isAlive()) {
                try { Thread.sleep(50); } catch (InterruptedException e) { }
            }
            afiseazaLitere("Th4 (grupa): " + GRUPA);
        }
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
    }
}