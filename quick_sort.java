import java.io.*;
import java.util.*;

//from data data_sample_10000.csv
public class quick_sort {

    static class Data {
        int key;
        String val;
        Data(int k, String v) { key = k; val = v; }
    }

    public static void quickSort(Data[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

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

    private static void swap(Data[] arr, int i, int j) {
        if (i != j) {
            Data temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    private static List<Data> readCSV(String filename) throws IOException {
        List<Data> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    try {
                        int key = Integer.parseInt(parts[0].trim());
                        String val = parts[1].trim();
                        list.add(new Data(key, val));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping malformed integer line: " + line);
                    }
                } else {
                    System.err.println("Skipping malformed line: " + line);
                }
            }
        }
        return list;
    }

    private static void writeSortedCSV(String filename, Data[] arr) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Data d : arr) {
                bw.write(d.key + "," + d.val);
                bw.newLine();
            }
        }
    }

    public static void main(String[] args) {
        String csvFile = "dataset_sample_1000.csv"; // fixed input filename
        String outputCsvFile = "quick_sort_1000.csv";

        try {
            List<Data> dataList = readCSV(csvFile);
            if (dataList.isEmpty()) {
                System.err.println("No data found in the file.");
                return;
            }
            Data[] arr = dataList.toArray(new Data[0]);

            quickSort(arr, 0, arr.length - 1);

            writeSortedCSV(outputCsvFile, arr);
            System.out.println("Sorted data saved to " + outputCsvFile);

            System.out.print("Sorted array keys: ");
            for (Data d : arr) System.out.print(d.key + " ");
            System.out.println();

        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
        }
    }
}
