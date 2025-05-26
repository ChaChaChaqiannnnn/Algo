import csv
import time

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

        # Merge by string value, stable sorting
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
    input_file = "dataset_1000000.csv"
    output_file = "merge_sort_1000000.csv"

    print("Reading input file...")
    data = read_csv(input_file)

    print("Sorting by string value...")
    start_time = time.time()

    merge_sort(data)

    end_time = time.time()
    elapsed = end_time - start_time
    print(f"Sorting completed in {elapsed:.3f} seconds.")

    print("Writing first 5 sorted rows to output file...")
    write_csv(data, output_file)

    print("Done.")

if __name__ == "__main__":
    main()
