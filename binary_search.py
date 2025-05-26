import csv
import time

class Data:
    def __init__(self, key, value):
        self.key = int(key)
        self.value = value

    def __repr__(self):
        return f"{self.key},{self.value}"

def binary_search(arr, target_key):
    left, right = 0, len(arr) - 1
    while left <= right:
        mid = (left + right) // 2
        if arr[mid].key == target_key:
            return mid  # Target found
        elif arr[mid].key < target_key:
            left = mid + 1
        else:
            right = mid - 1
    return -1  # Target not found

def read_csv(filename):
    data_list = []
    with open(filename, newline='') as csvfile:
        reader = csv.reader(csvfile)
        for row in reader:
            if len(row) == 2:
                key = row[0].strip()
                value = row[1].strip()
                if key.isdigit():
                    data_list.append(Data(key, value))
    return data_list

def write_search_times(filename, best_time, avg_time, worst_time):
    with open(filename, mode='w', newline='') as file:
        file.write(f"Best case running time: {best_time:.6f} seconds\n")
        file.write(f"Average case running time: {avg_time:.6f} seconds\n")
        file.write(f"Worst case running time: {worst_time:.6f} seconds\n")

def main():
    input_file = "merge_sort_1000000.csv"  # Input file with sorted data
    output_file = "binary_search_1000000.txt"  # Output file for running times

    print("Reading sorted data from input file...")
    data = read_csv(input_file)

    n = len(data)  # Size of dataset

    # Best case: Search for the middle element in each iteration
    print("Testing best case (middle element)...")
    start_time = time.perf_counter()
    for _ in range(n):  # Perform n searches for the best case
        target_best = data[n // 2].key  # Middle element
        binary_search(data, target_best)
    end_time = time.perf_counter()
    best_time = (end_time - start_time) / n  # Average time per search in best case

    # Average case: Search for a random element near the middle
    print("Testing average case (random element near middle)...")
    start_time = time.perf_counter()
    for _ in range(n):  # Perform n searches for the average case
        target_avg = data[min(n // 2 + 1, n - 1)].key  # Safe index near middle
        binary_search(data, target_avg)
    end_time = time.perf_counter()
    avg_time = (end_time - start_time) / n  # Average time per search in average case

    # Worst case: Search for an element that doesn't exist
    print("Testing worst case (non-existent element)...")
    start_time = time.perf_counter()
    for _ in range(n):  # Perform n searches for the worst case
        target_worst = -1  # Element that doesn't exist
        binary_search(data, target_worst)
    end_time = time.perf_counter()
    worst_time = (end_time - start_time) / n  # Average time per search in worst case

    print("Writing results to output file...")
    write_search_times(output_file, best_time, avg_time, worst_time)

    print("Done.")

if __name__ == "__main__":
    main()
