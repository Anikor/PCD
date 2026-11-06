public class Ion {

    static void marcheazaTerminat() {
        Thread.currentThread().setPriority(Lab3.DONE);
    }

    static void asteaptaToateSarcinile() {
        while (!(Lab3.t1.getPriority() == Lab3.DONE && Lab3.t2.getPriority() == Lab3.DONE
                && Lab3.t3.getPriority() == Lab3.DONE && Lab3.t4.getPriority() == Lab3.DONE)) {
            try { Thread.sleep(50); } catch (InterruptedException e) { }
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

            try {
                Lab3.t1.join();
                Lab3.t2.join();
                Lab3.t4.join();
            } catch (InterruptedException e) { }
            Lab3.afiseazaLitere("Th3 (disciplina): " + Lab3.DISCIPL);
            System.out.println("\nToate firele s-au terminat.");
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

            try { Lab3.t2.join(); } catch (InterruptedException e) { }   // Th4 controleaza ca Th2 s-a terminat
            Lab3.afiseazaLitere("Th4 (grupa): " + Lab3.GRUPA);
        }
    }
}