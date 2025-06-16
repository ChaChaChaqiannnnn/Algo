import csv
import re
import sys

def read_csv(filename):
    data = []
    with open(filename, newline='') as csvfile:
        reader = csv.reader(csvfile)
        for row in reader:
            if len(row) == 2:
                try:
                    key = int(row[0].strip())
                    val = row[1].strip()
                    data.append((key, val))
                except ValueError:
                    pass
    return data

def binary_search(arr, target, out_file):
    left, right = 0, len(arr) - 1
    while left <= right:
        mid = left + (right - left) // 2
        out_file.write(f"{mid+1}: {arr[mid][0]}/{arr[mid][1]}\n")
        if arr[mid][0] == target:
            return mid
        elif arr[mid][0] < target:
            left = mid + 1
        else:
            right = mid - 1
    out_file.write("-1\n")
    return -1

def main():
    if len(sys.argv) < 3:
        print("Usage: python binary_search.py <sorted_csv> <target_integer>")
        return

    input_file = sys.argv[1]
    target = int(sys.argv[2])

    match = re.search(r'(\d+)', input_file)
    num_part = match.group(1) if match else ''
    output_file = f"binary_search_step_{num_part}.txt"

    arr = read_csv(input_file)
    if not arr:
        print("No data found in the file.")
        return

    with open(output_file, 'w') as out_file:
        binary_search(arr, target, out_file)

    print(f"Binary search steps saved to {output_file}")

if __name__ == "__main__":
    main()
