package Assignment3;


import java.util.ArrayList;
import java.util.Iterator;

public class CatTree implements Iterable<CatInfo>{
    public CatNode root;
    public int timesCalled=0;
    int hiredCounter=0;
	
    //THE FOLLOWING MAIN METHOD IS ONLY USED FOR TESTING
    /*
    public static void main(String[] args) {
    	CatInfo info1 = new CatInfo("A",87,50,250,35);
    	CatInfo info2 = new CatInfo("B",88,60,248,50);
    	CatInfo info3 = new CatInfo("F",85,60,249,26);
    	CatInfo info4 = new CatInfo("H",95,55,244,46);
    	CatInfo info5 = new CatInfo("D",85,50,247,5);
    	CatInfo info6 = new CatInfo("E",85,45,246,42);
    	CatInfo info7 = new CatInfo("C",87,55,249,23);
    	CatInfo info8 = new CatInfo("G",86,55,249,11);  
    	//CatInfo info9 = new CatInfo("I", 97, 50, 0, 0);
    	
    	CatTree t = new CatTree(info1);
    	t.addCat(info2);
    	t.addCat(info3);
    	t.addCat(info4);
    	t.addCat(info5);
    	t.addCat(info6);
    	t.addCat(info7);
    	t.addCat(info8);
    	//t.addCat(info9);
    	//To see what tree is- working
    	//System.out.println(t.root.toString());
    	
    	//TEST: MostSenior- working
    	//System.out.println("and its most senior member is " + t.root.mostSenior());
    	
    	//TEST: fluffiest- working
    	//System.out.println("but the fluffiest member is " + t.root.fluffiest());
    	
    	//TEST: hiredFromMonths- CHECK!!!!!
    	//System.out.println("the number of cats hired during months 82 -> 87: " + t.root.hiredFromMonths(82, 87));
    	//System.out.println("the number of cats hired during months 85 -> 86: " + t.root.hiredFromMonths(85, 86)); //all cats in tree are visible
    	//System.out.println("num cats 85, 85 " + t.root.hiredFromMonths(85, 85));
    	
    	//TEST: fluffiestFromMonth- working
    	//System.out.println("the fluffiest from month 86 is " + t.root.fluffiestFromMonth(86));
    	//System.out.println("the fluffiest from month 95 is " + t.root.fluffiestFromMonth(95)); 
    	//System.out.println("the fluffiest from month 85 is " + t.root.fluffiestFromMonth(85)); 
    	//System.out.println("the fluffiest from month 82 is " + t.root.fluffiestFromMonth(82)); 
    	
    	//for (CatInfo x : t) {
    		//System.out.println(x.toString());
    	//}
    	
    	
    	/*
    	t.root.removeCat(info7);
    	System.out.println("removed coco results in \n" + t.root.toString());
    	t.root.removeCat(info1);
    	System.out.println("then removed alice results in \n" + t.root.toString());
    	t.root.removeCat(info2);
    	System.out.println("then removed Bob results in \n" + t.root.toString());
    	t.root.removeCat(info9);
    	System.out.println("then ingrid removed does \n" + t.root.toString());
    	
    	Iterator<CatInfo> iterator=t.iterator();
		CatInfo deets=iterator.next();
		deets=iterator.next();
		deets=iterator.next();
		deets=iterator.next();
		System.out.println(deets);		
		
		Boolean bool=iterator.hasNext();
		System.out.println(bool);
		

    	
    	//System.out.println("costPlanning(10)");
    	//t.root.costPlanning(10);
    	
    	
    	
    }
*/
  
   
    public CatTree(CatInfo c) {
        this.root = new CatNode(c);
    }
    
    private CatTree(CatNode c) {
        this.root = c;
    }
    
    
    public void addCat(CatInfo c)
    {
    	//takes in a CatInfo object and converts it to CatNode and calls CatNode.addCat() with the CatNode as input
        this.root = root.addCat(new CatNode(c));
    }
    
    public void removeCat(CatInfo c)
    {
        this.root = root.removeCat(c);
    }
    
    public int mostSenior()
    {
        return root.mostSenior();
    }
    
    public int fluffiest() {
        return root.fluffiest();
    }
    
    public CatInfo fluffiestFromMonth(int month) {
    	
        return root.fluffiestFromMonth(month);
    }
    
    public int hiredFromMonths(int monthMin, int monthMax) {
    	
        return root.hiredFromMonths(monthMin, monthMax);
    }
    
    public int[] costPlanning(int nbMonths) {
        return root.costPlanning(nbMonths);
    }
    
    public Iterator<CatInfo> iterator()
    {
        return new CatTreeIterator(); //returns an object of type iterator, returns an object that will allow us to iterate through the collection
    }
 
    class CatNode {
        
        CatInfo data;
        CatNode senior;
        CatNode same;
        CatNode junior;
        
        
        public CatNode(CatInfo data) {
            this.data = data;
            this.senior = null;
            this.same = null;
            this.junior = null;
        }
        
        public String toString() {
            String result = this.data.toString() + "\n";
            if (this.senior != null) {
                result += "more senior " + this.data.toString() + " :\n";
                result += this.senior.toString();
            }
            if (this.same != null) {
                result += "same seniority " + this.data.toString() + " :\n";
                result += this.same.toString();
            }
            if (this.junior != null) {
                result += "more junior " + this.data.toString() + " :\n";
                result += this.junior.toString();
            }
            return result;
        }
        
        
        public CatNode addCat(CatNode catToAdd) {
          
        	//CASE 1: When the cat is senior
        	if (catToAdd.data.monthHired< this.data.monthHired) {
        		//if the catToAdd is senior and no more senior things to traverse, add the cat here
        		if (this.senior == null) {
        			this.senior = catToAdd;
        		}
        		else { //do the method again with the cat to add and this.senior
        			this.senior.addCat(catToAdd);
        			//theArray.add(catToAdd.data);
        		}
        		
        	}
        	
        	//CASE 2: When the cat is junior
        	if (catToAdd.data.monthHired> this.data.monthHired) {
        		//if the catToAdd is junior and there are no more things to 
        		if (this.junior == null) {
        			this.junior = catToAdd;
        			
        		}
        		else {
        			this.junior.addCat(catToAdd);
        			//theArray.add(catToAdd.data);
        		}
        	}
        	
        	//CASE 3 When the month hired is same, you also need to compare fur thickness
        	if (catToAdd.data.monthHired == this.data.monthHired) {
        		//CASE 3a: If the cat to add has a thinner fur, then you just put it under
        		//CASE 3b: If the cat to add has the same fur thickness, then you just put it under
        		if (catToAdd.data.furThickness <= this.data.furThickness) {
        			//fur is less and no other nodes
        			if (this.same == null) {
        				this.same = catToAdd;
        			}
        			else { //fur is less but there's still nodes keep checking
        				this.same.addCat(catToAdd);
        				//theArray.add(catToAdd.data);
        			}
        		}
        		
        		//CASE 3c: If the cat to add has thicker fur, swap info and add node with connections
        		if (catToAdd.data.furThickness > this.data.furThickness) {
        			//fur is more and end node
        			if (this.same == null) {

        				//swap the cat infos 
        				CatInfo t = this.data;
        				this.data = catToAdd.data;
        				catToAdd.data = t;
        				
        				
        				//add the cat node as this.same
        				this.same = catToAdd;
        				
        			}
        			//swapping info
        			else { //swap info and call method again 
        				CatInfo temp = this.data;
            			this.data = catToAdd.data;
            			catToAdd.data = temp;
            			this.same.addCat(catToAdd);
            			//theArray.add(catToAdd.data);
        			}
        			
        		}
        	}
        	
            return this; 
        }
        private CatNode findMin(CatNode r) {
        	if (r == null) {
        		return null;
        	}
        	else if (r.senior == null) {
        		return r;
        	}
        	else {
        		return findMin(this.senior);
        	}
        }
        private CatNode findMax(CatNode a) {
        	if (a == null)
        		return null;
        	else if (a.junior == null) {
        		return a;
        	}
        	else
        		return findMax(a.junior);
        }
        public CatNode removeCat(CatInfo cInf) {
        	System.out.println("r is " +this);
        	
         	if(this.senior != null &&this.senior.data.equals(cInf) &&this.senior.senior == null &&this.senior.same == null &&this.senior.junior == null) {
         		this.senior = null;
         	
         	}
         	if (this.junior != null &&this.junior.data.equals(cInf) &&this.junior.senior == null &&this.junior.same== null &&this.junior.junior == null) {
         		this.junior = null;
         	}
         	if (this.same != null &&this.same.data.equals(cInf) &&this.same.senior == null &&this.same.same == null &&this.same.junior == null) {
         		this.same = null;
         	}
     		if(this.data.monthHired > cInf.monthHired) { 
     			if (this.senior != null) {
     				this.senior.removeCat(cInf);
     			}
         	 }
         	
         	else if (this.data.monthHired < cInf.monthHired) {
         		System.out.println("this.junior " + this.junior);
         		if (this.junior != null) {
         			this.junior.removeCat(cInf);
         			}
         		}
         	
         	
         	else  {
         		if (!this.data.equals(cInf)) {
         			if (this.same != null) {
         				this.same.removeCat(cInf);
         			}
         			
         		}
         		//else equal month and thats all we know
         		//if c != this.data then do stuff
         		//same thing as above but with same
         		//else--> means c.equals this.data --> use three cases
         		else {
         			//CASE 1: Works
             		if (this.same != null) { 
             			System.out.println("CASE 1 \n");
             			CatInfo cSame =this.same.data;
             			this.data = cSame;
             		
             			if (this.same.same == null){
             				this.same = null;
             			}
             			else {
             				this.same =this.same.same;
             			}
             		}
             		
             		//CASE 2:
             		else if (this.senior != null) {
             			
             			System.out.println("CASE 2 \n");
             			
             			//handles branching
             			CatNode origJunior =this.junior;
             			this.junior = null;
             			
             			//duplicates the CatInfo
             			CatInfo felix = this.senior.data;
             			
             			
             			CatNode temporarySenior =this.senior.senior;
             			CatNode temporaryJunior =this.senior.junior;
             			CatNode temporarySame =this.senior.same;
             			
             			//reassigns
             			this.data = felix;
             			this.senior = temporarySenior;
             			this.junior = temporaryJunior;
             			this.same = temporarySame;
             		
             		    CatNode maxNode = findMax(this);
             		    
             		    maxNode.junior = origJunior;
             		   
             		    
             		}
             		
             		
             		

             		//CASE 3:
             		else if (this.junior != null ) {
             			System.out.println("CASE 3 \n");
             			//System.out.println("the cInf is " + cInf.toString());
             			
             			CatInfo hilda =this.junior.data;
             			
             			CatNode tempSen =this.junior.senior;
             			CatNode tempJun =this.junior.junior;
             			CatNode tempSame =this.junior.same;
             			
             			this.data = hilda;
             			this.senior = tempSen;
             			this.junior = tempJun;
             			this.same = tempSame;
             		
             			
             		}
             		//CASE 4:
             		//if you want to remove the root but it has no children
             		else {
             			this.data = null;
             			return null;
             		}
         		}
         	}
         	
         	return this;
            
        }
        
        public int mostSenior() {
        	
            if (this.senior == null) {
            	return this.data.monthHired;
            }
            else {
            	return this.senior.mostSenior();
            }
            
        }
        
        public int fluffiest() {
        	 int fluffyAns=0;
        	if (this.senior != null) {
        		fluffyAns = this.senior.fluffiest();
        	}
        	if (this.junior != null) {
        		fluffyAns = this.junior.fluffiest();
        	}
        	if (this.data.furThickness > fluffyAns) fluffyAns = this.data.furThickness;
        		
            return fluffyAns; 
        }
        
        public int hiredFromMonths(int monthMin, int monthMax) {
            //Initial Check for Valid Inputs
        	if (monthMin > monthMax) {
            	return 0;
            }
            if (root == this) {
            	hiredCounter = 0;  
            }
            if (this.senior != null) {
            	hiredCounter = this.senior.hiredFromMonths(monthMin, monthMax);
            }
            
        	if (this.junior != null) {
        		hiredCounter = this.junior.hiredFromMonths(monthMin, monthMax);
        	}
        	
        	if (this.same != null) {
        		hiredCounter = this.same.hiredFromMonths(monthMin, monthMax);
        	}
        	
        	
        	if (this.data.monthHired >= monthMin && this.data.monthHired <= monthMax) hiredCounter++;
        	
        	
            return hiredCounter; 
            
        }
        
        public CatInfo fluffiestFromMonth(int month) {
        	System.out.println("fluffiest from month is being called upon " + this.data);
            if (this.data.monthHired > month && this.senior != null) {
            	return this.senior.fluffiestFromMonth(month);
            }
            if (this.data.monthHired < month && this.junior != null) {
            	return this.junior.fluffiestFromMonth(month);
            }
            if (this.data.monthHired == month) {
            	return this.data;
            }
            else {
            	return null;
            }
        }
        
        public int[] costPlanning(int nbMonths) {
        	CatTree theTree = new CatTree(this);
        	CatTreeIterator iterator = new CatTreeIterator();
            int [] costArray = new int[nbMonths];
            
            
            for (CatInfo i : theTree) {
            	
            	int month = i.nextGroomingAppointment; //month = 249
            	int iCost = i.expectedGroomingCost; //c = 11
            	int index = month -243;
            	if (month <= (nbMonths -1)+243)  {
            		costArray[index] += iCost;
            	}
            }
            
            
            return costArray; 
        }
        
    }
    
    private class CatTreeIterator implements Iterator<CatInfo> {
        //fields here
    	CatNode cur;
    	ArrayList<CatInfo> myArray = new ArrayList<CatInfo>();
    	
    	private int index=0;
    	private int x;
    	
        public CatTreeIterator() {
        	cur = root;
        	
        	inorder(cur);
        	x = myArray.size();
        
        }
        private void inorder(CatNode cur) {
        	if (cur == null) {
        		return;
        	}
        	//L,root,S, right
        	if (cur.senior != null) {
        		inorder(cur.senior);
        	}
        	myArray.add(cur.data);
        	if (cur.same != null) {
        		inorder(cur.same);
        	}
        	
        	if (cur.junior != null) {
        		inorder(cur.junior);
        	}
        }
        public CatInfo next(){
            return (CatInfo) myArray.get(index++); 
        }
        
        public boolean hasNext() {
            return index<x; 
        }
    }
    
}

