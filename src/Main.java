import java.util.Random;
public class Main {
    public static void main(String[] args){
        int[] array = new int[100];
        Random randomnumber = new Random();
        for(int i = 0; i < array.length; i++){
            array[i] = randomnumber.nextInt(100);
        }

    }

}

class Helper{
    public boolean isodd(int n){
     return n % 2 != 0;
    }
}

class FromStart extends Thread{

}

/*
public class Main {

    public static void main(String[] args) {
        // общий массив для обоих вариантов
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

        Helper.printArray(array);

        // Вариант 1: с первого элемента до конца
        SumFromStart.run(array);

        // Вариант 2: с последнего элемента к началу
        SumFromEnd.run(array);
    }
}

// ---------- ОБЩИЕ ФУНКЦИИ (трогаем только если надо обоим) ----------
class Helper {

    // проверка на нечетность (для отрицательных тоже работает)
    static boolean isOdd(int number) {
        return number % 2 != 0;
    }

    static void printArray(int[] arr) {
        System.out.print("Массив: ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    static void printPairSum(int a, int b) {
        System.out.println(a + " + " + b + " = " + (a + b));
    }

    // если нечетных нечетное количество, последний остается без пары
    static void printSingle(int a) {
        System.out.println(a + " - без пары");
    }
}

// ---------- ВАРИАНТ 1: с первого элемента до конца (мой) ----------
class SumFromStart {

    static void run(int[] arr) {
        System.out.println();
        System.out.println("Суммы нечетных по два, с первого элемента:");

        int first = 0;           // первое число из пары
        boolean hasFirst = false; // нашли ли уже первое число

        for (int i = 0; i < arr.length; i++) {
            if (Helper.isOdd(arr[i])) {
                if (!hasFirst) {
                    first = arr[i];
                    hasFirst = true;
                } else {
                    Helper.printPairSum(first, arr[i]);
                    hasFirst = false;
                }
            }
        }

        // если остался один без пары
        if (hasFirst) {
            Helper.printSingle(first);
        }
    }
}

// ---------- ВАРИАНТ 2: с последнего элемента (напарник) ----------
class SumFromEnd {

    static void run(int[] arr) {
        System.out.println();
        System.out.println("Суммы нечетных по два, с последнего элемента:");

        int first = 0;
        boolean hasFirst = false;

        for (int i = arr.length - 1; i >= 0; i--) {
            if (Helper.isOdd(arr[i])) {
                if (!hasFirst) {
                    first = arr[i];
                    hasFirst = true;
                } else {
                    Helper.printPairSum(first, arr[i]);
                    hasFirst = false;
                }
            }
        }

        if (hasFirst) {
            Helper.printSingle(first);
        }
    }
}*/

