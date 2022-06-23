import java.util.Arrays;
import java.util.Random;

public class TrainLine {

	private TrainStation leftTerminus;
	private TrainStation rightTerminus;
	private String lineName;
	private boolean goingRight;
	public TrainStation[] lineMap;
	public static Random rand;

	
	public static void main(String [] args) {
		TrainStation A1 = new TrainStation("1. BOOM");
		TrainStation A2 = new TrainStation("3. AOOM");
		TrainStation A3 = new TrainStation("2. KABOOM");
		TrainStation A4 = new TrainStation("1. NOORJAM");
		
		TrainStation[] linemap = {A1, A2, A3, A4};
		TrainLine tt = new TrainLine(linemap, "NONONONONO!", true);
		System.out.println("the initial stuff " + tt);
		tt.swap(A2, A3);
		System.out.println("the swapped stuff " + tt);
	}
	
	public TrainLine(TrainStation leftTerminus, TrainStation rightTerminus, String name, boolean goingRight) {
		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		this.lineMap = this.getLineArray();
	}

	public TrainLine(TrainStation[] stationList, String name, boolean goingRight)
	/*
	 * Constructor for TrainStation input: stationList - An array of TrainStation
	 * containing the stations to be placed in the line name - Name of the line
	 * goingRight - boolean indicating the direction of travel
	 */
	{
		TrainStation leftT = stationList[0];
		TrainStation rightT = stationList[stationList.length - 1];

		stationList[0].setRight(stationList[stationList.length - 1]);
		stationList[stationList.length - 1].setLeft(stationList[0]);

		this.leftTerminus = stationList[0];
		this.rightTerminus = stationList[stationList.length - 1];
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		for (int i = 1; i < stationList.length - 1; i++) {
			this.addStation(stationList[i]);
		}

		this.lineMap = this.getLineArray();
	}

	public TrainLine(String[] stationNames, String name,
			boolean goingRight) {/*
									 * Constructor for TrainStation. input: stationNames - An array of String
									 * containing the name of the stations to be placed in the line name - Name of
									 * the line goingRight - boolean indicating the direction of travel
									 */
		TrainStation leftTerminus = new TrainStation(stationNames[0]);
		TrainStation rightTerminus = new TrainStation(stationNames[stationNames.length - 1]);

		leftTerminus.setRight(rightTerminus);
		rightTerminus.setLeft(leftTerminus);

		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		for (int i = 1; i < stationNames.length - 1; i++) {
			this.addStation(new TrainStation(stationNames[i]));
		}

		this.lineMap = this.getLineArray();

	}

	public void addStation(TrainStation stationToAdd) {
		TrainStation rTer = this.rightTerminus;
		TrainStation beforeTer = rTer.getLeft();
		rTer.setLeft(stationToAdd);
		stationToAdd.setRight(rTer);
		beforeTer.setRight(stationToAdd);
		stationToAdd.setLeft(beforeTer);

		stationToAdd.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}

	public String getName() {
		return this.lineName;
	}

	public int getSize() {
		int len = 0;
		for (TrainStation cur = this.getLeftTerminus(); cur != null; cur = cur.getRight()) {
			len++;
		}
		return len;
		
	}

	public void reverseDirection() {
		this.goingRight = !this.goingRight;
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation travelOneStation(TrainStation current, TrainStation previous) throws StationNotFoundException{
		try {
			findStation(current.getName());
		}
		catch (StationNotFoundException e){
			throw new StationNotFoundException("Station not found!");
		}
		
		
		//if you are moving between lines
		if(current.hasConnection) {
			if ((current.getTransferStation() != previous) ) { 
				previous = current;
				return current.getTransferStation();
			} //you have already moved between lines
			else {
				return getNext(current);
			}
		}	
		//Normal case
		return this.getNext(current);
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation getNext(TrainStation station) throws StationNotFoundException {
		
		//ERROR CHECKING
		if (station.getLine() != this) {
			throw new StationNotFoundException("Station is not found!");
		}
		
		//IF RIGHT TERMINAL YOU HAVE TO CHANGE DIRECTION, INCLUDES TRANSFER EDGE CASE FROM GET NEXT, THE ONE WHERE IT HAS JUST COMPELTED THE TRANSFER
		if (station == this.rightTerminus) {
			if (this.goingRight) {
				this.reverseDirection();
			}
			return station.getLeft();
		}
		
		//IF LEFT TERMINAL YOU ALSO HAVE TO CHANGE DIRECTION, CONSIDERES TRANSFER EDGE CASE FROM GET NEXT, ONE WHERE TRANSFER JUST COMPLETED
		else if (station == this.leftTerminus) {
			if(! this.goingRight) {
				this.reverseDirection();
			}
			return station.getRight();
		}
		
		//NORMAL GOING RIGHT
		else if (station.getLine().goingRight) {
			return station.getRight();
		}
		//NORMAL GOING LEFT
		return station.getLeft();
		
	}

	
	
	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation findStation(String name) throws StationNotFoundException {
		
		TrainStation c = this.getLeftTerminus();
		
		c = this.leftTerminus;
		
		while (true) {
			
			if (c.getName().contentEquals(name)) {
				return c;
			}
			if (c.getRight() == null) {
				throw new StationNotFoundException ("Station not found");
			}
			c = c.getRight();
		}
	}

	

	public void sortLine() {
		
		boolean cont = true;
		int counter = 0;
		while (cont) {
			cont = false;
			counter++;
			TrainStation c = this.getLeftTerminus();
			c = this.leftTerminus;
			for (int i = 0; i< this.getSize()- counter; i++) { //ROMAN CHANGED THIS TO GET SIZE. I THINK IT WORKS TOO
					
						String cName = c.getName();
						System.out.println("c is " + c);
						String cRName = c.getRight().getName();
				if (cName.compareTo(cRName) >0) {
					swap(c, c.getRight()); //PROBABLY AN ERROR IN SWAP
							System.out.println("left the swap");
					c = c.getLeft();
					cont = true;
				} c = c.getRight();
				
			}
		}
		//UPDATING LINEMAP
		this.lineMap = this.getLineArray();
		
	}
	
	public void swap(TrainStation a, TrainStation b) { 
		System.out.println("line map is " + Arrays.toString(lineMap));
		
		//CHECK IF THEY ARE SAME
		if (a.equals(b)) return;
	
		//SWAPPING
			System.out.println("aLeft is " + a.getLeft());
	        //WHEN LEFT MOST
		    if (a.getLeft() == null) {
		    	a.setNonTerminal();
		    	this.leftTerminus = b;
		    	TrainStation bTemp = b.getRight();
		    	
		    	a.setLeft(a.getRight());
		    	System.out.println("a has new left --> " + a.getLeft().getName() + " with reference " + a.getLeft());
		    	
		    	a.setRight(a.getRight().getRight());
		    	System.out.println("a has new right --> " + a.getRight().getName() + " with reference " + a.getRight());
		    	
		    	b.setRight(b.getLeft());
		    	System.out.println("b has new right --> " + b.getRight().getName() + " with reference " + b.getRight());
		    	
		    	b.setLeftTerminal();
		    	b.setLeft(null); //b.getPrev -> left terminal
		    	bTemp.setLeft(a);
		    }
		    
	
		    //WHEN RIGHT MOST
		    else if (b.getRight() == null) {
		    	b.setNonTerminal();
		    	this.rightTerminus = a;
		    	TrainStation aTemp = a.getLeft();
		    	
		    	b.setRight(b.getLeft());
			    	System.out.println("b has new right --> " + b.getRight().getName() + " with reference " + b.getRight());
		    	b.setLeft(b.getLeft().getLeft());
			    	System.out.println("b has new left --> " + b.getLeft().getName() + " with reference " + b.getLeft());
			    a.setLeft(a.getRight());
			    	System.out.println("a has new left --> " + a.getLeft().getName() + " with reference " + a.getLeft());
		    	
			    a.setRightTerminal();
			    a.setRight(null); 
		    		System.out.println("a is null");
		    	aTemp.setRight(b);
			    
			   
		    }  
   
		    
		    //IF THEY ARE RIGHT NEXT TO EACHOTHER
		    else {
		    	TrainStation bLeftTemp = a.getLeft();
			
		    	a.setLeft(a.getRight());
					System.out.println("a has new left --> " + a.getLeft().getName() + " with reference " + a.getLeft());
				
				a.setRight(a.getRight().getRight());
    				System.out.println("a has new right --> " + a.getRight().getName() + " with reference " + a.getRight());
    			
    			
    		
				b.setRight(b.getLeft());
					
					System.out.println("b has new right --> " + b.getRight().getName() + " with reference " + b.getRight());
					
    			b.setLeft(bLeftTemp);
					System.out.println("b has new left --> " + b.getLeft().getName() + " with reference " + b.getLeft());
				
			
				this.lineMap = this.getLineArray();
				
				b.getLeft().setRight(b);
				a.getRight().setLeft(a);
				
			
		    }
		    this.lineMap = this.getLineArray();
			System.out.println("line map is " + Arrays.toString(lineMap));
			System.out.println();	
	}

	
	

	public TrainStation[] getLineArray() {
		//VARIABLES
		TrainStation c = this.getLeftTerminus();
		int sizeArray = this.getSize();
		TrainStation[] myArrayStations = new TrainStation[sizeArray];
		
		c = this.leftTerminus;
		
		//ITERATE THROUGH THE TRAINLINE
		for (int i = 0; i< sizeArray; i++ ) {
			myArrayStations[i] = c;
			c = c.getRight();
			
		}
		return myArrayStations;
	}

	private TrainStation[] shuffleArray(TrainStation[] array) {
		Random rand = new Random();
		rand.setSeed(11);
		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			TrainStation temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		this.lineMap = array;
		return array;
	}

	public void shuffleLine() {

		//VARIABLES AND DECLARATIONS
		TrainStation[] lineArray = this.getLineArray();
		TrainStation[] shuffledArray = shuffleArray(lineArray);
		
		//REORDER THE SHUFFLED ARRAY
		for (int i = 0; i< shuffledArray.length; i++) {
			if (i == 0) { //when i is leftmost
				shuffledArray[i].setNonTerminal();
				shuffledArray[i].setLeftTerminal();
				shuffledArray[i].setRight(shuffledArray[1]);
				shuffledArray[i].setLeft(null);//bc already leftmost terminal
			}
			
			else if (i == shuffledArray.length-1){ //when i is rightmost
				shuffledArray[i].setNonTerminal();
				shuffledArray[i].setRightTerminal();
				shuffledArray[i].setRight(null);//bc already rightmost terminal
				shuffledArray[i].setLeft(shuffledArray[shuffledArray.length-2]);
			}
			else { //middle elements
				shuffledArray[i].setNonTerminal();
				shuffledArray[i].setLeft(shuffledArray[i-1]);
				shuffledArray[i].setRight(shuffledArray[i+1]);
			}
			
			//now shuffled array contains the correct order or train stations
		}
		
		//this.lineMap = shuffledArray;
		
		//STARTING FROM THE LEFT, TRAINLINE STATION[0] = SHUFFLEDARRAY[0] ETC
		
		//REDEFINE LEFT AND RIGHT TERMINUS VALUES
		this.leftTerminus = shuffledArray[0];
		this.rightTerminus = shuffledArray[shuffledArray.length-1];
		
		
		

	}

	public String toString() {
		TrainStation[] lineArr = this.getLineArray();
		String[] nameArr = new String[lineArr.length];
		for (int i = 0; i < lineArr.length; i++) {
			nameArr[i] = lineArr[i].getName();
		}
		return Arrays.deepToString(nameArr);
	}

	public boolean equals(TrainLine line2) {

		// check for equality of each station
		TrainStation current = this.leftTerminus;
		TrainStation curr2 = line2.leftTerminus;

		try {
			while (current != null) {
				if (!current.equals(curr2))
					return false;
				else {
					current = current.getRight();
					curr2 = curr2.getRight();
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public TrainStation getLeftTerminus() {
		return this.leftTerminus;
	}

	public TrainStation getRightTerminus() {
		return this.rightTerminus;
	}
}

//Exception for when searching a line for a station and not finding any station of the right name.
class StationNotFoundException extends RuntimeException {
	String name;

	public StationNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "StationNotFoundException[" + name + "]";
	}
}

