package COMP250_A4_W2020;

//package FinalProject_Template;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import java.util.function.Consumer;


public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
  // num of entries to the table
  private int numEntries;
  // num of buckets 
  private int numBuckets;
  // load factor needed to check for rehashing 
  private static final double MAX_LOAD_FACTOR = 0.75;
  // ArrayList of buckets. Each bucket is a LinkedList of HashPair
  private ArrayList<LinkedList<HashPair<K,V>>> buckets; 

	/*
  public static void main(String[] args) {
		MyHashTable tester = new MyHashTable(7);
		
		int temp1 = 3;
		String temp2 = "Katty";
		String temp3 = "Jacob";
		int temp4 = 5;
		String temp5 = "Manny";
		int temp6 = 6;
		String temp7 = "David";
		int temp8 = 4;
		String temp9 = "James";
		int temp10 = 8;
		String temp11 = "Kelly";
		int temp12 = 2;
		String temp13 = "Dave";
		
		//PUT->WORKS
			tester.put(temp1, temp2); //Katty
			tester.put(temp4, temp5); //Manny
			tester.put(temp6, temp7); //David
			tester.put(temp8, temp9); //James
			tester.put(temp10, temp11); //Kelly
			tester.put(temp12, temp13); //Dave
			tester.put(temp1, temp3); //Jacob replacing Katty


		//GET->WORKS
			//System.out.println(tester.get(3)); //returns null bc doesnt exist
			//System.out.println(tester.get(6));
			//System.out.println(tester.get(4));
			//System.out.println(tester.get(17)); //out of bounds

		//REMOVE->WORKS
			System.out.println(tester.remove(3));
			System.out.println(tester.remove(7)); //should return null bc doesnt exist
		
		//Rehash-> WORKS
			//tester.rehash();
			
		//KEYS->WORKS
			System.out.println(tester.keys());
		
		//VALUES->WORKS
			System.out.println(tester.values());

		//Fast sort-> WORKS
			System.out.println(fastSort(tester));



	}
	*/
  
  // constructor
  public MyHashTable(int initialCapacity) {
      // ADD YOUR CODE BELOW THIS
  	this.numEntries = 0;
  	this.numBuckets = initialCapacity;

		if (this.numBuckets <= 0){
			this.numBuckets = 1;
			this.buckets = new ArrayList<LinkedList<HashPair<K,V>>>(1);
		}else{
			this.buckets = new ArrayList<LinkedList<HashPair<K,V>>>(initialCapacity);
		}

      //System.out.println(initialCapacity + "is InitialCapacity");
      for(int i = 0; i < this.numBuckets; i++){ 
        this.buckets.add(new LinkedList<HashPair<K,V>>());
      }
      //ADD YOUR CODE ABOVE THIS
  }
  
  public int size() {
      return this.numEntries;
  }
  
  public boolean isEmpty() {
      return this.numEntries == 0;
  }
  
  public int numBuckets() {
      return this.numBuckets;
  }
  
  /**
   * Returns the buckets variable. Useful for testing  purposes.
   */
  public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
      return this.buckets;
  }
  
  /**
   * Given a key, return the bucket position for the key. 
   */
  public int hashFunction(K key) {
  	//System.out.println("buckets is " + numBuckets);
      int hashValue = Math.abs(key.hashCode())%this.numBuckets;
      return hashValue;
  }
  
  /**
   * Takes a key and a value as input and adds the corresponding HashPair
   * to this HashTable. Expected average run time  O(1)
   */
  public V put(K key, V value) {
  	
  	//System.out.println("----------------------------------------------");
  	//System.out.println("Put: " + key + " " + value);

  	if (value == null) {
  		return null;
  	}
		int hashID = hashFunction(key);
  	//System.out.println(hashID + ": HashID");
  	
  	
  	//Does the key already exist in the hashTable? if yes, then replace value, return old value
  	if (get(key) != null) { 
  		V oldValue = get(key); //temporarily store the old value you wish to replace
  		LinkedList<HashPair<K,V>> myList = buckets.get(hashID); //get the preexisting bucket
  		//System.out.println("bucket number " + hashID + " now has size " + myList.size());

  		for(HashPair<K,V> p : myList) {
  			if (p.getKey().equals(key)) {
  				p.setValue(value);
  				return oldValue;
  			}
  		}
  	
  	}
  	else { //If not, add it to the appropriate bucket
  		HashPair<K,V> toAdd = new HashPair<K,V>(key,value);
  		LinkedList<HashPair<K, V>> LLofHP = buckets.get(hashID);
  		numEntries++;
  		LLofHP.add(toAdd);
  	}

		//If needed rehash
		if (numBuckets != 0) { //to avoid arithmetic exception
			if (((double)numEntries / (double)numBuckets) > MAX_LOAD_FACTOR)  rehash();
		}


  	return null;
      
      //  ADD YOUR CODE ABOVE HERE
  }
  
  
  /**
   * Get the value corresponding to key. Expected average runtime O(1)
   */
  
  public V get(K key) {  //get is being passed in as date, correct
      //ADD YOUR CODE BELOW HERE
		//System.out.println("----------------------------------------------");
		//System.out.println("Get: " + key );
  	int hashID = hashFunction(key);
  	LinkedList<HashPair<K,V>> box = buckets.get(hashID);
  	for (HashPair<K,V> i : box) {
  		if ((i.getKey() != null)&& (i.getKey().equals(key))) return i.getValue(); //changed the == to .equals and now it compiles
  	}
  	//getting to this point
  	//System.out.println("returning null on item " + key);
  	return null;
  	
      //ADD YOUR CODE ABOVE HERE
  }
  
  /**
   * Remove the HashPair corresponding to key . Expected average runtime O(1) 
   */
  public V remove(K key) {
      //ADD YOUR CODE BELOW HERE
		//System.out.println("----------------------------------------------");
		//System.out.println("Remove: " + key );
  	//Case 1: Empty, then return null
      if (isEmpty()) return null;
      
      //If the key cannot be found
      LinkedList<HashPair<K,V>> theList = buckets.get(hashFunction(key));
      
      HashPair<K,V> toRemove = new HashPair<K,V>(key, get(key));

      for (HashPair<K,V> e : theList) {
      	if (e.getValue().equals(toRemove.getValue())) {
      		
      		//System.out.println("removing " + key);
         	 	//subtract number of entries
             numEntries--;
             //Value to remove
             V valRemove = toRemove.getValue();
             //call remove on the Linked List to remove it from the list
             theList.remove(e);
             //System.out.println("buckets is " + buckets);
             return valRemove;
      	}

      }
      return null;
  	
      //ADD YOUR CODE ABOVE HERE
  }
  
  
  /** 
   * Method to double the size of the hashtable if load factor increases
   * beyond MAX_LOAD_FACTOR.
   * Made public for ease of testing.
   * Expected average runtime is O(m), where m is the number of buckets
   */
  public void rehash() {
      //ADD YOUR CODE BELOW HERE
		//System.out.println("----------------------------------------------");
		//System.out.println("Rehashing");
  	int resizeTo = this.numBuckets * 2;
  	V val;
  	
  	//create new table with correct number of buckets
  	MyHashTable<K,V> newTable = new MyHashTable<K,V>(resizeTo);

  	//iterating this way through the HashTable should be on average O(n)
  	//for each bucket
  	for(int i =0; i < this.numBuckets; i++){
  	      LinkedList<HashPair<K,V>> b  = buckets.get(i);
  	      //for each hash Pair in bucket
  	      for(HashPair<K,V> p : b){                                    
  	        if(p.getKey() != null){ //O(1)
  	          val = get(p.getKey());   //O(1)
  	          if(val!= null){        //O(1)
  	            newTable.put(p.getKey(), val);   //O(1)
  	          }
  	        }
  	      } 
  	    }
  	
  	this.buckets = newTable.buckets;
  	this.numEntries = newTable.numEntries; //MUST do this, will reset numEntries in put
  	this.numBuckets = newTable.numBuckets;

      //ADD YOUR CODE ABOVE HERE
  }
  
  
  /**
   * Return a list of all the keys present in this hashtable.
   * Expected average runtime is O(m), where m is the number of buckets
   */
  
  public ArrayList<K> keys() {
      //ADD YOUR CODE BELOW HERE
		//System.out.println("----------------------------------------------");
		//System.out.println("Getting keys");
  	//arrayList to store Keys, initialize size as number of Entries
  	ArrayList<K> theKeys = new ArrayList<K>(this.numEntries); 

  	for (LinkedList<HashPair<K,V>> b : buckets) {
  		for (HashPair<K,V> p : b) {
  			theKeys.add(p.getKey());
  		}
  	}
  	
  	return theKeys;
  	
      //ADD YOUR CODE ABOVE HERE
  }
  
  /**
   * Returns an ArrayList of unique values present in this hashtable.
   * Expected average runtime is O(m) where m is the number of buckets
   */
  public ArrayList<V> values() {
      //ADD CODE BELOW HERE

  	ArrayList<V> theValues = new ArrayList<V>();
		MyHashTable<V,K> myTable = new MyHashTable<V,K>(10);

		for (LinkedList<HashPair<K,V>> b : buckets) {
			for (HashPair<K,V> p : b) {
				if (myTable.get(p.getValue()) == null){
					myTable.put(p.getValue(), p.getKey());
					theValues.add(p.getValue());
				}
			}
		}
  	return theValues;
  	
      //ADD CODE ABOVE HERE
  }
  
  
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2), where n is the number 
	 * of pairs in the map. 
	 */
  public static <K, V extends Comparable<V>> ArrayList<K> slowSort (MyHashTable<K, V> results) {
		//System.out.println("----------------------------------------------");
		//System.out.println("Slowsort being called");


      ArrayList<K> sortedResults = new ArrayList<>();


      for (HashPair<K, V> entry : results) {
			V element = entry.getValue();
			K toAdd = entry.getKey();
			int i = sortedResults.size() - 1;
			V toCompare = null;
      	while (i >= 0) {
      		toCompare = results.get(sortedResults.get(i));
      		if (element.compareTo(toCompare) <= 0 )
      			break;
      		i--;
      	}
      	sortedResults.add(i+1, toAdd);
      }



      return sortedResults;
  }
  
  
	/*
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to.
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */


  public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
      //ADD CODE BELOW HERE
		//System.out.println("----------------------------------------------");
		//System.out.println("Fastsort");
  	ArrayList<K> theKeys = results.keys();
  	//System.out.println("fastSort: keys is " + theKeys);
		return mergeSort(theKeys, results);
		
      //ADD CODE ABOVE HERE
  }


  /*
  private static <K, V extends Comparable<V>>  void merge(ArrayList<K> arr, int l, int m, int r, MyHashTable<K,V> results )
	{
		System.out.println("----------------------------------------------");
		System.out.println("merging");

		//find sizes of two subarrays to be merged
		int n1 = m - l + 1;
		int n2 = r - m;

		//create two temp arrays
		ArrayList<K> L = new ArrayList<K>(n1);
		ArrayList<K> R = new ArrayList<K>(n2);

		//Copy data to temp arrays
		for (int i=0; i<n1; i++){ //CHANGED
			L.add(i, arr.get(l+i)); //used to add this at an index
			//L[i] = arr[l + i];
		}
		for (int j=0; j<n2; j++){ //CHANGED
			R.add(j, arr.get(m+1+j));
			//R.set(j, arr.get(m + 1 + j));
		}

		//MERGING THE TEMP ARRAYS

		// Initial indexes of first and second subarrays
		int i = 0, j = 0;

		// Initial index of merged subarray array
		int k = l;
		while (i < n1 && j < n2)
		{
			//if L[i] less than R[j] ===== if R[j] greater than L[i]
			K lKey = L.get(i); //get the key at index i
			V lVal = results.get(lKey); //get the value for that key from the hashTable

			K rKey = R.get(j); //get the key at index j
			V rVal = results.get(rKey); //get the value for that key from the HashTable
			//MUST BE SORTED IN DESCENDING ORDER
			//if the value at index i in L is less than or equal to the value at index j in R
			if (lVal.compareTo(rVal) >= 0) { //double check
				arr.set(k, lKey); //changed set to add
				i++;
			}
			else {
				arr.set(k, rKey); //changed set to add
				j++;
			}
			k++;
		}

		//Copy remaining elements of L[] if any
		while (i < n1)
		{
			arr.set(k, L.get(i)); //changed set to add
			i++;
			k++;
		}

		// Copy remaining elements of R[] if any
		while (j < n2) {
			arr.set(k, R.get(j));
			j++;
			k++;
		}
	}
	//main function that sorts the array and calls merge
	private static <K, V extends Comparable<V>> void sort(ArrayList<K> arr, int l, int r, MyHashTable<K,V> rez)
	{
		if (l < r) {
			int m = (l+r)/2; //find the middle point
			//sort first half
			sort(arr, l, m , rez);
			//sort second half
			sort(arr , m+1, r , rez);
			//merge the sorted halves
			merge(arr, l, m, r, rez);
		}
	}
	*/

	private static <K, V extends Comparable<V>> ArrayList<K> mergeSort(ArrayList<K> arr, MyHashTable<K, V> results){
		if (arr.size() == 1 || arr.size() == 0){
			return arr;
		}
		else{

			int mid = (arr.size() -1)/2;
			ArrayList<K> list1 = new ArrayList<K>(); //left
			ArrayList<K> list2 = new ArrayList<K>(); //right
			for (int i = 0; i<= mid; i++){ //copy left until mid
				//System.out.println("mergeSort: leftList copying at index "  +i); //yes, good, acccessed 10 times
				list1.add(arr.get(i)); //populate left list with indicies 0-9 from arr
			}

			for (int i = mid +1; i<=arr.size()-1; i++){ //copy starting at mid+1 until size-1
				//System.out.println("mergeSort rightList copying at index" + i);
				list2.add(arr.get(i)); //populate right list with indicies 10-18
			}

			list1 = mergeSort(list1, results);

			list2 = mergeSort(list2, results);

			return merge(list1, list2, results);
		}

	}
	private static <K, V extends Comparable<V>>  ArrayList<K> merge(ArrayList<K> list1, ArrayList<K> list2, MyHashTable<K, V> results){
		ArrayList<K> list = new ArrayList<K>();
		while (!list1.isEmpty() && !list2.isEmpty()){
			//System.out.println("neither list is yet empty");
			K iLeftKey = list1.get(0); //get first key in leftList
			V leftValue = results.get(iLeftKey); //corresponding first value

			K iRightKey = list2.get(0); //get first key in rightList
			V rightValue = results.get(iRightKey); //corresponding firstValue

			if (leftValue.compareTo(rightValue) > 0){
				list.add(list1.remove(0)); //list1.removeFirst()
				//System.out.println("adding to list, has key" + iLeftKey);
			}
			else{

				list.add(list2.remove(0));//list2.removeFirst()
				//System.out.println("adding to list, has key" + iRightKey);
			}
		}
		while (!list1.isEmpty()){
			//System.out.println("adding to list" + list1.get(0));
			list.add(list1.remove(0));//list1.removeFirst()

		}
		while (!list2.isEmpty()){
			//System.out.println("adding to list" + list2.get(0));
			list.add(list2.remove(0));//list2.removeFirst()

		}
		return list;
	}

	@Override
  public MyHashIterator iterator() {
      return new MyHashIterator();
  }

	private class MyHashIterator implements Iterator<HashPair<K,V>> {
      //ADD YOUR CODE BELOW HERE
  	LinkedList<HashPair<K,V>> myList;
      //ADD YOUR CODE ABOVE HERE
  	
  	/**
  	 * Expected average runtime is O(m) where m is the number of buckets
  	 */
      private MyHashIterator() { //I don't think this is the correct time complexity
          //ADD YOUR CODE BELOW HERE
      	//initialize linked list
      	this.myList = new LinkedList<HashPair<K,V>>();
      	//if its not empty
      	if (numBuckets != 0) {
      		//for each bucket in the hash table
          	for (int i = 0; i< numBuckets; i++) { //going thru all buckets
          		//for each HashPair in the bucket i
          		for (HashPair<K,V> x : buckets.get(i)) { //going thru all hashPairs in bucket
          			//if its not null
          			if (x.getKey() != null) {
          				//then add to the list
          				myList.add(x);
          			}
          		}
          	}
      	}
          //ADD YOUR CODE ABOVE HERE
      }
      
      @Override
      /**
       * Expected average runtime is O(1)
       */
      public boolean hasNext() { //But this will only check if the linked list is empty not if you are actually at the last element in the list
          //ADD YOUR CODE BELOW HERE
      	//when list is not empty, method will return true, yes bc that means it has a next
      	//when list is empty, method will return false, yes bc means it has no next
      	return !myList.isEmpty();
          //ADD YOUR CODE ABOVE HERE
      }
      @Override
      /**
       * Expected average runtime is O(1)
       */
      public HashPair<K,V> next() {
          //ADD YOUR CODE BELOW HERE
      	//null pointer checking
      	if (isEmpty()) return null;
      	//returns the next element of the linked list, built in function of linked lists
      	return myList.removeFirst();
          //ADD YOUR CODE ABOVE HERE
      }
      
  }
}
