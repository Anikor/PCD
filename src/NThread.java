class NThread extends Thread {
    int[] mas;
    String res = "";
    public void run() {
        int n = -1;
        int sum = 0;
        res = "Sarcina 2, thread " + this + " (citirea pozitiei de la sfarsit)\n";
        for (int i = 99; i >= 0; i--) {
            if (i % 2 == 0) {
                if (n == -1) {
                    n = mas[i];
                } else {
                    int pr = n * mas[i];
                    sum = sum + pr;
                    res = res + "Pozitii: " + (i+2) + " и " + i + " -> " + n + " * " + mas[i] + " = " + pr + "\n";
                    n = -1;
                }
            }
        }
        res = res + "\nSuma totala a inmultirilor: " + sum;
    }
}