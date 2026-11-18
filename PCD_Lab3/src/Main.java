public class Main {
    static final String LAST_NAME  = "Gordei Balan";
    static final String FIRST_NAME = "Serhgei Alecsandr";
    static final String GROUP      = "R-241";
    static final String DISCIPLINE = "PCD";

    public static void main(String[] args) {
        PairOne pairOne = new PairOne(LAST_NAME, FIRST_NAME);
        PairTwo pairTwo = new PairTwo(GROUP, DISCIPLINE);

        pairOne.setPairTwo(pairTwo);
        pairTwo.setPairOne(pairOne);

        pairOne.start();
        pairTwo.start();
    }

    static void printText(String info) throws InterruptedException {
        for (char c : info.toCharArray()) {
            System.out.print(c);
            Thread.sleep(100L);
        }
        System.out.println();
    }
}