import javax.swing.*;
import java.awt.*;

public class Main {
    private static JTextArea textArea;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Результаты работы потоков");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);

        frame.setVisible(true);

        int[] mas = new int[100];
        for (int i = 0; i < 100; i++) {
            mas[i] = (int)(Math.random() * 100) + 1;
        }

        appendToWindow("Исходный массив:\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mas.length; i++) {
            sb.append(mas[i]).append("\t");
            if (i == 49) {
                sb.append("\n");
            }
        }
        sb.append("\n\n\n\n");
        appendToWindow(sb.toString());

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

        new Thread(() -> {
            String text = "\nIrimciuc Egor\nVidrasco Nichita\nГруппа R-241\n";
            for (int i = 0; i < text.length(); i++) {
                String ch = String.valueOf(text.charAt(i));
                appendToWindow(ch);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    public static void appendToWindow(String text) {
        SwingUtilities.invokeLater(() -> {
            textArea.append(text);
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }
}