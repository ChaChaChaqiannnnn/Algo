import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class quick_sort {

    static class Data {
        long key;  // Support for long integers
        String val;

        Data(long key, String val) {
            this.key = key;
            this.val = val;
        }

        // Overloading constructor for int keys (if needed for smaller datasets)
        Data(int key, String val) {
            this.key = key;
            this.val = val;
        }
    }

    private static Random rand = new Random();

    // Quick Sort with randomized pivot
    public static void quickSort(Data[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = randomizedPartition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    // Randomized partition for better pivot choice
    private static int randomizedPartition(Data[] arr, int low, int high) {
        int pivotIndex = low + rand.nextInt(high - low + 1);
        swap(arr, pivotIndex, high);
        return partition(arr, low, high);
    }

    // Partitioning function that places pivot at correct position
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

    // Swap elements in the array
    private static void swap(Data[] arr, int i, int j) {
        Data temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Read CSV into Data[]
    public static List<Data> readCSV(String filename) throws IOException {
        List<Data> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Assuming CSV with 2 columns: key and value
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    try {
                        // Try parsing long key first, if fails then try int
                        long key = Long.parseLong(parts[0].trim());
                        String val = parts[1].trim();
                        dataList.add(new Data(key, val));
                    } catch (NumberFormatException e) {
                        // Fallback for int key if long fails
                        try {
                            int key = Integer.parseInt(parts[0].trim());
                            String val = parts[1].trim();
                            dataList.add(new Data(key, val));  // Fallback with int key
                        } catch (NumberFormatException ex) {
                            System.err.println("Skipping malformed line (invalid key): " + line);
                        }
                    }
                } else {
                    System.err.println("Skipping malformed line: " + line);
                }
            }
        }
        return dataList;
    }

    // Write sorted data to CSV
    private static void writeSortedCSV(String filename, Data[] arr) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Data d : arr) {
                bw.write(d.key + "," + d.val);
                bw.newLine();
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java quick_sort <input_csv>");
            return;
        }

        String inputFile = args[0];

        // Extract numeric part from input filename to dynamically name output file
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(inputFile);
        String numPart = "";
        if (m.find()) {
            numPart = m.group(1);
        }
        String outputFile = "quick_sort_" + numPart + ".csv";

        try {
            // Measure dataset loading time
            long loadStart = System.nanoTime();
            List<Data> dataList = readCSV(inputFile);
            long loadEnd = System.nanoTime();
            double loadTime = (loadEnd - loadStart) / 1e9;

            if (dataList.isEmpty()) {
                System.err.println("No valid data found in the file.");
                return;
            }

            // Average case (random order)
            Data[] avgCase = dataList.toArray(new Data[0]);
            long avgStart = System.nanoTime();
            quickSort(avgCase, 0, avgCase.length - 1);
            long avgEnd = System.nanoTime();
            double avgTime = (avgEnd - avgStart) / 1e9;

            // Best case (already sorted)
            Data[] bestCase = dataList.toArray(new Data[0]);
            Arrays.sort(bestCase, Comparator.comparingLong(d -> d.key));
            long bestStart = System.nanoTime();
            quickSort(bestCase, 0, bestCase.length - 1);
            long bestEnd = System.nanoTime();
            double bestTime = (bestEnd - bestStart) / 1e9;

            // Worst case (reverse sorted)
            Data[] worstCase = dataList.toArray(new Data[0]);
            Arrays.sort(worstCase, (a, b) -> Long.compare(b.key, a.key));
            long worstStart = System.nanoTime();
            quickSort(worstCase, 0, worstCase.length - 1);
            long worstEnd = System.nanoTime();
            double worstTime = (worstEnd - worstStart) / 1e9;

            // Write the sorted output for average case only
            writeSortedCSV(outputFile, avgCase);

            // Print only the runtimes
            System.out.printf("Dataset loading time: %.6f seconds\n", loadTime);
            System.out.printf("Best case: %.6f seconds\n", bestTime);
            System.out.printf("Average case: %.6f seconds\n", avgTime);
            System.out.printf("Worst case: %.6f seconds\n", worstTime);

        } catch (IOException e) {
            System.err.println("Error reading or writing file: " + e.getMessage());
        }
    }
}
