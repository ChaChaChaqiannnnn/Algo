import java.io.*;
import java.util.*;

public class quick_sort_step {

    private static BufferedWriter stepWriter;

    // Data class to hold integer + string pair
    static class Data {
        int key;
        String val;
        Data(int k, String v) { key = k; val = v; }
    }

    public static void quickSort(Data[] arr) throws IOException {
        quickSortHelper(arr, 0, arr.length - 1);
    }

    private static void quickSortHelper(Data[] arr, int low, int high) throws IOException {
        if (low < high) {
            int pi = partition(arr, low, high);

            stepWriter.write(String.format("Pivot placed at index %d with value %d%n", pi, arr[pi].key));
            stepWriter.write("Current array: ");
            for (Data d : arr) {
                stepWriter.write(d.key + " ");
            }
            stepWriter.write("\n\n");

            quickSortHelper(arr, low, pi - 1);
            quickSortHelper(arr, pi + 1, high);
        }
    }

    private static int partition(Data[] arr, int low, int high) throws IOException {
        int pivot = arr[high].key;
        stepWriter.write(String.format("Partitioning with pivot %d at index %d%n", pivot, high));
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].key <= pivot) {
                i++;
                swap(arr, i, j);
                stepWriter.write(String.format("Swapped elements at indices %d and %d: ", i, j));
                for (Data d : arr) {
                    stepWriter.write(d.key + " ");
                }
                stepWriter.write("\n");
            }
        }

        swap(arr, i + 1, high);
        stepWriter.write(String.format("Swapped pivot with element at index %d: ", i + 1));
        for (Data d : arr) {
            stepWriter.write(d.key + " ");
        }
        stepWriter.write("\n");

        return i + 1;
    }

    private static void swap(Data[] arr, int i, int j) {
        if (i != j) {
            Data temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java quick_sort_step dataset_sample_1000.csv <start_row> <end_row>");
            return;
        }

        String csvFile = args[0];
        int startRow, endRow;
        try {
            startRow = Integer.parseInt(args[1]);
            endRow = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.err.println("Start row and end row must be integers.");
            return;
        }

        List<Data> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            int row = 1;
            while ((line = br.readLine()) != null) {
                if (row >= startRow && row <= endRow) {
                    String[] parts = line.split(",", 2);
                    if (parts.length == 2) {
                        try {
                            int key = Integer.parseInt(parts[0].trim());
                            String val = parts[1].trim();
                            dataList.add(new Data(key, val));
                        } catch (NumberFormatException ex) {
                            System.err.println("Skipping malformed integer at row " + row);
                        }
                    } else {
                        System.err.println("Skipping malformed line at row " + row);
                    }
                }
                if (row > endRow) break;
                row++;
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            return;
        }

        Data[] arr = dataList.toArray(new Data[0]);

        String outFile = String.format("quick_sort_step_%d_%d.txt", startRow, endRow);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
            stepWriter = writer;
            quickSort(arr);
        } catch (IOException e) {
            System.err.println("Error writing step log: " + e.getMessage());
            return;
        }

        System.out.println("Quick sort steps written to " + outFile);

        // Print sorted keys on console
        System.out.print("Sorted array keys: ");
        for (Data d : arr) System.out.print(d.key + " ");
        System.out.println();
    }
}
