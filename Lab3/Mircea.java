public class Mircea {

    static void marcheazaTerminat() {
        Thread.currentThread().setPriority(Lab3.DONE);
    }

    static void asteaptaToateSarcinile() {
        while (!(Lab3.t1.getPriority() == Lab3.DONE && Lab3.t2.getPriority() == Lab3.DONE
                && Lab3.t3.getPriority() == Lab3.DONE && Lab3.t4.getPriority() == Lab3.DONE)) {
            try { Thread.sleep(50); } catch (InterruptedException e) { }
        }
    }

    static class Th1 extends Thread {
        @Override
        public void run() {
            System.out.println("Starting Thread 1");
            for (int i = 0; i + 1 < Lab3.poz.size(); i += 2) {
                int suma = Lab3.poz.get(i) + Lab3.poz.get(i + 1);
                System.out.println("Th1: " + Lab3.poz.get(i) + " + " + Lab3.poz.get(i + 1) + " = " + suma);
                try { Thread.sleep(100); } catch (InterruptedException e) { }
            }
            marcheazaTerminat();
            asteaptaToateSarcinile();

            try { Lab3.t4.join(); } catch (InterruptedException e) { }   // asteapta Th4 sa termine
            Lab3.afiseazaLitere("Th1 (prenume): " + Lab3.PRENUME);
        }
    }


    static class Th2 extends Thread {
        @Override
        public void run() {
            System.out.println("Starting Thread 2");
            for (int i = Lab3.poz.size() - 1; i - 1 >= 0; i -= 2) {
                int suma = Lab3.poz.get(i) + Lab3.poz.get(i - 1);
                System.out.println("Th2: " + Lab3.poz.get(i) + " + " + Lab3.poz.get(i - 1) + " = " + suma);
                try { Thread.sleep(100); } catch (InterruptedException e) { }
            }
            marcheazaTerminat();
            asteaptaToateSarcinile();

            Lab3.afiseazaLitere("Th2 (nume): " + Lab3.NUME);             // primul care afiseaza
        }
    }
}