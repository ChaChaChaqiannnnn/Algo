import sys

# Data class holding integer + string pair
class Data:
    def __init__(self, key, value):
        self.key = key
        self.value = value

    def __repr__(self):
        return f"{self.key}/{self.value}"

# Perform binary search on Data array and write the comparison steps
def binary_search(arr, target, writer):
    left = 0
    right = len(arr) - 1

    while left <= right:
        mid = left + (right - left) // 2

        # Write current comparison: row: key/value
        writer.write(f"{mid + 1}: {arr[mid].key}/{arr[mid].value}\n")

        if arr[mid].key == target:
            return mid  # Found, return index
        elif arr[mid].key < target:
            left = mid + 1
        else:
            right = mid - 1

    # Not found
    writer.write("-1\n")
    return -1

# Read the dataset (key/value pairs) from the provided CSV file
def read_data(filename):
    data = []
    with open(filename, 'r') as f:
        for line in f:
            parts = line.strip().split(",", 1)  # Split into key and value
            if len(parts) == 2:
                try:
                    key = int(parts[0].strip())
                    val = parts[1].strip()
                    data.append(Data(key, val))
                except ValueError:
                    print(f"Skipping malformed integer in line: {line}")
    return data

def main():
    if len(sys.argv) < 3:
        print("Usage: python3 binary_search_step.py <sorted_dataset_file> <target_integer>")
        return

    sorted_file = sys.argv[1]  # Expect the file name as an argument
    try:
        target = int(sys.argv[2])  # Target value for binary search
    except ValueError:
        print("Target must be an integer.")
        return

    try:
        # Read data from the CSV file (e.g., quick_sort_1000.csv)
        arr = read_data(sorted_file)
        if not arr:
            print("Dataset is empty.")
            return

        # Define output file name based on target value
        out_file = f"binary_search_step_{target}.txt"
        with open(out_file, 'w') as writer:
            found_index = binary_search(arr, target, writer)

        print(f"Binary search steps saved to {out_file}")

    except IOError as e:
        print(f"File error: {e}")

if __name__ == "__main__":
    main()
