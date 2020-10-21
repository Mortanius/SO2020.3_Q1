package divisortester;

public class MultiThreadedDivisorTester {
    private final long number;
    private final int threads;
    private Boolean divisorFound = false;

    public MultiThreadedDivisorTester(long number, int threads) {
        this.number = number;
        if (threads > number - 2) {
            this.threads = (int) number - 2;
        } else {
            this.threads = threads;
        }
        System.out.println("MultiThreadedSequenceDivisorTester:: Using " + this.threads + " threads");
    }
    // Invocado pela classe Worker com sua threadId
    // Pode encerrar o loop com antecedencia quando o divisor (this.divisorFound) é encontrado externamente
    protected boolean hasDivisorSubSequence(int threadId) {
        //System.out.printf("(Thread #%d) Searching for divisor start: %d step: %d\n", threadId, threadId + 2, this.threads);
        for (long div = (long) threadId + 2; !this.divisorFound && div < number; div += this.threads) {
            if (number % div == 0) {
                return true;
            }
        }
        return false;
    }
    private static class Worker implements Runnable {
        private final MultiThreadedDivisorTester coordInstance;
        int threadId;
        public Worker(MultiThreadedDivisorTester coordInstance, int threadId) {
            this.coordInstance = coordInstance;
            this.threadId = threadId;
        }
        @Override
        public void run() {
            boolean subResult = coordInstance.hasDivisorSubSequence(threadId);
            if (subResult && coordInstance.divisorFound) {
                //System.out.printf("(Thread #%d) divisor found\n", threadId);
                coordInstance.divisorFound = true;
            } //else
                //System.out.printf("(Thread #%d) NO divisor found\n", threadId);
        }
    }
    public boolean hasDivisor() throws InterruptedException {
        // Somente é alterado quando um divisor é encontrado
        divisorFound = false;
        Thread[] threadArr = new Thread[this.threads];
        for (int i = 0; i < threadArr.length; i++) {
            Thread t = new Thread(new MultiThreadedDivisorTester.Worker(this, i));
            t.start();
            threadArr[i] = t;
        }
        for (Thread t : threadArr) {
            t.join();
        }
        return false;
    }
    public boolean isPrime() throws InterruptedException {
        return !hasDivisor();
    }
}
