import javax.swing.JOptionPane;
public class Main {
    public static void main(String[] args) {
        int[] mas = new int[100];
        for (int i = 0; i < 100; i++) {
            mas[i] = (int)(Math.random() * 100) + 1;
        }
        MyThread1 t1 = new MyThread1();
        t1.mas = mas;
        t1.start();
        MyThread2 t2 = new MyThread2();
        t2.mas = mas;
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (Exception e) {}
        JOptionPane.showMessageDialog(null, t1.res);
        JOptionPane.showMessageDialog(null, t2.res);
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
class MyThread1 extends Thread {
    int[] mas;
    String res = "";
    public void run() {
        int n = -1;
        int sum = 0;
        res = "Условие 1 (четные позиции с начала):\n";
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                if (n == -1) {
                    n = mas[i];
                } else {
                    int pr = n * mas[i];
                    sum = sum + pr;
                    res = res + "Позиции: " + (i-2) + " и " + i + " -> " + n + " * " + mas[i] + " = " + pr + "\n";
                    n = -1;
                }
            }
        }
        res = res + "\nОбщая сумма произведений: " + sum;
    }
}
class MyThread2 extends Thread {
    int[] mas;
    String res = "";
    public void run() {
        int n = -1;
        int sum = 0;
        res = "Условие 2 (четные позиции с конца):\n";
        for (int i = 99; i >= 0; i--) {
            if (i % 2 == 0) {
                if (n == -1) {
                    n = mas[i];
                } else {
                    int pr = n * mas[i];
                    sum = sum + pr;
                    res = res + "Позиции: " + (i+2) + " и " + i + " -> " + n + " * " + mas[i] + " = " + pr + "\n";
                    n = -1;
                }
            }
        }
        res = res + "\nОбщая сумма произведений: " + sum;
    }
}