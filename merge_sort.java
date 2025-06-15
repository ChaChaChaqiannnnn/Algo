import java.io.*;
import java.util.*;

public class merge_sort {

    static class Data {
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

    // Stable merge sort by string value
    public static void mergeSort(List<Data> list) {
        if (list.size() > 1) {
            int mid = list.size() / 2;
            List<Data> left = new ArrayList<>(list.subList(0, mid));
            List<Data> right = new ArrayList<>(list.subList(mid, list.size()));

            mergeSort(left);
            mergeSort(right);

            int i = 0, j = 0, k = 0;
            while (i < left.size() && j < right.size()) {
                if (left.get(i).value.compareTo(right.get(j).value) <= 0) {
                    list.set(k++, left.get(i++));
                } else {
                    list.set(k++, right.get(j++));
                }
            }
            while (i < left.size()) {
                list.set(k++, left.get(i++));
            }
            while (j < right.size()) {
                list.set(k++, right.get(j++));
            }
        }
    }

    // Read CSV into List<Data>
    public static List<Data> readCSV(String filename) throws IOException {
        List<Data> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    try {
                        long key = Long.parseLong(parts[0].trim());
                        String val = parts[1].trim();
                        dataList.add(new Data(key, val));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid line (bad key): " + line);
                    }
                }
            }
        }
        return dataList;
    }

    // Write only first 5 rows to CSV
    public static void writeCSVFirst5(List<Data> data, String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            int limit = Math.min(5, data.size());
            for (int i = 0; i < limit; i++) {
                Data d = data.get(i);
                bw.write(d.key + "," + d.value);
                bw.newLine();
            }
        }
    }

    public static void main(String[] args) {
        String inputFile = "dataset_1000000.csv";
        String outputFile = "merge_sort_1000000.csv";

        try {
            System.out.println("Reading input file...");
            List<Data> data = readCSV(inputFile);
            int inputSize = data.size();

            System.out.println("Sorting by string value...");
            long startTime = System.currentTimeMillis();

            mergeSort(data);

            long endTime = System.currentTimeMillis();
            double elapsedSeconds = (endTime - startTime) / 1000.0;

            System.out.println("Sorting completed.");
            System.out.println("Input size: " + inputSize);
            System.out.printf("Time taken: %.6f seconds%n", elapsedSeconds);

            System.out.println("Writing first 5 sorted rows to output file...");
            writeCSVFirst5(data, outputFile);

            System.out.println("Sorted data written to " + outputFile);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
