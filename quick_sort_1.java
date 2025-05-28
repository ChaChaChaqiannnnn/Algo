import java.io.*;
import java.util.*;

public class quick_sort_1 {

    static class Data {
        long key;
        String val;

        Data(long key, String val) {
            this.key = key;
            this.val = val;
        }
    }

    private static Random rand = new Random();

    public static void quickSort(Data[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(Data[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = randomizedPartition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int randomizedPartition(Data[] arr, int low, int high) {
        int pivotIndex = low + rand.nextInt(high - low + 1);
        swap(arr, pivotIndex, high);
        return partition(arr, low, high);
    }

    private static int partition(Data[] arr, int low, int high) {
        long pivot = arr[high].key;
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

    private static void swap(Data[] arr, int i, int j) {
        Data temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Read CSV into Data[]
    public static Data[] readCSV(String filename) throws IOException {
        List<Data> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Assume CSV with 2 columns separated by comma
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    long key = Long.parseLong(parts[0].trim());
                    String val = parts[1].trim();
                    dataList.add(new Data(key, val));
                }
            }
        }
        return dataList.toArray(new Data[0]);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java QuickSortCSV <csv-file-path>");
            return;
        }

        try {
            // Read CSV file
            Data[] dataArray = readCSV(args[0]);

            // Sort the array
            quickSort(dataArray);

            // Print first 20 sorted records
            System.out.println("First 20 sorted entries:");
            for (int i = 0; i < Math.min(20, dataArray.length); i++) {
                System.out.println(dataArray[i].key + ", " + dataArray[i].val);
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing key as long integer: " + e.getMessage());
        }
    }
}
