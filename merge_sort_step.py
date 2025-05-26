import sys

def merge(arr, left, mid, right, step_file):
    leftArr = arr[left:mid+1]
    rightArr = arr[mid+1:right+1]

    msg = f"Merging subarrays [{left}-{mid}] and [{mid+1}-{right}]\n"
    print(msg, end='')
    step_file.write(msg)

    msg = f"Left: {leftArr}\n"
    print(msg, end='')
    step_file.write(msg)

    msg = f"Right: {rightArr}\n"
    print(msg, end='')
    step_file.write(msg)

    i = j = 0
    k = left

    while i < len(leftArr) and j < len(rightArr):
        if leftArr[i] <= rightArr[j]:
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

    msg = f"Merged: {arr[left:right+1]}\n\n"
    print(msg, end='')
    step_file.write(msg)

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
        print("Usage: python merge_sort_step.py <csv_file> <start_row> <end_row>")
        sys.exit(1)

    csv_file = sys.argv[1]
    start_row = int(sys.argv[2])
    end_row = int(sys.argv[3])

    arr = read_csv_first_column(csv_file, start_row, end_row)

    if not arr:
        print("No data read from the CSV file within the specified rows.")
        sys.exit(1)

    out_file = f"merge_sort_step_{start_row}_{end_row}.txt"
    with open(out_file, 'w') as step_file:
        merge_sort(arr, step_file)

    print(f"\nSorted array: {' '.join(map(str, arr))}")
    print(f"Merge steps saved to {out_file}")

if __name__ == "__main__":
    main()
