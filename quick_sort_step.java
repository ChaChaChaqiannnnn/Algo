import java.io.*;
import java.util.*;

public class quick_sort_step {

    private static BufferedWriter stepWriter;

    // Data class holding integer + string pair
    static class Data {
        int key;
        String val;
        Data(int k, String v) {
            key = k;
            val = v;
        }
    }

    // Format array as [key/val, key/val, ...]
    private static String formatArray(Data[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i].key).append("/").append(arr[i].val);
            if (i != arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    // QuickSort entry
    public static void quickSort(Data[] arr) throws IOException {
        quickSortHelper(arr, 0, arr.length - 1);
    }

    // Recursive quicksort with step logging
    private static void quickSortHelper(Data[] arr, int low, int high) throws IOException {
        if (low < high) {
            int pi = partition(arr, low, high);
            stepWriter.write("pi=" + pi + "," + formatArray(arr) + "\n");
            quickSortHelper(arr, low, pi - 1);
            quickSortHelper(arr, pi + 1, high);
        }
    }

    // Partition using last element as pivot
    private static int partition(Data[] arr, int low, int high) {
        int pivot = arr[high].key;
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j].key <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    // Swap elements
    private static void swap(Data[] arr, int i, int j) {
        if (i != j) {
            Data temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    // Read specified rows from CSV into Data list
    private static List<Data> readCSV(String filename, int startRow, int endRow) throws IOException {
        List<Data> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int row = 1;
            while ((line = br.readLine()) != null) {
                if (row >= startRow && row <= endRow) {
                    String[] parts = line.split(",", 2);
                    if (parts.length == 2) {
                        try {
                            int key = Integer.parseInt(parts[0].trim());
                            String val = parts[1].trim();
                            list.add(new Data(key, val));
                        } catch (NumberFormatException e) {
                            System.err.println("Skipping malformed integer at row " + row);
                        }
                    } else {
                        System.err.println("Skipping malformed line at row " + row);
                    }
                }
                if (row > endRow) break;
                row++;
            }
        }
        return list;
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java quick_sort_step <csv_file> <start_row> <end_row>");
            return;
        }

        String csvFile = args[0];
        int startRow, endRow;

        try {
            startRow = Integer.parseInt(args[1]);
            endRow = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.err.println("Start and end rows must be integers.");
            return;
        }

        try {
            List<Data> dataList = readCSV(csvFile, startRow, endRow);
            if (dataList.isEmpty()) {
                System.err.println("No data found in the specified row range.");
                return;
            }
            Data[] arr = dataList.toArray(new Data[0]);

            String logFile = String.format("quick_sort_step_%d_%d.txt", startRow, endRow);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile))) {
                stepWriter = writer;
                stepWriter.write("# Quicksort steps and sorted data\n");
                stepWriter.write("Original Array:," + formatArray(arr) + "\n");
                quickSort(arr);
            }

            System.out.println("Quick sort steps and sorted data saved to " + logFile);

        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
        }
    }
}
