import java.util.Random;

class StartSum {
    static final int[] data = new int[20];

    static class Worker implements Runnable {
        private long sum = 0;

        @Override
        public void run() {
            for ( int idx = 0; idx < data.length; idx++) {
                if (data[idx] % 2 == 0) {
                    sum += data[idx];
                    System.out.println("(Индекс-> Число) " + idx + " -> " + data[idx]);
                }
            }
        }
        long getSum() {
            return sum;
        }
    }
}
