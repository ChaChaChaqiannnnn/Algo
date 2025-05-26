import java.io.*;
import java.util.*;

public class merge_sort_step {

    private static BufferedWriter stepWriter;

    public static void mergeSort(int[] arr) throws IOException {
        if (arr == null || arr.length < 2) return;
        mergeSortHelper(arr, 0, arr.length - 1);
    }

    private static void mergeSortHelper(int[] arr, int left, int right) throws IOException {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSortHelper(arr, left, mid);
        mergeSortHelper(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private static void merge(int[] arr, int left, int mid, int right) throws IOException {
        int[] leftArr = Arrays.copyOfRange(arr, left, mid + 1);
        int[] rightArr = Arrays.copyOfRange(arr, mid + 1, right + 1);

        stepWriter.write(String.format("Merging subarrays [%d-%d] and [%d-%d]%n", left, mid, mid + 1, right));
        stepWriter.write("Left: " + Arrays.toString(leftArr) + "\n");
        stepWriter.write("Right: " + Arrays.toString(rightArr) + "\n");

        int i = 0, j = 0, k = left;
        while (i < leftArr.length && j < rightArr.length) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        while (i < leftArr.length) arr[k++] = leftArr[i++];
        while (j < rightArr.length) arr[k++] = rightArr[j++];

        stepWriter.write("Merged: " + Arrays.toString(Arrays.copyOfRange(arr, left, right + 1)) + "\n\n");
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java merge_sort_step <csv_file> <start_row> <end_row>");
            return;
        }

        String csvFile = args[0];
        int startRow = Integer.parseInt(args[1]);
        int endRow = Integer.parseInt(args[2]);

        try {
            // Read integers from first column between startRow and endRow
            List<Integer> data = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                String line;
                int row = 1;
                while ((line = br.readLine()) != null) {
                    if (row >= startRow && row <= endRow) {
                        String[] parts = line.split(",");
                        if (parts.length > 0) {
                            data.add(Integer.parseInt(parts[0].trim()));
                        } else {
                            System.err.println("Skipping malformed line " + row);
                        }
                    }
                    row++;
                    if (row > endRow) break;
                }
            }

            int[] arr = data.stream().mapToInt(i -> i).toArray();

            String outFile = String.format("merge_sort_step_%d_%d.txt", startRow, endRow);
            System.out.println("Writing merge sort steps to file: " + outFile);
            stepWriter = new BufferedWriter(new FileWriter(outFile));

            mergeSort(arr);

            stepWriter.close();
            System.out.println("Finished writing steps.");

            // Print sorted array on console
            System.out.print("Sorted array: ");
            for (int num : arr) System.out.print(num + " ");
            System.out.println();

        } catch (Exception e) {
            System.err.println("Error:");
            e.printStackTrace();
        }
    }
}
