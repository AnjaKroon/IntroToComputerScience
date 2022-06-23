
public class BnBReservation extends HotelReservation{
	public BnBReservation(String myNameRez, Hotel myHotel, String myRoomType, int myNumNights) {
		super(myNameRez, myHotel, myRoomType, myNumNights); 
	}
	
	public int getCost() {
		int myCost = super.getCost() + ((10*100)*super.getNumOfNights()); 
		return myCost;
	}

}
