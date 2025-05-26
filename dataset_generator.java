import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class dataset_generator {

    private static String randomString(Random rand) {
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            sb.append((char) ('a' + rand.nextInt(26)));
        }
        return sb.toString();
    }

    public static void generateDataset(int size, int maxInt) throws IOException {
        if (size > maxInt) {
            throw new IllegalArgumentException("Size cannot exceed max integer range for uniqueness.");
        }

        String filename = "dataset_" + size + ".csv";

        System.out.println("Generating " + size + " unique integers up to " + maxInt);
        System.out.println("Output file: " + filename);

        List<Integer> numbers = new ArrayList<>(size);

        for (int i = 1; i <= maxInt && numbers.size() < size; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers, new Random());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            Random rand = new Random();
            for (int num : numbers) {
                String str = randomString(rand);
                writer.write(num + "," + str);
                writer.newLine();
            }
        }

        System.out.println("Dataset generated and saved to " + filename);
    }

    public static void main(String[] args) {
        int size = 1_000_000;        // Change as needed
        int maxInt = 1_000_000_000;  // Integers up to 1 billion

        try {
            long start = System.currentTimeMillis();

            generateDataset(size, maxInt);

            long end = System.currentTimeMillis();
            System.out.println("Generation time: " + ((end - start) / 1000.0) + " seconds");
        } catch (IOException e) {
            System.err.println("Error generating dataset: " + e.getMessage());
        }
    }
}