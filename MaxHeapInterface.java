
public interface MaxHeapInterface<T extends Comparable<? super T>> {
	
	// This method adds a new entry to the max heap and performs the 
	// upheap operation
	public void add(T newEntry);
	
	// This method removes the max value in a max heap and performs the
	// downheap operation
	public T removeMax();
	
	// This method returns the entry which has the largest value in the heap
	public T getMax();
	
	// This method returns true if the heap is entry and false if it isn't
	public boolean isEmpty();
	
	// This method returns the current size of the max heap
	public int getSize();
	
	// This method sets all entries in the max heap to null
	public void clear();
}
