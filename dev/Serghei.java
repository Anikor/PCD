public class Serghei extends Thread {
    private final int[] mas;
    private final boolean fromStart;

    public Serghei(int[] mas, boolean fromStart, String name) {
        super(name);
        this.mas = mas;
        this.fromStart = fromStart;
    }

    @Override
    public void run() {
        if (fromStart) {
            work(0, 1);
        } else {
            work(mas.length - 1, -1);
        }
    }

    private void work(int startIdx, int step) {
        String name = getName();
        int first = -1;

        for (int i = startIdx; i >= 0 && i < mas.length; i += step) {
            if (mas[i] % 2 == 0) {
                if (first == -1) {
                    first = i;
                } else {
                    int pairSum = mas[first] + mas[i];
                    System.out.println(name + ": " + mas[first] + " + " + mas[i]
                            + " = " + pairSum + " (ИНДЕКСЫ " + first + ", " + i + ")");
                    first = -1;

                }
            }
        }

        if (first != -1) {
            System.out.println(name + ": чётное " + mas[first]
                    + " (индекс " + first + ") осталось без пары");
        }
    }


}