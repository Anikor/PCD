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
        int perLine = 50;
        for (int i = 0; i < mas.length; i++) {
            sb.append(String.format("%3d ", mas[i]));
            if ((i + 1) % perLine == 0 && i != mas.length - 1) {
                sb.append("\n");
            }
        }
        sb.append("\n");
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                String text = "\n\nIrimciuc Egor\nVidrasco Nichita\nГруппа R-241\n";
                for (int i = 0; i < text.length(); i++) {
                    String ch = String.valueOf(text.charAt(i));
                    appendToWindow(ch);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }).start();
    }

    public static void appendToWindow(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textArea.append(text);
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }
        });
    }
}