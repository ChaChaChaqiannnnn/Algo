import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Data {
    long key;
    String value;

    Data(long key, String value) {
        this.key = key;
        this.value = value;
    }
}

public class binary_search {

    public static int binarySearch(List<Data> list, long targetKey) {
        int left = 0, right = list.size() - 1;
        while (left <= right) {
            int mid = (left + right) >>> 1;  // Avoid overflow
            long midKey = list.get(mid).key;
            if (midKey == targetKey) {
                return mid;
            } else if (midKey < targetKey) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public static List<Data> readCSV(String filename) throws IOException {
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
        return dataList;
    }

    public static void writeSearchTimes(String filename, double bestTime, double avgTime, double worstTime) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(String.format("Best case running time: %.6e seconds%n", bestTime));
            writer.write(String.format("Average case running time: %.6e seconds%n", avgTime));
            writer.write(String.format("Worst case running time: %.6e seconds%n", worstTime));
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java binary_search<sorted_dataset_file>");
            return;
        }
        String inputFile = args[0];

        try {
            // Measure dataset loading time
            long loadStart = System.nanoTime();
            List<Data> dataList = readCSV(inputFile);
            long loadEnd = System.nanoTime();
            double loadTime = (loadEnd - loadStart) / 1e9;

            if (dataList.isEmpty()) {
                System.err.println("Dataset is empty.");
                return;
            }

            // Prepare targets
            // Best case: middle element
            long bestTarget = dataList.get(dataList.size() / 2).key;
            // Average case: random element
            long avgTarget = dataList.get((int)(Math.random() * dataList.size())).key;
            // Worst case: not in the array (e.g., -1)
            long worstTarget = -1;

            // Timing best case
            long bestStart = System.nanoTime();
            binarySearch(dataList, bestTarget);
            long bestEnd = System.nanoTime();
            double bestTime = (bestEnd - bestStart) / 1e9;

            // Timing average case
            long avgStart = System.nanoTime();
            binarySearch(dataList, avgTarget);
            long avgEnd = System.nanoTime();
            double avgTime = (avgEnd - avgStart) / 1e9;

            // Timing worst case
            long worstStart = System.nanoTime();
            binarySearch(dataList, worstTarget);
            long worstEnd = System.nanoTime();
            double worstTime = (worstEnd - worstStart) / 1e9;

            // Print only the runtimes
            System.out.printf("Dataset loading time: %.6f seconds\n", loadTime);
            System.out.printf("Best case: %.6f seconds\n", bestTime);
            System.out.printf("Average case: %.6f seconds\n", avgTime);
            System.out.printf("Worst case: %.6f seconds\n", worstTime);

        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        }
    }

    public static Data[] readData(String filename) throws IOException {
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

    public static int binarySearch(Data[] data, int target, BufferedWriter writer) throws IOException {
        int left = 0;
        int right = data.length - 1;
        int step = 0;

        while (left <= right) {
            step++;
            int mid = (left + right) >>> 1;
            long midKey = data[mid].key;

            // Log the current step
            writer.write(String.format("Step %d: left=%d, right=%d, mid=%d, midKey=%d%n", step, left, right, mid, midKey));

            if (midKey == target) {
                return mid;
            } else if (midKey < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
