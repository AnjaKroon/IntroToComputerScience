
public abstract class Reservation {
		
		private String nameReservation;
		
		public Reservation(String n) {
			this.nameReservation = n;
		}
		
		public final String reservationName() {
			return this.nameReservation;
		}
		
		public abstract int getCost(); 
		public abstract boolean equals(Object o);
		
}
