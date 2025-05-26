import sys

class Data:
    def __init__(self, key, val):
        self.key = key
        self.val = val

    def __repr__(self):
        return f"{self.key}/{self.val}"

def format_array(arr):
    return "[" + ", ".join(str(d) for d in arr) + "]"

def swap(arr, i, j):
    if i != j:
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

def quick_sort_helper(arr, low, high, step_file):
    if low < high:
        pi = partition(arr, low, high)
        step_file.write(f"pi={pi},{format_array(arr)}\n")
        quick_sort_helper(arr, low, pi - 1, step_file)
        quick_sort_helper(arr, pi + 1, high, step_file)

def quick_sort(arr, step_file):
    quick_sort_helper(arr, 0, len(arr) - 1, step_file)

def read_csv(filename, start_row, end_row):
    data = []
    with open(filename, 'r') as f:
        for idx, line in enumerate(f, 1):
            if idx < start_row:
                continue
            if idx > end_row:
                break
            parts = line.strip().split(',', 1)
            if len(parts) == 2:
                try:
                    key = int(parts[0])
                    val = parts[1]
                    data.append(Data(key, val))
                except ValueError:
                    print(f"Skipping malformed integer at line {idx}")
    return data

def main():
    if len(sys.argv) < 4:
        print("Usage: python quick_sort_step.py <csv_file> <start_row> <end_row>")
        return

    csv_file = sys.argv[1]
    try:
        start_row = int(sys.argv[2])
        end_row = int(sys.argv[3])
    except ValueError:
        print("Start and end rows must be integers.")
        return

    data = read_csv(csv_file, start_row, end_row)
    if not data:
        print("No data found in the specified row range.")
        return

    # Create the output text file for quicksort steps
    output_file = f"quick_sort_step_{start_row}_{end_row}.txt"
    with open(output_file, 'w') as f:
        f.write("# Quicksort steps and sorted data\n")
        f.write("Original Array:," + format_array(data) + "\n")
        quick_sort(data, f)

    print(f"Quick sort steps and sorted data saved to {output_file}")

if __name__ == "__main__":
    main()
