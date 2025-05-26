import java.io.*;
import java.util.*;

public class binary_search_step {

    // Data class holding integer + string pair
    static class Data {
        int key;
        String value;
        Data(int k, String v) { key = k; value = v; }
    }

    // Perform binary search on Data array and write the comparison steps
    public static int binarySearch(Data[] arr, int target, BufferedWriter writer) throws IOException {
        int left = 0, right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // Write current comparison: row: key/string
            writer.write(String.format("%d: %d/%s%n", mid + 1, arr[mid].key, arr[mid].value));

            if (arr[mid].key == target) {
                return mid; // Found, return index
            } else if (arr[mid].key < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        // Not found
        writer.write("-1\n");
        return -1;
    }

    // Read the dataset (key/value pairs) from the provided CSV file
    public static Data[] readData(String filename) throws IOException {
        List<Data> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);  // Split into key and value
                if (parts.length == 2) {
                    try {
                        int key = Integer.parseInt(parts[0].trim());
                        String val = parts[1].trim();
                        data.add(new Data(key, val));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping malformed integer in line: " + line);
                    }
                }
            }
        }
        return data.toArray(new Data[0]);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java binary_search_step <sorted_dataset_file> <target_integer>");
            return;
        }

        String sortedFile = args[0]; // Expect the file name as an argument
        int target;

        try {
            target = Integer.parseInt(args[1]); // Target value for binary search
        } catch (NumberFormatException e) {
            System.err.println("Target must be an integer.");
            return;
        }

        try {
            // Read data from the CSV file (e.g., quick_sort_1000.csv)
            Data[] arr = readData(sortedFile);
            if (arr.length == 0) {
                System.err.println("Dataset is empty.");
                return;
            }

            // Define output file name based on target value
            String outFile = String.format("binary_search_step_%d.txt", target);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
                int foundIndex = binarySearch(arr, target, writer);
                if (foundIndex >= 0) {
                    // Found target, index will be logged
                }
            }
            System.out.println("Binary search steps saved to " + outFile);
        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        }
    }
}
