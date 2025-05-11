package fr.anthonus;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.time.Instant;

public class Main {
    private static int size = 2;
    private static int[] numbers = new int[size];
    private static BigInteger globalCpt = BigInteger.ZERO;
    private static BigInteger currentCpt = BigInteger.ZERO;
    private static Instant globalStartTime = Instant.now();
    private static Instant startTime = Instant.now();

    public static void main(String[] args) {
        File file = new File("output.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        while (true) {
            generate();

            verifyAndChange();
        }
    }

    private static void generate() {
        for (int i = 0; i < size; i++) {
            numbers[i] = (int) (Math.random() * 10);
        }
        currentCpt = currentCpt.add(BigInteger.ONE);
        globalCpt = globalCpt.add(BigInteger.ONE);
    }

    private static void verifyAndChange() {
        for (int i = 0; i < size; i += 2) {
            if (numbers[i] != 6 || numbers[i + 1] != 9)
                return;
        }

        print();
        startTime = Instant.now();
        size += 2;
        numbers = new int[size];
        currentCpt = BigInteger.ZERO;
    }

    private static void print() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt", true))){
            StringBuilder sb = new StringBuilder();
            sb.append("Taille : ").append(size).append("\n");

            sb.append("Nombre de boucles : ").append(currentCpt).append("\n");
            sb.append("Nombre de boucles depuis le début : ").append(globalCpt).append("\n");

            long elapsedTime = Instant.now().toEpochMilli() - startTime.toEpochMilli();
            long totalElapsedTime = Instant.now().toEpochMilli() - globalStartTime.toEpochMilli();
            sb.append("Temps écoulé : ").append(formatTime(elapsedTime)).append("\n");
            sb.append("Temps écoulé depuis le début : ").append(formatTime(totalElapsedTime)).append("\n");

            sb.append("Nombre : ");
            for (int i = 0; i < size; i++) sb.append(numbers[i]);

            sb.append("\n\n");

            System.out.println(sb);
            bw.write(sb.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static String formatTime(long millis) {
        if (millis < 1000) {
            return millis + " ms";
        } else if (millis < 3600000) {
            return (millis / 1000) + " s";
        } else {
            return (millis / 3600000) + " h";
        }
    }
}