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
}

public class binary_search {

    public static int binarySearch(List<Data> list, long targetKey) {
        int left = 0, right = list.size() - 1;
        while (left <= right) {
            int mid = (left + right) >>> 1;  // Avoid overflow
            long midKey = list.get(mid).key;
            if (midKey == targetKey) {
                return mid;
            } else if (midKey < targetKey) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

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

    public static void writeSearchTimes(String filename, double bestTime, double avgTime, double worstTime) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(String.format("Best case running time: %.6e seconds%n", bestTime));
            writer.write(String.format("Average case running time: %.6e seconds%n", avgTime));
            writer.write(String.format("Worst case running time: %.6e seconds%n", worstTime));
        }
    }

    public static void main(String[] args) {
        String inputFile = "merge_sort_1000000.csv";
        String outputFile = "binary_search_1000000.txt";

        try {
            List<Data> data = readCSV(inputFile);
            int datasetSize = data.size();
            if (datasetSize == 0) {
                System.err.println("Dataset is empty!");
                return;
            }

            int n = 100000;  // Number of searches to perform (set as desired)

            // Prepare targets for each case
            long bestTarget = data.get(datasetSize / 2).key;
            long avgTarget = data.get(Math.min(datasetSize / 2 + 1, datasetSize - 1)).key;
            long worstTarget = -1L;  // Non-existent element

            // Best case timing
            long start = System.nanoTime();
            for (int i = 0; i < n; i++) {
                binarySearch(data, bestTarget);
            }
            long end = System.nanoTime();
            double bestTime = (end - start) / 1e9 / n;  // average time in seconds

            // Average case timing
            start = System.nanoTime();
            for (int i = 0; i < n; i++) {
                binarySearch(data, avgTarget);
            }
            end = System.nanoTime();
            double avgTime = (end - start) / 1e9 / n;

            // Worst case timing
            start = System.nanoTime();
            for (int i = 0; i < n; i++) {
                binarySearch(data, worstTarget);
            }
            end = System.nanoTime();
            double worstTime = (end - start) / 1e9 / n;

            // Write results to output file
            writeSearchTimes(outputFile, bestTime, avgTime, worstTime);

            // Print the message to the command prompt
            System.out.println("Results stored in binary_search_1000000.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
