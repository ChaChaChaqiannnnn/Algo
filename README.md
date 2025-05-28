Project Overview
This project implements and analyzes sorting and searching algorithms, specifically Merge Sort, Quick Sort, and Binary Search. The algorithms are applied to a randomly generated dataset of integers and strings.

Algorithms Implemented
Merge Sort: A stable sorting algorithm that splits the dataset into smaller sublists, sorts them, and merges them.

Quick Sort: A non-stable sorting algorithm that uses the last element as a pivot to partition the dataset and recursively sorts the sublists.

Binary Search: A search algorithm used to find a target element in a sorted array by repeatedly dividing the search interval in half.

Dataset
Dataset: Randomized integers (unique, up to 1 billion) and strings.

A dataset of size 1 million elements is used for testing.

How to Run
1. Dataset Generation
To generate a dataset (e.g., 1 million elements):

bash
Copy
Edit
java DatasetGenerator 1000000
2. Merge Sort
Java:

bash
Copy
Edit
javac MergeSort.java
java MergeSort dataset_1000000.csv
Python:

bash
Copy
Edit
python3 merge_sort.py dataset_1000000.csv
3. Quick Sort
Java:

bash
Copy
Edit
javac QuickSort.java
java QuickSort dataset_1000000.csv
Python:

bash
Copy
Edit
python3 quick_sort.py dataset_1000000.csv
4. Binary Search
Java:

bash
Copy
Edit
javac BinarySearch.java
java BinarySearch dataset_1000000.csv 1000000
Python:

bash
Copy
Edit
python3 binary_search.py dataset_1000000.csv 1000000
Performance Analysis
The program measures the running time of each algorithm.

Performance is tested with datasets of varying sizes, and results are captured for:

Best, average, and worst-case scenarios for binary search.

Time and space complexity of merge sort and quick sort.

Conclusion
The best sorting algorithm is determined based on performance (runtime and space).

A comparison of AVL with array vs. linked structure is also discussed.

Files Included
Dataset Generator: Generates datasets of unique random integers and strings.

Sorting and Searching Algorithms: Java and Python implementations for Merge Sort, Quick Sort, and Binary Search.

Performance Logs: Logs the sorting steps and binary search path for analysis.
