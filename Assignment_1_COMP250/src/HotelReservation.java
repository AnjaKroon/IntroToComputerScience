
public class HotelReservation extends Reservation {
	Hotel rezLoc;
	String roomType;
	int numberNights;
	int priceInCentsOneNight; 
	
	public HotelReservation(String rezname, Hotel h, String t, int numNights) {
		super(rezname);
		this.rezLoc = h;
		this.roomType = t;
		this.numberNights = numNights;
		
		
		this.priceInCentsOneNight = h.reserveRoom(t);
		
		
		
	}
	
	public int getNumOfNights() {
		return this.numberNights;
	}
	
	public int getCost() {
		return this.priceInCentsOneNight*this.numberNights; 
	}
	
	@Override
	public boolean equals(Object o) {
			System.out.println(o.getClass());
			if (o instanceof Reservation){ 
				HotelReservation a = ((HotelReservation)o);
				
					if
					(a.reservationName().equals(this.reservationName())   
					&& a.rezLoc.equals(this.rezLoc)  
					&& a.roomType.equals(this.roomType)
					&& a.numberNights == this.numberNights 
					&& a.priceInCentsOneNight == this.priceInCentsOneNight
					)
					{
						return true;
					}
			}return false;
		
		
	}
}
