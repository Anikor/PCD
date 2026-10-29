class EThread extends Thread {
    public String toString() {
        return getName();
    }
    int[] mas;

    public void run() {
        int n = -1;
        int sum = 0;

        Main.appendToWindow("[СТАРТ] Поток " + this + " (четные позиции с начала)\n");

        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                if (n == -1) {
                    n = mas[i];
                } else {
                    int pr = n * mas[i];
                    sum = sum + pr;
                    Main.appendToWindow("Поток " + this + " | Позиции: " + (i - 2) + " и " + i + " -> " + n + " * " + mas[i] + " = " + pr + "\n");
                    n = -1;
                    try {
                        Thread.sleep((long)(Math.random() * 20));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        Main.appendToWindow(">>> [ИТОГ] Поток " + this + " Сумма: " + sum + "\n");
    }
}