


public class Basket {
	
	
	private Reservation[] r;
	
	
	
	public Basket(){
		
		//DONT THINK IM DOING THIS CORRECTLY
		this.r = new Reservation[0];
	}
	
	public Reservation[] getProducts() { 
		
		//if empty
		
		
		
			if (this.r.length == 0) {
				return new Reservation[0];
			}
			
		Reservation[] shallowCopyRez = new Reservation[r.length]; 
		for (int j = 0; j< this.r.length; j++) {
			shallowCopyRez[j] = this.r[j];
		}
		return shallowCopyRez;
	}
	
	public int add(Reservation x) {
		Reservation[] newArray = new Reservation[r.length+1];
		
		//copy all elements for r into newArray, then set r to newArray.
		
		//need to create special case if r is empty
		if (this.r.length == 0) {
			newArray[0] = x; //populates incoming element into new array
			this.r = newArray; //old array equal to newArray
			return this.r.length; 
		}
		
		
		//copy elements
		for (int i = 0; i< r.length; i++) {
			newArray[i] = r[i];
		}
		
		//last element of new array is Reservation x
		newArray[r.length] = x; 
		
		//reassigning values in r to new array
		this.r = newArray;
		
		//returns new amount of reservations in basket
		return this.r.length;
		
	}
	
	//REEXAMINE THIS
	public boolean remove(Reservation y) {
		System.out.println("N RESERVATIONS BEFORE REMOVING"+r.length);
		if (this.r[0] == null) {
			return false;
		}
		
		//declare new array thats length one smaller
		//when you copy and if not the one you want to remove then 
		
		Reservation[] myNewArray = new Reservation[r.length-1];
		
		int validResCounter = 0;
		for (int h = 0; (h<r.length); h++) {
			if (r[h] != y) {
				
				myNewArray[validResCounter] = r[h];
				validResCounter++;
			}
		}
		
		this.r = myNewArray;

		
		return true;
		
		
		
		
		
		
	}
	
	
	
	public void clear() {
		//for (int i = 0; i< this.r.length; i++) {
		//	this.r = null; 
		//}
		this.r = new Reservation[0];
	}
	
	public int getNumOfReservations() {
		int numReservationsInBasket = this.r.length;
		return numReservationsInBasket;
	}
	
	public int getTotalCost() {
		
		Reservation[] x = getProducts();
		
		int totalCostAllRezInBasket = 0;
		
		for (int i = 0; i< x.length; i++) {
			totalCostAllRezInBasket += this.r[i].getCost();
		}
		return totalCostAllRezInBasket;
	}


	
	
}
