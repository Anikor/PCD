class EThread extends Thread {
    int[] mas;
    String res = "";
    public void run() {
        int n = -1;
        int sum = 0;
        res = "Условие 1, поток " + this + " (четные позиции с начала):\n";
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