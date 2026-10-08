import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;

// Clasa care creeaza tabloul comun
class Tablou {
    public static int[] genereaza() {
        // Tablou de 100 de elemente, valori aleatoare intre 1 si 100
        int[] mas = new int[100];
        for (int i = 0; i < mas.length; i++) {
            mas[i] = (int) (Math.random() * 100) + 1;
        }
        return mas;
    }
}

// Clasa lui Ion: contine firele stegarescu1 si stegarescu2
class Ion {
    private int[] mas;      // tabloul comun, citit de toate cele 4 fire
    private JTextArea zona; // zona de text din fereastra, unde se afiseaza rezultatele
    Thread stegarescu1, stegarescu2;

    public Ion(int[] mas, JTextArea zona) {
        this.mas = mas;
        this.zona = zona;

        // Th1 - Conditia 1: parcurgere de la primul element spre ultimul
        stegarescu1 = new Thread(() -> calcul(0, 1), "stegarescu1");

        // Th2 - Conditia 2: parcurgere de la ultimul element spre primul
        stegarescu2 = new Thread(() -> calcul(mas.length - 1, -1), "stegarescu2");
    }

    // Cauta primul numar par pornind de la pozitia i, mergand cu pasul dat.
    // Returneaza pozitia lui sau -1 daca nu mai exista.
    private int gasestePar(int i, int pas) {
        while (i >= 0 && i < mas.length) {
            if (mas[i] % 2 == 0) {
                return i;
            }
            i = i + pas;
        }
        return -1;
    }

    // Calculul pentru ambele conditii (difera doar start-ul si pasul)
    private void calcul(int start, int pas) {
        ArrayList<String> linii = new ArrayList<>();
        int i = start;

        while (true) {
            int s1 = gasestePar(i, pas);        // primul numar par din pereche
            if (s1 == -1) break;                // nu mai sunt numere pare

            int s2 = gasestePar(s1 + pas, pas); // al doilea numar par din pereche
            if (s2 == -1) break;                // ultima pereche incompleta -> NU se afiseaza

            int s = s1 + s2;
            linii.add("S1=" + s1 + " S2=" + s2 + " S=" + s);

            i = s2 + pas;                       // continuam dupa al doilea numar par
        }

        // Afisam totul odata, ca rezultatele firelor sa nu se amestece
        synchronized (mas) {
            zona.append("--- " + Thread.currentThread().getName() + " ---\n");
            for (String linie : linii) {
                zona.append(linie + "\n");
            }
        }
    }
}

// Clasa lui Mircea: contine firele besleaga1 si besleaga2 (aceeasi sarcina)
class Mircea {
    private int[] mas;      // acelasi tablou comun
    private JTextArea zona;
    Thread besleaga1, besleaga2;

    public Mircea(int[] mas, JTextArea zona) {
        this.mas = mas;
        this.zona = zona;

        // Th1 - Conditia 1: de la primul element spre ultimul
        besleaga1 = new Thread(() -> calcul(0, 1), "besleaga1");

        // Th2 - Conditia 2: de la ultimul element spre primul
        besleaga2 = new Thread(() -> calcul(mas.length - 1, -1), "besleaga2");
    }

    // Cauta primul numar par pornind de la pozitia i, mergand cu pasul dat.
    // Returneaza pozitia lui sau -1 daca nu mai exista.
    private int gasestePar(int i, int pas) {
        while (i >= 0 && i < mas.length) {
            if (mas[i] % 2 == 0) {
                return i;
            }
            i = i + pas;
        }
        return -1;
    }

    private void calcul(int start, int pas) {
        ArrayList<String> linii = new ArrayList<>();
        int i = start;

        while (true) {
            int s1 = gasestePar(i, pas);
            if (s1 == -1) break;

            int s2 = gasestePar(s1 + pas, pas);
            if (s2 == -1) break;

            int s = s1 + s2;
            linii.add("S1=" + s1 + " S2=" + s2 + " S=" + s);

            i = s2 + pas;
        }

        // Aceeasi cheie de sincronizare ,
        // deci cele 4 fire nu isi amesteca rezultatele
        synchronized (mas) {
            zona.append("--- " + Thread.currentThread().getName() + " ---\n");
            for (String linie : linii) {
                zona.append(linie + "\n");
            }
        }
    }
}

public class Laborator1 {
    public static void main(String[] args) throws InterruptedException {
        // Fereastra
        JFrame fereastra = new JFrame("Laborator 1");
        JTextArea zona = new JTextArea();
        zona.setEditable(false);
        zona.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        fereastra.add(new JScrollPane(zona));
        fereastra.setSize(900, 700);
        fereastra.setLocationRelativeTo(null);
        fereastra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fereastra.setVisible(true);

        // Tabloul este apelat aici
        int[] mas = Tablou.genereaza();

        // Afisam tabloul pe randuri de cate 20 de elemente:
        // deasupra indicii, dedesubt valorile
        for (int start = 0; start < mas.length; start += 20) {
            String randIndici = "Indici:  ";
            String randValori = "Valori:  ";
            for (int i = start; i < start + 20 && i < mas.length; i++) {
                randIndici = randIndici + String.format("%4d", i);
                randValori = randValori + String.format("%4d", mas[i]);
            }
            zona.append(randIndici + "\n");
            zona.append(randValori + "\n\n");
        }

        //  ACELASI tablou
        Ion lucrareIon = new Ion(mas, zona);
        Mircea lucrareMircea = new Mircea(mas, zona);

        lucrareIon.stegarescu1.start();
        lucrareIon.stegarescu2.start();
        lucrareMircea.besleaga1.start();
        lucrareMircea.besleaga2.start();

        // Thread-ul principal asteapta terminarea tuturor celor 4 fire
        lucrareIon.stegarescu1.join();
        lucrareIon.stegarescu2.join();
        lucrareMircea.besleaga1.join();
        lucrareMircea.besleaga2.join();


        String info = "\nLucrare de laborator 1 efectuata de: Stegarescu Ion si Besleaga Mircea, grupa R-241";
        for (int i = 0; i < info.length(); i++) {
            zona.append(String.valueOf(info.charAt(i)));
            Thread.sleep(100);
        }
        zona.append("\n");
    }
}