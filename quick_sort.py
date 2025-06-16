import random
import time
import csv
import re
import sys

# from data dataset_1000000.csv
class Data:
    def __init__(self, key, val):
        self.key = key
        self.val = val

def swap(arr, i, j):
    arr[i], arr[j] = arr[j], arr[i]

def partition(arr, low, high):
    pivot = arr[high].key
    i = low - 1
    for j in range(low, high):
        if arr[j].key <= pivot:
            i += 1
            swap(arr, i, j)
    swap(arr, i + 1, high)
    return i + 1

def randomized_partition(arr, low, high):
    pivot_index = random.randint(low, high)
    swap(arr, pivot_index, high)
    return partition(arr, low, high)

def quicksort(arr, low, high):
    if low < high:
        pi = randomized_partition(arr, low, high)
        quicksort(arr, low, pi - 1)
        quicksort(arr, pi + 1, high)

def read_csv(filename):
    data_list = []
    try:
        with open(filename, newline='', encoding='utf-8') as csvfile:
            reader = csv.reader(csvfile)
            for row in reader:
                if len(row) == 2:
                    try:
                        key = int(row[0].strip())
                        val = row[1].strip()
                        data_list.append(Data(key, val))
                    except ValueError:
                        print(f"Skipping malformed line: {row}")
                else:
                    print(f"Skipping malformed line: {row}")
    except FileNotFoundError:
        print(f"Error: The file {filename} was not found.")
        return []
    except Exception as e:
        print(f"Error reading file: {e}")
        return []
    
    return data_list

def write_sorted_csv(filename, arr):
    try:
        with open(filename, 'w', newline='', encoding='utf-8') as csvfile:
            writer = csv.writer(csvfile)
            for d in arr:
                writer.writerow([d.key, d.val])
    except Exception as e:
        print(f"Error writing to file: {e}")

def main():
    if len(sys.argv) < 2:
        print("Usage: python quick_sort.py <input_csv>")
        return

    input_file = sys.argv[1]
    match = re.search(r'(\d+)', input_file)
    num_part = match.group(1) if match else 'output'
    output_file = f'quick_sort_{num_part}.csv'

    # Measure dataset loading time
    load_start = time.time()
    data_list = read_csv(input_file)
    load_time = time.time() - load_start

    if not data_list:
        print("No valid data to sort.")
        return

    # Average case (random order)
    avg_case = [Data(d.key, d.val) for d in data_list]
    start_time = time.time()
    quicksort(avg_case, 0, len(avg_case) - 1)
    avg_time = time.time() - start_time

    # Best case (already sorted)
    best_case = sorted(data_list, key=lambda d: d.key)
    start_time = time.time()
    quicksort(best_case, 0, len(best_case) - 1)
    best_time = time.time() - start_time

    # Worst case (reverse sorted)
    worst_case = sorted(data_list, key=lambda d: d.key, reverse=True)
    start_time = time.time()
    quicksort(worst_case, 0, len(worst_case) - 1)
    worst_time = time.time() - start_time

    # Write the sorted output for average case only
    write_sorted_csv(output_file, avg_case)

    # Print only the runtimes
    print(f"Dataset loading time: {load_time:.6f} seconds")
    print(f"Best case: {best_time:.6f} seconds")
    print(f"Average case: {avg_time:.6f} seconds")
    print(f"Worst case: {worst_time:.6f} seconds")

if __name__ == "__main__":
    main()
