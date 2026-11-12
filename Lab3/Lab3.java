public class Lab3 {
    static final String NUME = "Beșleagă și Stegărescu";
    static final String PRENUME = "Mircea și Ion";
    static final String GRUPA = "R-241";
    static final String DISCIPLINA = "Programarea Concurenta si Distribuita";
    static final int TERMINAT = 9;

    static Thread t1 = new Mircea.Th1();
    static Thread t2 = new Mircea.Th2();
    static Ion ion = new Ion();
    static Thread t3 = ion.stegarescu1;
    static Thread t4 = ion.stegarescu2;

    static void asteaptaToateSarcinile() {
        Thread.currentThread().setPriority(TERMINAT);

        while (t1.getPriority() != TERMINAT || t2.getPriority() != TERMINAT ||
                t3.getPriority() != TERMINAT || t4.getPriority() != TERMINAT) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
        Mircea.genereazaVector();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}