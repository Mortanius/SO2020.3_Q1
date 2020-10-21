package main;

import divisortester.MultiThreadedDivisorTester;
import org.apache.commons.cli.*;
import divisortester.DivisorTester;

public class Main {
    private static Options argsOptions() {
        Options options = new Options();
        Option number = new Option("n", "numero", true, "Numero para verificar a primalidade");
        number.setRequired(true);
        options.addOption(number);
        Option threadsNumber = new Option("t", "threads", true, "Numero de threads");
        threadsNumber.setRequired(false);
        options.addOption(threadsNumber);
        return options;
    }

    public static void main(String[] args) {
        Options options = argsOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            HelpFormatter helpF = new HelpFormatter();
            System.err.println(e.getMessage());
            helpF.printHelp("NumeroPrimo [opcoes]", options);
            System.exit(1);
        }
        long n = 1;
        int t = 1;
        String opt = "", val= "";
        try {
            opt = "n";
            val = cmd.getOptionValue(opt);
            n = Long.parseUnsignedLong(val);
            opt = "t";
            val = cmd.getOptionValue(opt);
            if (val != null) {
                t = Integer.parseUnsignedInt(val);
            }
        } catch (NumberFormatException e) {
            System.err.println("Valor " + val + " para " + opt + " invalido");
        }
        System.out.println("Numero: " + n + " Threads: " + t);
        boolean isPrimeNumber;
        long start, end;
        if (t == 1) {
            start = System.currentTimeMillis();
            isPrimeNumber = DivisorTester.isPrime(n);
            end = System.currentTimeMillis();
            System.out.printf("Numero %d " + (isPrimeNumber ? "" : "nao ") + "e primo\n", n);
            System.out.println("Tempo decorrido (Single-Threaded): " + (end - start));
        } else {
        //if (t > 1) {
            MultiThreadedDivisorTester tester = new MultiThreadedDivisorTester(n, t);
            try {
                start = System.currentTimeMillis();
                isPrimeNumber = tester.isPrime();
                end = System.currentTimeMillis();
                System.out.printf("Numero %d " + (isPrimeNumber ? "" : "nao ") + "e primo\n", n);
                System.out.println("Tempo decorrido (Multi-Threaded): " + (end - start));
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
