package divisortester;

public class DivisorTester {
    public static boolean hasDivisor (long number) {
        for (long div = 2; div < number; div++) {
            if (number % div == 0) {
                return true;
            }
        }
        return false;
    }
    public static boolean isPrime(long number) {
        return !hasDivisor(number);
    }
}
