public class Ion {
    Thread stegarescu1 = new Thread("stegarescu1") {
        @Override
        public void run() {
            System.out.println("Starting Thread 3");

            for (int numar = 120; numar <= 690; numar++) {
                System.out.println(getName() + ": " + numar);
            }

            Lab3.asteaptaToateSarcinile();

            while (Lab3.t1.isAlive()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    return;
                }
            }

            Lab3.afiseazaLitere("Th3 (disciplina): " + Lab3.DISCIPLINA);
        }
    };

    Thread stegarescu2 = new Thread("stegarescu2") {
        @Override
        public void run() {
            System.out.println("Starting Thread 4");

            for (int numar = 1567; numar >= 1000; numar--) {
                System.out.println(getName() + ": " + numar);
            }

            Lab3.asteaptaToateSarcinile();

            while (Lab3.t2.isAlive()) {
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