public class Ion {
    Thread stegarescu1 = new Thread("stegarescu1") {
        @Override
        public void run() {
            System.out.println("Starting Thread 3");
            for (int i = 120; i <= 690; i++) {
                System.out.println(getName() + ": " + i);
            }

            Lab3.marcheazaTerminat();
            Lab3.asteaptaToateSarcinile();

            while (Lab3.mircea.t1.isAlive()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    return;
                }
            }
            Lab3.afiseazaLitere("Th3 (disciplina): " + Lab3.DISCIPL);
        }
    };

    Thread stegarescu2 = new Thread("stegarescu2") {
        @Override
        public void run() {
            System.out.println("Starting Thread 4");
            for (int i = 1567; i >= 1000; i--) {
                System.out.println(getName() + ": " + i);
            }

            Lab3.marcheazaTerminat();
            Lab3.asteaptaToateSarcinile();

            while (Lab3.mircea.t2.isAlive()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    return;
                }
            }
            Lab3.afiseazaLitere("Th4 (grupa): " + Lab3.GRUPA);
        }
    };
}
