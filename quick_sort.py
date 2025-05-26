import csv

class Data:
    def __init__(self, key, val):
        self.key = key
        self.val = val

def quicksort(arr, low, high):
    if low < high:
        pi = partition(arr, low, high)
        quicksort(arr, low, pi - 1)
        quicksort(arr, pi + 1, high)

def partition(arr, low, high):
    pivot = arr[high].key
    i = low - 1
    for j in range(low, high):
        if arr[j].key <= pivot:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]
    arr[i+1], arr[high] = arr[high], arr[i+1]
    return i + 1

def read_csv(filename):
    data_list = []
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
    return data_list

def write_sorted_csv(filename, arr):
    with open(filename, 'w', newline='', encoding='utf-8') as csvfile:
        writer = csv.writer(csvfile)
        for d in arr:
            writer.writerow([d.key, d.val])

def main():
    input_file = 'dataset_sample_1000.csv'
    output_file = 'quick_sort_1000.csv'

    data_list = read_csv(input_file)
    if not data_list:
        print("No data found in the file.")
        return

    quicksort(data_list, 0, len(data_list) - 1)

    write_sorted_csv(output_file, data_list)

    print(f"Sorted data saved to {output_file}")
    print("Sorted array keys:", ' '.join(str(d.key) for d in data_list))

if __name__ == "__main__":
    main()
