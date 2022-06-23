
public class Room {
	String roomType;
	int priceInCents;
	boolean isAvailable;
	
	public Room(String a) {
		a.toLowerCase();
		this.roomType = a;
		if (a.equals("double")) {
			this.priceInCents = 100*90; 
			this.isAvailable = true;
		}
		else if (a.equals("queen")) {
			this.priceInCents = 100*110;
			this.isAvailable = true;
		}
		else if (a.equals("king")) {
			this.priceInCents = 100*150;
			this.isAvailable = true;
		}
		else {
			throw new IllegalArgumentException("The room type you have submitted is invalid.");
		}
		
	}
	
	public Room(Room r) {
		this.roomType = r.roomType;
		this.priceInCents = r.priceInCents;
		this.isAvailable = r.isAvailable;
	}
	
	public String getType() {
		
		return this.roomType;
	}
	
	public int getPrice() {
		return this.priceInCents;
	}
	
	public void changeAvailability() {
		if (this.isAvailable){
			this.isAvailable = false;
		}
		else {
			this.isAvailable = true;
		}
	}

	
	public static Room findAvailableRoom(Room[] q, String rType) {
		System.out.println("finding available room");
		for(int x = 0; x< q.length; x++) {
			System.out.println("ROOM ARRAY, INDEX "+x);
			System.out.println("ROOM TYPE"+q[x].getType());
			if (q[x].getType().equals(rType)) { //comparing string to string
				System.out.println("rooms compared"+q[x].getType()+rType);
				if (q[x].isAvailable){
					System.out.println("isAvailable?"+q[x].isAvailable);
					 return q[x];
				}
			}
			
		}
		return null;
		}
	
	public static boolean makeRoomAvailable(Room[] abc, String rType) {
		
		for(int x = 0; x< abc.length; x++) {
			if (abc[x].getType().equals(rType)){ 
				if ((!abc[x].isAvailable)){
					abc[x].isAvailable = true;
					return abc[x].isAvailable;
				}
			}
				
		}
		
		return false;
	}
}
	
