import java.io.*;
import java.util.*;

public class quick_sort_step {

    private static BufferedWriter stepWriter;

    public static void quickSort(int[] arr) throws IOException {
        quickSortHelper(arr, 0, arr.length - 1);
    }

    private static void quickSortHelper(int[] arr, int low, int high) throws IOException {
        if (low < high) {
            int pi = partition(arr, low, high);

            stepWriter.write(String.format("Pivot placed at index %d with value %d\n", pi, arr[pi]));
            stepWriter.write("Current array: " + Arrays.toString(arr) + "\n\n");

            quickSortHelper(arr, low, pi - 1);
            quickSortHelper(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) throws IOException {
        int pivot = arr[high];
        stepWriter.write(String.format("Partitioning with pivot %d at index %d\n", pivot, high));
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
                stepWriter.write(String.format("Swapped elements at indices %d and %d: %s\n", i, j, Arrays.toString(arr)));
            }
        }

        swap(arr, i + 1, high);
        stepWriter.write(String.format("Swapped pivot with element at index %d: %s\n", i + 1, Arrays.toString(arr)));

        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        if (i != j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
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
            System.err.println("Start row and end row must be integers.");
            return;
        }

        List<Integer> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            int row = 1;
            while ((line = br.readLine()) != null) {
                if (row >= startRow && row <= endRow) {
                    String[] parts = line.split(",");
                    if (parts.length > 0) {
                        try {
                            data.add(Integer.parseInt(parts[0].trim()));
                        } catch (NumberFormatException ex) {
                            System.err.println("Skipping malformed integer at row " + row);
                        }
                    }
                }
                if (row > endRow) break;
                row++;
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            return;
        }

        int[] arr = data.stream().mapToInt(i -> i).toArray();

        String outFile = String.format("quick_sort_step_%d_%d.txt", startRow, endRow);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
            stepWriter = writer;
            quickSort(arr);
        } catch (IOException e) {
            System.err.println("Error writing output file: " + e.getMessage());
            return;
        }

        System.out.print("Sorted array: ");
        for (int num : arr) System.out.print(num + " ");
        System.out.println();
        System.out.println("Quick sort steps written to " + outFile);
    }
}
