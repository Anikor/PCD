import javax.swing.JOptionPane;
public class Main {
    public static void main(String[] args) {
        int[] mas = new int[100];
        for (int i = 0; i < 100; i++) {
            mas[i] = (int)(Math.random() * 100) + 1;
        }

        EThread e1 = new EThread();
        EThread e2 = new EThread();
        e1.mas = mas;
        e2.mas = mas;

        NThread n1 = new NThread();
        NThread n2 = new NThread();
        n1.mas = mas;
        n2.mas = mas;

        e1.start();
        e2.start();
        n1.start();
        n2.start();

        String text = "Irimciuc Egor\nVidrasco Nichita\nГруппа R-241";

        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            try {
                Thread.sleep(100);
            } catch (Exception e) {}
        }
        System.out.println();
    }
}