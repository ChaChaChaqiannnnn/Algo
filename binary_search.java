import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Data {
    long key;
    String value;

    Data(long key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key + "," + value;
    }
}

public class binary_search {

    // Binary search implementation
    public static int binarySearch(List<Data> arr, long targetKey) {
        int left = 0;
        int right = arr.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr.get(mid).key == targetKey) {
                return mid;  // Target found
            } else if (arr.get(mid).key < targetKey) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;  // Target not found
    }

    // Read the CSV file and return a list of Data objects
    public static List<Data> readCSV(String filename) throws IOException {
        List<Data> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    long key = Long.parseLong(parts[0].trim());
                    String value = parts[1].trim();
                    dataList.add(new Data(key, value));
                }
            }
        }
        return dataList;
    }

    // Write the running times to a file
    public static void writeSearchTimes(String filename, double bestTime, double avgTime, double worstTime) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Best case running time: " + bestTime + " seconds\n");
            writer.write("Average case running time: " + avgTime + " seconds\n");
            writer.write("Worst case running time: " + worstTime + " seconds\n");
        }
    }

    public static void main(String[] args) {
        String inputFile = "merge_sort_1000000.csv";  // Input file with sorted data
        String outputFile = "binary_search_1000000.txt";  // Output file for running times

        try {
            System.out.println("Reading sorted data from input file...");
            List<Data> data = readCSV(inputFile);

            int n = data.size();  // Size of dataset

            // Best case: Search for the middle element in each iteration
            System.out.println("Testing best case (middle element)...");
            long startTime = System.nanoTime();
            for (int i = 0; i < n; i++) {
                long targetBest = data.get(n / 2).key;  // Middle element
                binarySearch(data, targetBest);
            }
            long endTime = System.nanoTime();
            double bestTime = (endTime - startTime) / 1e9 / n;  // Average time per search in best case

            // Average case: Search for a random element near the middle
            System.out.println("Testing average case (random element near middle)...");
            startTime = System.nanoTime();
            for (int i = 0; i < n; i++) {
                long targetAvg = data.get(Math.min(n / 2 + 1, n - 1)).key;  // Safe index near middle
                binarySearch(data, targetAvg);
            }
            endTime = System.nanoTime();
            double avgTime = (endTime - startTime) / 1e9 / n;  // Average time per search in average case

            // Worst case: Search for an element that doesn't exist
            System.out.println("Testing worst case (non-existent element)...");
            startTime = System.nanoTime();
            for (int i = 0; i < n; i++) {
                long targetWorst = -1;  // Element that doesn't exist
                binarySearch(data, targetWorst);
            }
            endTime = System.nanoTime();
            double worstTime = (endTime - startTime) / 1e9 / n;  // Average time per search in worst case

            System.out.println("Writing results to output file...");
            writeSearchTimes(outputFile, bestTime, avgTime, worstTime);

            System.out.println("Done.");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
