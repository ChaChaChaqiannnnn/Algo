import csv
import time

class Data:
    def __init__(self, key, value):
        self.key = int(key)
        self.value = value

def binary_search(arr, target_key):
    left, right = 0, len(arr) - 1
    while left <= right:
        mid = (left + right) // 2
        if arr[mid].key == target_key:
            return mid
        elif arr[mid].key < target_key:
            left = mid + 1
        else:
            right = mid - 1
    return -1

def read_csv(filename):
    data_list = []
    with open(filename, newline='') as csvfile:
        reader = csv.reader(csvfile)
        for row in reader:
            if len(row) == 2 and row[0].isdigit():
                data_list.append(Data(row[0].strip(), row[1].strip()))
    return data_list

def write_search_times(filename, best_time, avg_time, worst_time):
    with open(filename, 'w') as f:
        f.write(f"Best case running time: {best_time:.6e} seconds\n")
        f.write(f"Average case running time: {avg_time:.6e} seconds\n")
        f.write(f"Worst case running time: {worst_time:.6e} seconds\n")

def main():
    input_file = "merge_sort_1000000.csv"
    output_file = "binary_search_1000000.txt"

    data = read_csv(input_file)
    dataset_size = len(data)
    if dataset_size == 0:
        print("Dataset is empty!")
        return

    # Explicit number of searches to perform
    n = 100000  # For example, 100,000 searches; you can change this as needed

    # Prepare targets safely
    middle_index = dataset_size // 2
    best_target = data[middle_index].key
    avg_target = data[min(middle_index + 1, dataset_size - 1)].key
    worst_target = -1  # Not in dataset

    # Run n searches for best case
    start = time.perf_counter()
    for _ in range(n):
        binary_search(data, best_target)
    end = time.perf_counter()
    best_time = (end - start) / n

    # Run n searches for average case
    start = time.perf_counter()
    for _ in range(n):
        binary_search(data, avg_target)
    end = time.perf_counter()
    avg_time = (end - start) / n

    # Run n searches for worst case
    start = time.perf_counter()
    for _ in range(n):
        binary_search(data, worst_target)
    end = time.perf_counter()
    worst_time = (end - start) / n

    write_search_times(output_file, best_time, avg_time, worst_time)
    print(f"Performed {n} searches per case.")
    print(f"Running times saved to {output_file}")

if __name__ == "__main__":
    main()
