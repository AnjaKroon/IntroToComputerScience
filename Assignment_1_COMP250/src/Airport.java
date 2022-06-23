
public class Airport {
	int airportLocX;
	int airportLocY;
	int airFeeCents;
	
	public Airport(int posX, int posY, int feeCents) {
		this.airportLocX = posX;
		this.airportLocY = posY;
		this.airFeeCents = feeCents;
	}
	
	public int getFees() {
		return this.airFeeCents;
	}
	
	
	
	public static int getDistance(Airport a, Airport b) {
		long ax = a.airportLocX;
		long ay = a.airportLocY;
		long bx = b.airportLocX;
		long by = b.airportLocY;
		
		long xSquared = Math.abs((bx-ax)*(bx-ax));
		
		long ySquared = Math.abs((by-ay)*(by-ay));		
		
		double dist = Math.sqrt(xSquared + ySquared);
		
		
		int roundedDist = (int) Math.ceil(dist);
		
		return roundedDist;
	}
		
		
	

}
