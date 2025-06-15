import sys

class Data:
    def __init__(self, key, val):
        self.key = key
        self.val = val
    
    def __str__(self):
        return f"{self.key}/{self.val}"

def format_array(arr):
    return "[" + ", ".join(str(x) for x in arr) + "]"

def merge(arr, left, mid, right, step_file):
    leftArr = arr[left:mid+1]
    rightArr = arr[mid+1:right+1]

    i = j = 0
    k = left

    while i < len(leftArr) and j < len(rightArr):
        if leftArr[i].key <= rightArr[j].key:
            arr[k] = leftArr[i]
            i += 1
        else:
            arr[k] = rightArr[j]
            j += 1
        k += 1

    while i < len(leftArr):
        arr[k] = leftArr[i]
        i += 1
        k += 1

    while j < len(rightArr):
        arr[k] = rightArr[j]
        j += 1
        k += 1

    # Log the entire array state after this merge
    step_file.write(format_array(arr) + "\n")

def merge_sort_helper(arr, left, right, step_file):
    if left >= right:
        return
    mid = (left + right) // 2
    merge_sort_helper(arr, left, mid, step_file)
    merge_sort_helper(arr, mid + 1, right, step_file)
    merge(arr, left, mid, right, step_file)

def merge_sort(arr, step_file):
    if len(arr) < 2:
        return
    merge_sort_helper(arr, 0, len(arr) - 1, step_file)

def read_csv(filename, start_row, end_row):
    data = []
    with open(filename, 'r') as f:
        for idx, line in enumerate(f, 1):
            if idx < start_row:
                continue
            if idx > end_row:
                break
            parts = line.strip().split(',', 1)  # Split into max 2 parts
            if len(parts) == 2:
                try:
                    key = int(parts[0])
                    val = parts[1].strip()
                    data.append(Data(key, val))
                except ValueError:
                    print(f"Skipping malformed integer at line {idx}")
    return data

def main():
    if len(sys.argv) < 4:
        print("Usage: python merge_sort_step.py <csv_file> <start_row> <end_row>")
        sys.exit(1)

    csv_file = sys.argv[1]
    start_row = int(sys.argv[2])
    end_row = int(sys.argv[3])

    arr = read_csv(csv_file, start_row, end_row)

    if not arr:
        print("No data read from the CSV file within the specified rows.")
        sys.exit(1)

    out_file = f"merge_sort_step_{start_row}_{end_row}.txt"
    with open(out_file, 'w') as step_file:
        step_file.write("# Merge sort steps and sorted data\n")
        step_file.write("Original Array:," + format_array(arr) + "\n")
        merge_sort(arr, step_file)

    print(f"Merge steps saved to {out_file}")

if __name__ == "__main__":
    main()
