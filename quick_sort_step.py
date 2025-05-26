import sys

def quicksort(arr, low, high, log_file):
    if low < high:
        pi = partition(arr, low, high, log_file)

        log_file.write(f"Pivot placed at index {pi} with value {arr[pi][0]}\n")
        log_file.write("Current array: " + " ".join(str(x[0]) for x in arr) + "\n\n")

        quicksort(arr, low, pi - 1, log_file)
        quicksort(arr, pi + 1, high, log_file)

def partition(arr, low, high, log_file):
    pivot = arr[high][0]
    log_file.write(f"Partitioning with pivot {pivot} at index {high}\n")
    i = low - 1
    for j in range(low, high):
        if arr[j][0] <= pivot:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]
            log_file.write(f"Swapped elements at indices {i} and {j}: " + " ".join(str(x[0]) for x in arr) + "\n")
    arr[i+1], arr[high] = arr[high], arr[i+1]
    log_file.write(f"Swapped pivot with element at index {i+1}: " + " ".join(str(x[0]) for x in arr) + "\n")
    return i + 1

def read_csv(filename, start_row, end_row):
    data = []
    with open(filename, "r") as f:
        for idx, line in enumerate(f, 1):
            if idx < start_row:
                continue
            if idx > end_row:
                break
            parts = line.strip().split(",", 1)
            if len(parts) == 2:
                try:
                    key = int(parts[0].strip())
                    val = parts[1].strip()
                    data.append((key, val))
                except ValueError:
                    print(f"Skipping malformed integer at line {idx}")
            else:
                print(f"Skipping malformed line at line {idx}")
    return data

def main():
    if len(sys.argv) < 4:
        print("Usage: python quick_sort_step.py <csv_file> <start_row> <end_row>")
        sys.exit(1)

    csv_file = sys.argv[1]
    try:
        start_row = int(sys.argv[2])
        end_row = int(sys.argv[3])
    except ValueError:
        print("Start and end rows must be integers.")
        sys.exit(1)

    if start_row > end_row or start_row < 1:
        print("Invalid row range.")
        sys.exit(1)

    data = read_csv(csv_file, start_row, end_row)

    if not data:
        print("No data found in the specified row range.")
        sys.exit(1)

    log_filename = f"quick_sort_step_{start_row}_{end_row}.txt"
    with open(log_filename, "w") as log_file:
        quicksort(data, 0, len(data)-1, log_file)

    print(f"Quick sort steps written to {log_filename}")

    print("Sorted array keys: " + " ".join(str(x[0]) for x in data))

if __name__ == "__main__":
    main()

