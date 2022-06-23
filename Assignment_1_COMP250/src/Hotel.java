
public class Hotel {
	String nameHotel;
	Room[] arrayRoomsHotel;
	
	public Hotel(String s, Room[] r) {
		this.nameHotel = s;
		
		
		this.arrayRoomsHotel = new Room[r.length];
		for (int i = 0; i < r.length; i++) {
			this.arrayRoomsHotel[i] = new Room(r[i]);
		}
	}
	
	public int reserveRoom(String rType) {
			System.out.println("reserving Room");
			Room reserveRoom = Room.findAvailableRoom(arrayRoomsHotel, rType);
			System.out.println("Room found is null"+(reserveRoom==null));
			if (reserveRoom != null) {
				System.out.println("reserved room is not null");
				reserveRoom.changeAvailability();
				return reserveRoom.getPrice();
			}
			else {
				System.out.println("reserved room is null!");
				throw new IllegalArgumentException();	
			}
			
	
		
		
	}
	
	public boolean cancelRoom(String rType) {
		return Room.makeRoomAvailable(arrayRoomsHotel, rType);
	}
}
