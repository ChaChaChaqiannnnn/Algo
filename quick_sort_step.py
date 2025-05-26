import sys

def swap(arr, i, j):
    if i != j:
        arr[i], arr[j] = arr[j], arr[i]

def partition(arr, low, high, step_file):
    pivot = arr[high]
    step_file.write(f"Partitioning with pivot {pivot} at index {high}\n")
    i = low - 1

    for j in range(low, high):
        if arr[j] <= pivot:
            i += 1
            swap(arr, i, j)
            step_file.write(f"Swapped elements at indices {i} and {j}: {arr}\n")

    swap(arr, i + 1, high)
    step_file.write(f"Swapped pivot with element at index {i+1}: {arr}\n")

    return i + 1

def quick_sort_helper(arr, low, high, step_file):
    if low < high:
        pi = partition(arr, low, high, step_file)
        step_file.write(f"Pivot placed at index {pi} with value {arr[pi]}\n")
        step_file.write(f"Current array: {arr}\n\n")
        quick_sort_helper(arr, low, pi - 1, step_file)
        quick_sort_helper(arr, pi + 1, high, step_file)

def quick_sort(arr, step_file):
    quick_sort_helper(arr, 0, len(arr) - 1, step_file)

def read_csv_first_column(filename, start_row, end_row):
    data = []
    with open(filename, 'r') as f:
        for idx, line in enumerate(f, 1):
            if idx < start_row:
                continue
            if idx > end_row:
                break
            parts = line.strip().split(',')
            if len(parts) > 0:
                try:
                    data.append(int(parts[0]))
                except ValueError:
                    print(f"Skipping malformed integer at line {idx}")
    return data

def main():
    if len(sys.argv) < 4:
        print("Usage: python quick_sort_step.py <csv_file> <start_row> <end_row>")
        return

    csv_file = sys.argv[1]
    start_row = int(sys.argv[2])
    end_row = int(sys.argv[3])

    arr = read_csv_first_column(csv_file, start_row, end_row)

    if not arr:
        print("No data read from the CSV file within the specified rows.")
        return

    out_file = f"quick_sort_step_{start_row}_{end_row}.txt"
    with open(out_file, 'w') as step_file:
        quick_sort(arr, step_file)

    print(f"Sorted array: {' '.join(map(str, arr))}")
    print(f"Quick sort steps saved to {out_file}")

if __name__ == "__main__":
    main()
