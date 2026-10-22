
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.Font;

class Tablou {

    public static int[] genereaza() {
        int[] mas = new int[100];

        for (int i = 0; i < mas.length; i++) {
            mas[i] = (int) (Math.random() * 100) + 1;
        }

        return mas;
    }
}


class Ion implements Runnable {

    private int[] mas;
    private JTextArea zona;
    private int start;
    private int pas;

    public Ion(int[] mas, JTextArea zona, int start, int pas) {
        this.mas = mas;
        this.zona = zona;
        this.start = start;
        this.pas = pas;
    }

    @Override
    public void run() {
        calcul(start, pas);
    }

    // Cauta pozitia urmatorului numar par
    private int gasestePar(int i, int pas) {

        while (i >= 0 && i < mas.length) {
            if (mas[i] % 2 == 0) {
                return i;
            }
            i += pas;
        }

        return -1;
    }

    // Calculeaza suma INDICILOR numerelor pare
    private void calcul(int start, int pas) {

        int i = start;

        while (true) {

            int s1 = gasestePar(i, pas);
            if (s1 == -1) break;

            int s2 = gasestePar(s1 + pas, pas);
            if (s2 == -1) break;

            int s3 = gasestePar(s2 + pas, pas);
            if (s3 == -1) break;

            int s4 = gasestePar(s3 + pas, pas);
            if (s4 == -1) break;

            // Suma indicilor
            int s11 = s1 + s2;
            int s22 = s3 + s4;
            int Stotal = s11 + s22;

            String linie = Thread.currentThread().getName()
                    + ": S11=" + s1 + "+" + s2 + "=" + s11
                    + " | S22=" + s3 + "+" + s4 + "=" + s22
                    + " | Stotal=" + s11 + "+" + s22
                    + "=" + Stotal + "\n";

            SwingUtilities.invokeLater(
                    () -> zona.append(linie));

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Eroare");
            }

            i = s4 + pas;
        }
    }
}



class Mircea extends Thread {

    private int[] mas;
    private JTextArea zona;
    private int pozitie;
    private int pas;

    public Mircea(int[] mas, JTextArea zona,
                  int pozitie, int pas, String nume) {

        super(nume);
        this.mas = mas;
        this.zona = zona;
        this.pozitie = pozitie;
        this.pas = pas;
    }

    @Override
    public void run() {

        // Mircea foloseste un tablou de 4 indici
        int[] indici = new int[4];

        while (pozitie >= 0 && pozitie < mas.length) {

            int gasite = 0;

            // Cauta 4 pozitii cu numere pare
            while (pozitie >= 0
                    && pozitie < mas.length
                    && gasite < 4) {

                if (mas[pozitie] % 2 == 0) {
                    indici[gasite] = pozitie;
                    gasite++;
                }

                pozitie += pas;
            }

            // Nu calculeaza grupuri incomplete
            if (gasite < 4) {
                break;
            }

            // Suma indicilor in perechi
            int s11 = indici[0] + indici[1];
            int s22 = indici[2] + indici[3];
            int Stotal = s11 + s22;

            String linie = getName()
                    + ": s11=" + indici[0] + "+"
                    + indici[1] + "=" + s11
                    + " | s22=" + indici[2] + "+"
                    + indici[3] + "=" + s22
                    + " | Stotal=" + s11 + "+" + s22
                    + "=" + Stotal + "\n";

            SwingUtilities.invokeLater(
                    () -> zona.append(linie));

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                interrupt();
                break;
            }
        }
    }
}


// CLASA 4 - MAIN
public class Laborator1 {

    public static void main(String[] args)
            throws InterruptedException {

        // Crearea ferestrei
        JFrame fereastra = new JFrame("Laborator 1");
        JTextArea zona = new JTextArea();

        zona.setEditable(false);
        zona.setFont(new Font(
                Font.MONOSPACED, Font.PLAIN, 14));

        fereastra.add(new JScrollPane(zona));
        fereastra.setSize(1100, 700);
        fereastra.setLocationRelativeTo(null);
        fereastra.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);
        fereastra.setVisible(true);

        // Generarea tabloului comun
        int[] mas = Tablou.genereaza();

        // Afisarea indicilor si valorilor
        for (int start = 0; start < mas.length; start += 20) {

            String randIndici = "Indici:  ";
            String randValori = "Valori:  ";

            for (int i = start;
                 i < start + 20 && i < mas.length; i++) {

                randIndici += String.format("%4d", i);
                randValori += String.format("%4d", mas[i]);
            }

            zona.append(randIndici + "\n");
            zona.append(randValori + "\n\n");
        }

        // Crearea celor doua sarcini Ion
        Ion ion1 = new Ion(mas, zona, 0, 1);
        Ion ion2 = new Ion(mas, zona, mas.length - 1, -1);

        // Crearea firelor Ion prin Runnable
        Thread stegarescu1 = new Thread(ion1, "stegarescu1");
        Thread stegarescu2 = new Thread(ion2, "stegarescu2");

        // Crearea celor doua fire Mircea prin Thread
        Mircea besleaga1 = new Mircea(
                mas, zona, 0, 1, "besleaga1");

        Mircea besleaga2 = new Mircea(
                mas, zona, mas.length - 1, -1, "besleaga2");

        zona.append("\n===== CALCULUL SUMELOR INDICILOR =====\n\n");

        // Pornirea celor 4 fire in paralel
        stegarescu1.start();
        stegarescu2.start();

        besleaga1.start();
        besleaga2.start();


        String info = "\nLucrare de laborator 1 efectuata de: "
                + "Stegarescu Ion si Besleaga Mircea, "
                + "grupa R-241";

        for (int i = 0; i < info.length(); i++) {

            String litera = String.valueOf(info.charAt(i));

            SwingUtilities.invokeLater(
                    () -> zona.append(litera));

            Thread.sleep(100);
        }

        SwingUtilities.invokeLater(
                () -> zona.append("\n"));
    }
}
