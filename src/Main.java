import java.util.Random;
public class Main {
    public static void main(String[] args){
        int[] mas = new int[100];
        Random randomnumber = new Random();
        for(int i = 0; i < mas.length; i++){
            mas[i] = randomnumber.nextInt(100) + 1;
        }

    }

}

class Helper{

    public static boolean isodd(int n){
     return n % 2 != 0;
    }

    public void printArray(int[] mas){
        for (int i = 0; i < mas.length; i++){
            System.out.print(mas[i] + " ");
            System.out.println();
        }
    }


}

class Student1 extends Thread{
    class Th1 extends Thread{

    }

    class Th2 extends Thread{

    }



    private int[] Alexarray;
    public void SumOddpairsFromStart(int[] mas){
        for (int i = 0; i < mas.length; i++) {
        }
    }
}

