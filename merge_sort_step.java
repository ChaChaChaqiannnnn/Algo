import java.io.*;
import java.util.*;

public class merge_sort_step {

    private static BufferedWriter stepWriter;

    // Data class holding integer + string pair
    static class Data {
        int key;
        String val;
        Data(int k, String v) { key = k; val = v; }
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

    // MergeSort entry
    public static void mergeSort(Data[] arr) throws IOException {
        mergeSortHelper(arr, 0, arr.length - 1);
    }

    // Recursive merge sort with step logging
    private static void mergeSortHelper(Data[] arr, int left, int right) throws IOException {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSortHelper(arr, left, mid);
        mergeSortHelper(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    // Merge two subarrays and log the steps
    private static void merge(Data[] arr, int left, int mid, int right) throws IOException {
        Data[] leftArr = Arrays.copyOfRange(arr, left, mid + 1);
        Data[] rightArr = Arrays.copyOfRange(arr, mid + 1, right + 1);

        // Log left and right arrays before merging
        stepWriter.write(formatArray(leftArr) + "\n");
        stepWriter.write(formatArray(rightArr) + "\n");

        int i = 0, j = 0, k = left;
        while (i < leftArr.length && j < rightArr.length) {
            if (leftArr[i].key <= rightArr[j].key) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        while (i < leftArr.length) arr[k++] = leftArr[i++];
        while (j < rightArr.length) arr[k++] = rightArr[j++];

        // Log the merged array
        stepWriter.write("Merged: " + formatArray(Arrays.copyOfRange(arr, left, right + 1)) + "\n\n");
    }

    // Read CSV data and return it as Data[]
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
                            System.err.println("Skipping malformed integer at line " + row);
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
            System.out.println("Usage: java merge_sort_step <csv_file> <start_row> <end_row>");
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

            String outFile = String.format("merge_sort_step_%d_%d.txt", startRow, endRow);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
                stepWriter = writer;
                stepWriter.write("# Merge sort steps and sorted data\n");
                stepWriter.write("Original Array:," + formatArray(arr) + "\n");
                mergeSort(arr);
            }

            System.out.println("Merge sort steps saved to " + outFile);

        } catch (IOException e) {
            System.err.println("Error:");
            e.printStackTrace();
        }
    }
}
