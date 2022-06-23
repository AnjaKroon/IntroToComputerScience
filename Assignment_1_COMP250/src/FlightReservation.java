
public class FlightReservation extends Reservation {

	private Airport placeDeparture;
	private Airport placeArrival;
	
	public FlightReservation(String nameRez, Airport departure, Airport arrival) {
		super(nameRez);
		if (departure.equals(arrival)) {
			throw new IllegalArgumentException("arrival airport equals departure airport");
		}
		else {
			this.placeDeparture = departure;
			this.placeArrival = arrival;
		}
	}

	public int getCost() {
		
		double airportDist = Airport.getDistance(placeDeparture, placeArrival);		
		
		double numGal = (double)(airportDist/167.52);
	
		double fuelCost = numGal*124;

		int airportFees= placeDeparture.getFees() + placeArrival.getFees();
		
		
		double costOfReservation = fuelCost + airportFees + 5375; 
		int costFlight = (int) Math.ceil(costOfReservation);
		return costFlight;
	}
	
	@Override
	public boolean equals(Object o) {

		
		if (o instanceof FlightReservation) {
			FlightReservation f = (FlightReservation) o;
			if (   (  (f.reservationName()).equals(this.reservationName()) 
					&& (  f.placeDeparture == this.placeDeparture)  
					&& (  f.placeArrival == this.placeArrival ) )){
				return true;
			}
		}
		return false;
	}


	
}
