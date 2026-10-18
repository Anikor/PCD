import java.util.Random;

public class Lab1 {
    static final int[] data = new int[20];

    public static void main(String[] args) throws InterruptedException {
        Random r = new Random();
    for (int i = 0; i < data.length; i++) data[i] = r.nextInt(100);
    System.out.println("Массив: " + java.util.Arrays.toString(data));

    EndSum.Worker w = new EndSum.Worker();
    Thread t = new Thread(w);
    t.start();
    t.join();

    System.out.println("Сумма чётных (с конца): " + w.getSum());
}
}

