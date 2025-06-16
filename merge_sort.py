import csv
import time
import re
import sys

class Data:
    def __init__(self, key, value):
        self.key = int(key)
        self.value = value

    def __repr__(self):
        return f"{self.key},{self.value}"

def merge_sort(arr):
    if len(arr) > 1:
        mid = len(arr) // 2
        left_half = arr[:mid]
        right_half = arr[mid:]

        merge_sort(left_half)
        merge_sort(right_half)

        i = j = k = 0

        while i < len(left_half) and j < len(right_half):
            if left_half[i].value <= right_half[j].value:
                arr[k] = left_half[i]
                i += 1
            else:
                arr[k] = right_half[j]
                j += 1
            k += 1

        while i < len(left_half):
            arr[k] = left_half[i]
            i += 1
            k += 1

        while j < len(right_half):
            arr[k] = right_half[j]
            j += 1
            k += 1

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

def write_csv(data, filename, max_rows=5):
    with open(filename, mode='w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        for d in data[:max_rows]:
            writer.writerow([d.key, d.value])

def main():
    if len(sys.argv) < 2:
        print("Usage: python merge_sort.py <input_csv>")
        return

    input_file = sys.argv[1]
    match = re.search(r'(\d+)', input_file)
    num_part = match.group(1) if match else ''
    output_file = f"merge_sort_{num_part}.csv"

    # Read data
    load_start = time.time()
    data = read_csv(input_file)
    load_time = time.time() - load_start

    if not data:
        print("No valid data to sort.")
        return

    # Average case (random order)
    avg_case = [Data(d.key, d.value) for d in data]
    start_time = time.time()
    merge_sort(avg_case)
    avg_time = time.time() - start_time

    # Best case (already sorted by value)
    best_case = sorted(data, key=lambda d: d.value)
    start_time = time.time()
    merge_sort(best_case)
    best_time = time.time() - start_time

    # Worst case (reverse sorted by value)
    worst_case = sorted(data, key=lambda d: d.value, reverse=True)
    start_time = time.time()
    merge_sort(worst_case)
    worst_time = time.time() - start_time

    # Write the sorted output for average case only
    write_csv(avg_case, output_file)

    # Print only the runtimes
    print(f"Dataset loading time: {load_time:.6f} seconds")
    print(f"Best case: {best_time:.6f} seconds")
    print(f"Average case: {avg_time:.6f} seconds")
    print(f"Worst case: {worst_time:.6f} seconds")

if __name__ == "__main__":
    main()
