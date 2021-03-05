import java.util.Arrays;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;

public class MaxHeapArray<T extends Comparable<? super T>> 
	implements MaxHeapInterface<T> {

	private T[] maxHeap; 	// Array of entries in the max heap
	private static final int DEFAULT_CAPACITY = 25;
	private int lastIndex;	// Index of the last entry in the max heap
	int numOfSwaps;			// Counts number of swaps in the creation of a max heap
	
	public MaxHeapArray()
	{
		this(DEFAULT_CAPACITY);
	} // end default constructor
	
	// This constructor creates a max heap array with a specified initial capacity
	public MaxHeapArray(int initialCapacity)
	{
		@SuppressWarnings("unchecked")
		T[] tempHeap = (T[]) new Comparable[initialCapacity + 1];
		maxHeap = tempHeap;
		lastIndex = 0;
		numOfSwaps = 0;
	} // end constructor
	
	// This constructor creates a max heap array by copying the contents of an array 
	// and repeatedly calling the reheap method
	public MaxHeapArray(T[] entriesArray)
	{
		this(entriesArray.length);
		
		for (int i = 0; i < entriesArray.length; i++) {
			maxHeap[i + 1] = entriesArray[i];
			lastIndex = lastIndex + 1;	//Does this work? EDIT: I think it does...
		}
		for (int i = lastIndex; i > 0; i--) {
			reheap(i);
		}
	} // end constructor
	
	public void add(T newEntry)
	{
		int newIndex = lastIndex + 1;
		int parentIndex = newIndex / 2;
		
		while ((parentIndex > 0) && newEntry.compareTo(maxHeap[parentIndex]) > 0) {
			maxHeap[newIndex] = maxHeap[parentIndex];
			newIndex = parentIndex;
			parentIndex = newIndex / 2;
			numOfSwaps = numOfSwaps + 1;
		}
		maxHeap[newIndex] = newEntry;
		lastIndex = lastIndex + 1;
		
		if (lastIndex == maxHeap.length) {
			maxHeap = Arrays.copyOf(maxHeap, maxHeap.length * 2);
		}
	} // end add
	
	public T removeMax()
	{
		T root = null;
		if (!isEmpty()) {
			root = maxHeap[1];
			maxHeap[1] = maxHeap[lastIndex];
			lastIndex--;
			reheap(1);
		}
		return root;
	} // end removeMax
	
	public T getMax()
	{
		T max = null;
		if (!isEmpty()) {
			max = maxHeap[1];
		}
		return max;
	} // end getMax
	
	public boolean isEmpty()
	{
		if (lastIndex == 0) {
			return true;
		}
		else {
			return false;
		}
	} // end isEmpty
	
	public int getSize()
	{
		return lastIndex;
	} // end getSize
	
	public void clear()
	{
		for (int i = lastIndex; i >= 0; i--) {
			maxHeap[i] = null;
			lastIndex = lastIndex - 1;
		}
	} // end clear
	
	// This method implements the downheap operation
	private void reheap(int rootIndex)
	{
		boolean done = false;
		T orphan = maxHeap[rootIndex];
		int leftChildIndex = 2 * rootIndex;
		
		while (!done && (leftChildIndex <= lastIndex)) {
			int largerChildIndex = leftChildIndex;
			int rightChildIndex = leftChildIndex + 1;
			if (rightChildIndex <= lastIndex &&
				maxHeap[rightChildIndex].compareTo(maxHeap[largerChildIndex]) > 0) {
				largerChildIndex = rightChildIndex;
			}
			if (orphan.compareTo(maxHeap[largerChildIndex]) < 0) {
				maxHeap[rootIndex] = maxHeap[largerChildIndex];
				rootIndex = largerChildIndex;
				leftChildIndex = 2 * rootIndex;
			}
			else {
				done = true;
			}
			numOfSwaps = numOfSwaps + 1;
		}
		maxHeap[rootIndex] = orphan;
	} // end reheap
	
	// This method returns the entry at a certain index
	public T getEntryAt(int index)
	{
		return maxHeap[index];
	} // end getEntryAt
	
	// This method returns the number of swaps in the creation of a max heap
	public int getNumberOfSwaps()
	{
		return numOfSwaps;
	} // end get NumberOfSwaps
	
	public static void main(String[] args) throws IOException {
		
		// Create output file
		File file = new File("/Users/maahedah/eclipse-workspace/CS 2400 Project 4/src/output.txt");
		file.createNewFile();
		PrintWriter output = new PrintWriter(file);
		
		//Sequential Method
		File sequentialFile = new File("/Users/maahedah/eclipse-workspace/CS 2400 Project 4/src/data.txt");
		Scanner sequentialScnr = new Scanner(sequentialFile);
		
		// Create a new MaxHeapArray object with initial capacity 101
		MaxHeapArray<Integer> sequential = new MaxHeapArray<Integer>(101);
		while (sequentialScnr.hasNextInt()) {
			sequential.add(sequentialScnr.nextInt());
		}
		// Print out first 10 values of the max heap and the number of swaps
		output.print("Heap built using sequential insertions: ");
		for (int i = 0; i < 10; i++) {
			output.print(sequential.getEntryAt(i + 1) + ",");
		}
		output.print("...");
		output.println("\nNumber of swaps in the heap creation: "
							+ sequential.getNumberOfSwaps());
		// Remove 10 values and print out the first 10 values of the new max heap
		for (int i = 0; i < 10; i++) {
			sequential.removeMax();
		}
		output.print("Heap after 10 removals: ");
		for (int i = 0; i < 10; i++) {
			output.print(sequential.getEntryAt(i + 1) + ",");
		}
		output.println("...\n");
		sequentialScnr.close();		// Close Scanner object
		
		// Optimal Method
		File optimalFile = new File("/Users/maahedah/eclipse-workspace/CS 2400 Project 4/src/data.txt");
		Scanner optimalScnr = new Scanner(optimalFile);
		
		// Fill entriesArray with int values from the input file
		Integer[] entriesArray = new Integer[100];
		for (int i = 0; optimalScnr.hasNextInt(); i++) {
			entriesArray[i] = optimalScnr.nextInt();
		}
		
		// Create a new MaxHeapArray object with entriesArray as a parameter
		MaxHeapArray<Integer> optimal = new MaxHeapArray<Integer>(entriesArray);
		
		// Print out first 10 values of the max heap and the number of swaps
		output.print("Heap built using optimal insertions: ");
		for (int i = 0; i < 10; i++) {
			output.print(optimal.getEntryAt(i + 1) + ",");
		}
		output.print("...");
		output.println("\nNumber of swaps in the heap creation: "
							+ optimal.getNumberOfSwaps());
		// Remove 10 values and print out the first 10 values of the new max heap
		for (int i = 0; i < 10; i++) {
			optimal.removeMax();
		}
		output.print("Heap after 10 removals: ");
		for (int i = 0; i < 10; i++) {
			output.print(optimal.getEntryAt(i + 1) + ",");
		}
		output.println("...");
		optimalScnr.close();		// Close Scanner object
		
		output.close();				// Close PrintWriter object
		
	} // end main
	
} // end MaxHeapArray
