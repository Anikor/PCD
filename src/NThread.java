class NThread extends Thread {
    public String toString() {
        return getName();
    }
    int[] mas;

    public void run() {
        int n = -1;
        int sum = 0;

        Main.appendToWindow("[СТАРТ] Thread " + this + " (citirea de la sfarsit)\n");

        for (int i = 99; i >= 0; i--) {
            if (i % 2 == 0) {
                if (n == -1) {
                    n = mas[i];
                } else {
                    int pr = n * mas[i];
                    sum = sum + pr;
                    Main.appendToWindow("Thread " + this + " | Pozitii: " + (i + 2) + " и " + i + " -> " + n + " * " + mas[i] + " = " + pr + "\n");
                    n = -1;
                    try {
                        Thread.sleep((long)(Math.random() * 20));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        Main.appendToWindow(">>> [ИТОГ] Thread " + this + " Suma totala: " + sum + "\n");
    }
}