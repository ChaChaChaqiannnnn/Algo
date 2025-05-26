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

public class merge_sort {

    public static void mergeSort(Data[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(Data[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Data[] leftArr = new Data[n1];
        Data[] rightArr = new Data[n2];

        for (int i = 0; i < n1; i++)
            leftArr[i] = arr[left + i];
        for (int j = 0; j < n2; j++)
            rightArr[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;

        // Compare based on string value, alphabetical order
        while (i < n1 && j < n2) {
            if (leftArr[i].value.compareTo(rightArr[j].value) <= 0) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        while (i < n1)
            arr[k++] = leftArr[i++];
        while (j < n2)
            arr[k++] = rightArr[j++];
    }

    public static Data[] readCSV(String filename) throws IOException {
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
        return dataList.toArray(new Data[0]);
    }

    public static void writeCSV(Data[] data, String filename, int maxRows) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            int rowsToWrite = Math.min(maxRows, data.length);
            for (int i = 0; i < rowsToWrite; i++) {
                bw.write(data[i].toString());
                bw.newLine();
            }
        }
    }

    public static void main(String[] args) {
        String inputFile = "dataset_1000000.csv";
        String outputFile = "merge_sort_1000000.csv";

        try {
            System.out.println("Reading input file...");
            Data[] data = readCSV(inputFile);

            System.out.println("Starting merge sort by string value...");
            long startTime = System.currentTimeMillis();

            mergeSort(data, 0, data.length - 1);

            long endTime = System.currentTimeMillis();
            double elapsedSeconds = (endTime - startTime) / 1000.0;

            System.out.printf("Merge sort completed in %.3f seconds.%n", elapsedSeconds);

            System.out.println("Writing first 5 sorted rows to output file...");
            writeCSV(data, outputFile, 5);

            System.out.println("All done!");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
