import random
import time

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

def quick_sort(arr, low, high):
    if low < high:
        pi = randomized_partition(arr, low, high)
        quick_sort(arr, low, pi - 1)
        quick_sort(arr, pi + 1, high)

def quick_sort_wrapper(arr):
    quick_sort(arr, 0, len(arr) - 1)

def read_csv(filename):
    data_list = []
    with open(filename, 'r') as f:
        for line in f:
            parts = line.strip().split(',', 1)
            if len(parts) == 2:
                try:
                    key = int(parts[0].strip())
                    val = parts[1].strip()
                    data_list.append(Data(key, val))
                except ValueError:
                    pass
    return data_list

def write_csv(filename, data):
    with open(filename, 'w') as f:
        for d in data:
            f.write(f"{d.key},{d.val}\n")

def main():
    input_file = 'dataset_1000000.csv'
    output_file = 'quick_sort_1_1000000.csv'

    data = read_csv(input_file)
    input_size = len(data)
    if input_size == 0:
        print("No valid data to sort.")
        return

    start_time = time.time()
    quick_sort_wrapper(data)
    end_time = time.time()

    time_taken_ms = int((end_time - start_time) * 1000)

    write_csv(output_file, data)

    print("Sorting completed.")
    print(f"Input size: {input_size}")
    print(f"Time taken (ms): {time_taken_ms}")
    print(f"Sorted data written to {output_file}")

if __name__ == "__main__":
    main()
