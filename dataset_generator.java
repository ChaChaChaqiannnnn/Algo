import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class dataset_generator {
    private static Random random = new Random();

    private static String generateRandomString(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static void generateDataset(int size, String filename) {
        System.out.println("Generating dataset of size " + size + "...");
        long startTime = System.currentTimeMillis();

        // Generate unique random numbers up to 1 billion
        Set<Long> numbers = new HashSet<>();
        while (numbers.size() < size) {
            // Generate random 32-bit integers (up to 2^31 - 1)
            long num = random.nextInt(1000000000) + 1;
            numbers.add(num);
        }

        // Convert set to list and shuffle for random order
        List<Long> numberList = new ArrayList<>(numbers);
        Collections.shuffle(numberList);

        // Write to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Long num : numberList) {
                String val = generateRandomString(6);
                writer.write(num + "," + val + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            return;
        }

        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime) / 1000.0;
        System.out.printf("Dataset generation completed in %.2f seconds%n", duration);
        System.out.println("Generated " + size + " unique records");
        System.out.println("Output file: " + filename);
    }

    public static void verifyDataset(String filename) {
        System.out.println("\nVerifying dataset...");
        Set<Long> numbers = new HashSet<>();
        int duplicates = 0;
        int outOfRange = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                long num = Long.parseLong(line.split(",")[0]);
                if (numbers.contains(num)) {
                    duplicates++;
                }
                if (num < 1 || num > 1000000000) {
                    outOfRange++;
                }
                numbers.add(num);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        System.out.println("Total unique numbers: " + numbers.size());
        System.out.println("Duplicates found: " + duplicates);
        System.out.println("Numbers out of range: " + outOfRange);
        System.out.println("All numbers are unique: " + (duplicates == 0));
        System.out.println("All numbers are in range: " + (outOfRange == 0));
    }

    public static void main(String[] args) {
        // Generate a dataset of 1 million records
        int size = 1000000;
        String filename = "dataset_1000000.csv";

        generateDataset(size, filename);
        verifyDataset(filename);
    }
}