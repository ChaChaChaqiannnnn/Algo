import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (args.length < 1) {
            System.out.println("Usage: java merge_sort <input_csv>");
            return;
        }
        String inputFile = args[0];

        // Extract number from input file name
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(inputFile);
        String numPart = "";
        if (m.find()) {
            numPart = m.group(1);
        }
        String outputFile = "merge_sort_" + numPart + ".csv";

        try {
            // Measure dataset loading time
            long loadStart = System.nanoTime();
            List<Data> data = readCSV(inputFile);
            long loadEnd = System.nanoTime();
            double loadTime = (loadEnd - loadStart) / 1e9;

            if (data.isEmpty()) {
                System.err.println("No valid data found in the file.");
                return;
            }

            // Average case (random order)
            List<Data> avgCase = new ArrayList<>();
            for (Data d : data) avgCase.add(new Data(d.key, d.value));
            long avgStart = System.nanoTime();
            mergeSort(avgCase);
            long avgEnd = System.nanoTime();
            double avgTime = (avgEnd - avgStart) / 1e9;

            // Best case (already sorted by value)
            List<Data> bestCase = new ArrayList<>(data);
            bestCase.sort(Comparator.comparing(d -> d.value));
            long bestStart = System.nanoTime();
            mergeSort(bestCase);
            long bestEnd = System.nanoTime();
            double bestTime = (bestEnd - bestStart) / 1e9;

            // Worst case (reverse sorted by value)
            List<Data> worstCase = new ArrayList<>(data);
            worstCase.sort((a, b) -> b.value.compareTo(a.value));
            long worstStart = System.nanoTime();
            mergeSort(worstCase);
            long worstEnd = System.nanoTime();
            double worstTime = (worstEnd - worstStart) / 1e9;

            // Write the sorted output for average case only (first 5 rows)
            writeCSVFirst5(avgCase, outputFile);

            // Print only the runtimes
            System.out.printf("Dataset loading time: %.6f seconds\n", loadTime);
            System.out.printf("Best case: %.6f seconds\n", bestTime);
            System.out.printf("Average case: %.6f seconds\n", avgTime);
            System.out.printf("Worst case: %.6f seconds\n", worstTime);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
