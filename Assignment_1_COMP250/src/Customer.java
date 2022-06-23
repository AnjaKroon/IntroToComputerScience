
public class Customer {
	String name;
	int customerBalanceInCents;
	Basket customerReservations;
	
	public Customer(String n, int i){
		
		this.name = n;
		this.customerBalanceInCents = i;
		customerReservations = new Basket();

		
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getBalance() {
		return this.customerBalanceInCents;
	}
	
	public Basket getBasket() {
		return this.customerReservations;
	}
	
	public int addFunds(int moneyToAdd) {
		if(moneyToAdd<0) {
			throw new IllegalArgumentException("Money to Add was negative. Must be positive.");
		}
		this.customerBalanceInCents += moneyToAdd;
		return this.customerBalanceInCents;
	}
	
	public int addToBasket(Reservation q) {
		
		
		if (q.reservationName().equals(this.name)) { 
			
			return customerReservations.add(q);
		}
		else {
			
			throw new IllegalArgumentException("Name of reservation does not match the name of the customer.");
		}
	}
	
	public int addToBasket(Hotel myH, String myRType, int myNNights, boolean breakfastYorN) {
		
		
		if (breakfastYorN == true) {
			
			BnBReservation x = new BnBReservation(this.name, myH, myRType, myNNights);
			return this.customerReservations.add(x);
		}
		
		else {
			HotelReservation x = new HotelReservation(this.name, myH, myRType, myNNights);
			return this.customerReservations.add(x);
		}
		
		
		
	}
	
	public int addToBasket(Airport myDepart, Airport myArrive) {
		
		Reservation newFlightRez = new FlightReservation(this.name, myDepart, myArrive);
		return customerReservations.add(newFlightRez);
	}
	
	
	
	public boolean removeFromBasket(Reservation y) {
		return this.customerReservations.remove(y); 
	}
	
	
	
	public int checkOut() {
		
		if (this.customerBalanceInCents < this.customerReservations.getTotalCost()) {
			throw new IllegalArgumentException("The customer's balance is not enough to cover the total cost of their basket");
		}
		
		this.customerBalanceInCents = this.customerBalanceInCents - this.customerReservations.getTotalCost(); 
		this.customerReservations.clear();
		return customerBalanceInCents; 
	}
	
	

}
