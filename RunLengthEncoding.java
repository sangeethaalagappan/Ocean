/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes an
 *  Ocean object.  Descriptions of the methods you must implement appear below.
 *  They include constructors of the form
 *
 *      public RunLengthEncoding(int i, int j, int starveTime);
 *      public RunLengthEncoding(int i, int j, int starveTime,
 *                               int[] runTypes, int[] runLengths) {
 *      public RunLengthEncoding(Ocean ocean) {
 *
 *  that create a run-length encoding of an Ocean having width i and height j,
 *  in which sharks starve after starveTime timesteps.
 *
 *  The first constructor creates a run-length encoding of an Ocean in which
 *  every cell is empty.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts an Ocean object into a run-length encoding of that object.
 *
 *  See the README file accompanying this project for additional details.
 */

public class RunLengthEncoding {

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */
    
    private int width;
    private int height;
    private int sharkstarvetime;
    private int creature;
    private int runindex=0;
    private DList Oceanrun;
    private DListNode inserted;
    private DListNode inserted2;
    
    
  /* DListNode class and DList class are based on DList2.java and DListNode2.java from lab4 
   * and both have been modified to function in the context of Project1.
   */ 

    
    
private class DListNode {

  /**
   *  item references the item stored in the current node.
   *  prev references the previous node in the DList.
   *  next references the next node in the DList.
   *
   
   */

  private Object item;
  public DListNode prev;
  public DListNode next = null;
  private int creature;
  private int feeding = 0;
  private int repetition=0;
  

  DListNode(int creature,int feeding,int repetition, DListNode prev, DListNode next) {
    this.creature=creature;
    if (this.creature==Ocean.SHARK){
    this.feeding=feeding;
    }
    this.repetition = repetition;
    this.next=next;
    this.prev = prev;
  }
  
  DListNode(){
      this(Ocean.EMPTY,0,1,null,null);
  }
  
  //DListNode(int creature){    //Constructor case for 1 parameter (creature) input.
    //  this(creature,0,null,null);
  //}
  
  /*DListNode(int creature,int feeding){  //Constructor case when there is no next item.
      this(creature,feeding,null);
  }
  
  DListNode(int creature,DListNode next){    //Constructor case when the creature is not a shark.
      this(creature,0,next);
  } */
}

private class DList {

  /**
   *  head references the sentinel node.
   *
   * 
   */

  private DListNode head;
  private int size;
  private DListNode currentNode;

  /* DList2 invariants:
   *  1)  head != null.
   *  2)  For any DListNode2 x in a DList2, x.next != null.
   *  3)  For any DListNode2 x in a DList2, x.prev != null.
   *  4)  For any DListNode2 x in a DList2, if x.next == y, then y.prev == x.
   *  5)  For any DListNode2 x in a DList2, if x.prev == y, then y.next == x.
   *  6)  size is the number of DListNode2s, NOT COUNTING the sentinel
   *      (denoted by "head"), that can be accessed from the sentinel by
   *      a sequence of "next" references.
   */

  /**
   *  DList2() constructor for an empty DList2.
   */
  private DList() {
   head = new DListNode();    
   head.item = Integer.MIN_VALUE;
   head.next = head;
   head.prev= head;
    size = 0;        
  }
  
  private DList(int i,int j,int starveTime){
      head = new DListNode();
      head.item=Integer.MIN_VALUE;
      head.next = head;
      head.prev=head;
      size = 0;
      width = i;
      height = j;
      sharkstarvetime = starveTime;
  }


   private int length() {
    return size;
  }


  private void insertFront(int creature,int repetition,int feeding) {
     if (size!=0){
    if (head.prev != head) { 
      DListNode newcreature = new DListNode(creature,feeding,repetition,null,null);
      
      head.next.prev = newcreature;
      newcreature.next = head.next;
      newcreature.prev=head;
      head.next = newcreature;
                     
      size++;
    }
     } else{
        DListNode newcreature = new DListNode(creature,feeding,repetition,null,null);
      
      head.next = newcreature;
      head.prev = newcreature;
      newcreature.next = head;
      newcreature.prev = head;
      size++;
     }
  }

 
  private void insertBack(int creature,int repetition,int feeding) {
      if (size != 0){
    if (head.prev != head) {  
        DListNode newcreature = new DListNode(creature,feeding,repetition,null,null);
        
    
        head.prev.next = newcreature;
        newcreature.prev = head.prev;
        newcreature.next = head;
        head.prev = newcreature;
          
      size++;
    }
      } else{
         DListNode newcreature = new DListNode(creature,feeding,repetition,null,null);
       
        head.prev = newcreature;
        head.next = newcreature;
        newcreature.prev = head;
        newcreature.next =head;
        size++;
      }
  }
  
  /* Adapted from HW3 SList.java code. This method returns the entire DListNode at nth position.*/
  private DListNode nth(int position) {
   currentNode = new DListNode();

    if ((position < 1) || (head == head.next) || (head == head.prev)) {
      return null;
    } else {
      currentNode = head.next;
      while (position > 1) {
        currentNode = currentNode.next;
        if (currentNode == null) {
          return null;
        }
        position--;
      }
      return currentNode;
    }
  }
}

private Boolean sharkcompareWithNextCell(Ocean sea, int x, int y) {
        if (x == sea.width() - 1) {
            if (y == sea.height() - 1) {
                return false;
            } else {
                return sea.cellContents(0, y + 1) == sea.cellContents(x, y) && sea.sharkFeeding(0, y + 1) == sea.sharkFeeding(x, y);
            }
        } else {
            return sea.cellContents(x + 1, y) == sea.cellContents(x, y) && sea.sharkFeeding(x + 1, y) == sea.sharkFeeding(x, y);
        }
    }

private Boolean compareWithNextCell(Ocean sea, int x, int y) {
        if (x == sea.width() - 1) {
            if (y == sea.height() - 1) {
                return false;
            } else {
                return sea.cellContents(0, y + 1) == sea.cellContents(x, y);
            }
        } else {
            return sea.cellContents(x + 1, y) == sea.cellContents(x, y);
        }
    }



  /**
   *  The following methods are required for Part II.
   */

  /**
   *  RunLengthEncoding() (with three parameters) is a constructor that creates
   *  a run-length encoding of an empty ocean having width i and height j,
   *  in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public RunLengthEncoding(int i, int j, int starveTime) {
    Oceanrun = new DList(i,j,starveTime);
    Oceanrun.insertFront(Ocean.EMPTY,i*j,0);

    
    
  }

  /**
   *  RunLengthEncoding() (with five parameters) is a constructor that creates
   *  a run-length encoding of an ocean having width i and height j, in which
   *  sharks starve after starveTime timesteps.  The runs of the run-length
   *  encoding are taken from two input arrays.  Run i has length runLengths[i]
   *  and species runTypes[i].
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   *  @param runTypes is an array that represents the species represented by
   *         each run.  Each element of runTypes is Ocean.EMPTY, Ocean.FISH,
   *         or Ocean.SHARK.  Any run of sharks is treated as a run of newborn
   *         sharks (which are equivalent to sharks that have just eaten).
   *  @param runLengths is an array that represents the length of each run.
   *         The sum of all elements of the runLengths array should be i * j.
   */

  public RunLengthEncoding(int i, int j, int starveTime,
                           int[] runTypes, int[] runLengths) {
    Oceanrun = new DList(i,j,starveTime);   
    for (int index=0;index<runTypes.length;index++){        
            if(runTypes[index]!=Ocean.SHARK){      
            Oceanrun.insertBack(runTypes[index],runLengths[index],0);
            
            } else{
            Oceanrun.insertBack(runTypes[index], runLengths[index], starveTime); //Any run of sharks is treated as a run of newborn sharks
            
            }
        
    }
  }

  /**
   *  restartRuns() and nextRun() are two methods that work together to return
   *  all the runs in the run-length encoding, one by one.  Each time
   *  nextRun() is invoked, it returns a different run (represented as an
   *  array of two ints), until every run has been returned.  The first time
   *  nextRun() is invoked, it returns the first run in the encoding, which
   *  contains cell (0, 0).  After every run has been returned, nextRun()
   *  returns null, which lets the calling program know that there are no more
   *  runs in the encoding.
   *
   *  The restartRuns() method resets the enumeration, so that nextRun() will
   *  once again enumerate all the runs as if nextRun() were being invoked for
   *  the first time.
   *
   *  (Note:  Don't worry about what might happen if nextRun() is interleaved
   *  with addFish() or addShark(); it won't happen.)
   */

  /**
   *  restartRuns() resets the enumeration as described above, so that
   *  nextRun() will enumerate all the runs from the beginning.
   */

  public void restartRuns() {
   
    runindex = 0;
    
  }

  /**
   *  nextRun() returns the next run in the enumeration, as described above.
   *  If the runs have been exhausted, it returns null.  The return value is
   *  an array of two ints (constructed here), representing the type and the
   *  size of the run, in that order.
   *  @return the next run in the enumeration, represented by an array of
   *          two ints.  The int at index zero indicates the run type
   *          (Ocean.EMPTY, Ocean.SHARK, or Ocean.FISH).  The int at index one
   *          indicates the run length (which must be at least 1).
   */

  public int[] nextRun() {
   
        if(runindex < Oceanrun.length()){
            int[] nextrunarray = new int[2];
            //System.out.println(Oceanrun.head.next.creature);
            DListNode runDListNode = Oceanrun.nth(runindex+1);      
            //System.out.println(Oceanrun.head.next.creature+""+Oceanrun.head.next.repetition);
            
            nextrunarray[0]=runDListNode.creature;
            nextrunarray[1]=runDListNode.repetition;
            runindex++;
            return nextrunarray;               
        } else{
            return null;
        } 
  }
  

  /**
   *  toOcean() converts a run-length encoding of an ocean into an Ocean
   *  object.  You will need to implement the three-parameter addShark method
   *  in the Ocean class for this method's use.
   *  @return the Ocean represented by a run-length encoding.
   */

 public Ocean toOcean() {
    
    Ocean convertedtoocean = new Ocean(width,height,sharkstarvetime);
    int yindex = 0;
    int xindex = 0;
    
    for (int convertindex=1;convertindex<Oceanrun.length()+1;convertindex++){
        int repeatcounter = Oceanrun.nth(convertindex).repetition;
        for (;xindex<xindex+repeatcounter && xindex < convertedtoocean.width() && yindex < convertedtoocean.height();xindex++){            
            
               
            if (Oceanrun.nth(convertindex).creature==Ocean.SHARK){
                convertedtoocean.addShark(xindex,yindex,Oceanrun.nth(convertindex).feeding);
                repeatcounter--;
            } else if(Oceanrun.nth(convertindex).creature==Ocean.FISH){
                convertedtoocean.addFish(xindex,yindex);
                repeatcounter--;
            } else{
                repeatcounter--;
            }
            if (xindex==convertedtoocean.width()-1 && yindex < convertedtoocean.height()){
                yindex++;
                xindex=-1;               
            }    
        }
    }

  return convertedtoocean;     
 }

 
  

  /**
   *  The following method is required for Part III.
   */

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of an input Ocean.  You will need to implement
   *  the sharkFeeding method in the Ocean class for this constructor's use.
   *  @param sea is the ocean to encode.
   */

   public RunLengthEncoding(Ocean sea) {

        Oceanrun = new DList();
        this.width = sea.width();
        this.height = sea.height();
        this.sharkstarvetime = sea.starveTime();

        int yindex = 0;
        int xindex = 0;

        int repetitioncounter = 1;
        for (; xindex < sea.width() && yindex < sea.height(); xindex++){
            repetitioncounter = 1;
            if (sea.cellContents(xindex, yindex) == Ocean.SHARK) {
                for (; sharkcompareWithNextCell(sea, xindex, yindex) && yindex != sea.height(); xindex++) {
                repetitioncounter++;
                if (xindex == sea.width() - 1) {
                    yindex++;
                    xindex = -1;
                }
            }          
                this.Oceanrun.insertBack(Ocean.SHARK, repetitioncounter, sea.sharkFeeding(xindex,yindex));
            } else if (sea.cellContents(xindex, yindex) == Ocean.FISH) {
                for (; compareWithNextCell(sea, xindex, yindex) && yindex != sea.height(); xindex++) {
                repetitioncounter++;
                if (xindex == sea.width() - 1) {
                    yindex++;
                    xindex = -1;
                }
            }       
                this.Oceanrun.insertBack(Ocean.FISH, repetitioncounter, 0);
            } else {
                for (; compareWithNextCell(sea, xindex, yindex) && yindex != sea.height(); xindex++) {
                repetitioncounter++;
                if (xindex == sea.width() - 1) {
                    yindex++;
                    xindex = -1;
                }
            }       
                this.Oceanrun.insertBack(Ocean.EMPTY, repetitioncounter, 0);
                    }  
            
           
            if (xindex == sea.width() - 1 && yindex < sea.height()) {
                yindex++;
                xindex = -1;
            }
        }
        


 
    // Your solution here, but you should probably leave the following line
    //   at the end.
   check();
  }

  /**
   *  The following methods are required for Part IV.
   */

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.  The final run-length
   *  encoding should be compressed as much as possible; there should not be
   *  two consecutive runs of sharks with the same degree of hunger.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

 public void addFish(int x, int y) {
     int count = 0;
     int count2=1;
     int oldcount =0;
     
      for (int indexadd = 1; indexadd<Oceanrun.length();indexadd++){
     count=Oceanrun.nth(indexadd).repetition+count; 
     if (indexadd>=2){
     oldcount = Oceanrun.nth(indexadd-1).repetition+oldcount;
     }
     if (count >= (x)*(y)&& count2==1){
         if (Oceanrun.nth(indexadd).creature==Ocean.EMPTY){
             if(count > x*y && count-x*y >= 2 && x*y-oldcount>=2){
                 int after = count-(x*y);
                 int before = Oceanrun.nth(indexadd).repetition-after-1;
                 Oceanrun.nth(indexadd).repetition = before;
                 inserted = new DListNode(Ocean.FISH,0,1,Oceanrun.nth(indexadd),null);
                 inserted2 = new DListNode(Ocean.EMPTY,0,after,inserted,Oceanrun.nth(indexadd+1));
                 Oceanrun.nth(indexadd).next = inserted;
                 inserted.prev = Oceanrun.nth(indexadd);
                 inserted.next = inserted2;
                 inserted2.prev = inserted;
                 inserted2.next = Oceanrun.nth(indexadd+1);
                 Oceanrun.nth(indexadd+1).prev = inserted2;
                 Oceanrun.size = Oceanrun.size + 2;
                 
                 
             }else{
             if (Oceanrun.nth(indexadd-1).creature == Ocean.FISH){
                 if(Oceanrun.nth(indexadd+1).creature==Ocean.FISH && Oceanrun.nth(indexadd).repetition==1){
                 Oceanrun.nth(indexadd-1).repetition = Oceanrun.nth(indexadd-1).repetition + Oceanrun.nth(indexadd+1).repetition + 1;
                 Oceanrun.nth(indexadd-1).next = Oceanrun.nth(indexadd+2);
                Oceanrun.nth(indexadd+2).prev = Oceanrun.nth(indexadd-1);
                Oceanrun.size = Oceanrun.size-2;
                 } else{
                
                    if (Oceanrun.nth(indexadd).repetition == 1){
                        Oceanrun.nth(indexadd-1).repetition = Oceanrun.nth(indexadd-1).repetition + 1;
                        Oceanrun.nth(indexadd-1).next = Oceanrun.nth(indexadd+1);
                        Oceanrun.nth(indexadd+1).prev = Oceanrun.nth(indexadd-1);
                        Oceanrun.size--;
                    } else {
                        Oceanrun.nth(indexadd).repetition--;
                        Oceanrun.nth(indexadd-1).repetition++;
                }
             } 
             }
             else if(Oceanrun.nth(indexadd+1).creature==Ocean.FISH){
               
                
                if (Oceanrun.nth(indexadd).repetition == 1){
                Oceanrun.nth(indexadd+1).repetition = Oceanrun.nth(indexadd+1).repetition + 1;
                Oceanrun.nth(indexadd+1).prev = Oceanrun.nth(indexadd-1);
                Oceanrun.nth(indexadd-1).next = Oceanrun.nth(indexadd+1);
                Oceanrun.size--;
                } else {
                    Oceanrun.nth(indexadd).repetition--;
                    Oceanrun.nth(indexadd+1).repetition++;
                }
             }  else if(Oceanrun.nth(indexadd-1).creature==Ocean.SHARK && Oceanrun.nth(indexadd+1).creature==Ocean.SHARK){
                 if(Oceanrun.nth(indexadd).repetition == 1){
                     Oceanrun.nth(indexadd).creature = Ocean.FISH;
                 } else{
                     
                     if(Oceanrun.nth(indexadd-1).creature==Ocean.SHARK){                        
                         inserted = new DListNode(Ocean.FISH,0,1,Oceanrun.nth(indexadd-1),Oceanrun.nth(indexadd));
                         Oceanrun.nth(indexadd-1).next = inserted;
                         Oceanrun.nth(indexadd).repetition = Oceanrun.nth(indexadd).repetition-1;
                         Oceanrun.nth(indexadd).creature=Ocean.EMPTY;
                         Oceanrun.nth(indexadd).prev = inserted;
                         Oceanrun.size++;
                     } else if(Oceanrun.nth(indexadd+1).creature==Ocean.SHARK){
                         inserted = new DListNode(Ocean.FISH,0,1,Oceanrun.nth(indexadd),Oceanrun.nth(indexadd+1));
                         Oceanrun.nth(indexadd+1).prev=inserted;
                         Oceanrun.nth(indexadd).repetition--;
                         Oceanrun.nth(indexadd).creature = Ocean.EMPTY;
                         Oceanrun.nth(indexadd).prev = inserted;
                         Oceanrun.size++;
                     }
                 }
             }
         } 
         } count2 = 2;
         indexadd = Oceanrun.length();
     }
     
      }
    check();
  }
  

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  The final run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs of sharks with the same degree
   *  of hunger.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
    int countshark = 0;
     int countshark2=1;
     int sharkoldcount = 0;
     
      for (int indexadd1 = 1; indexadd1<Oceanrun.length();indexadd1++){
     countshark=Oceanrun.nth(indexadd1).repetition+countshark; 
     if (indexadd1>=2){
     sharkoldcount = Oceanrun.nth(indexadd1-1).repetition+sharkoldcount;
     }
     if (countshark >= (x)*(y)&& countshark2==1){
         if (Oceanrun.nth(indexadd1).creature==Ocean.EMPTY){
             if(countshark > x*y && countshark-x*y >= 2&& x*y-sharkoldcount>=2){
                 int after = countshark-(x*y);
                 int before = Oceanrun.nth(indexadd1).repetition-after-1;
                 Oceanrun.nth(indexadd1).repetition = before;
                 inserted = new DListNode(Ocean.SHARK,this.sharkstarvetime,1,Oceanrun.nth(indexadd1),null);
                 inserted2 = new DListNode(Ocean.EMPTY,0,after,inserted,Oceanrun.nth(indexadd1+1));
                 Oceanrun.nth(indexadd1).next = inserted;
                 inserted.prev = Oceanrun.nth(indexadd1);
                 inserted.next = inserted2;
                 inserted2.prev = inserted;
                 inserted2.next = Oceanrun.nth(indexadd1+1);
                 Oceanrun.nth(indexadd1+1).prev = inserted2;
                 Oceanrun.size = Oceanrun.size + 2;
                 
                 
             }else{
             if (Oceanrun.nth(indexadd1-1).creature == Ocean.SHARK && Oceanrun.nth(indexadd1-1).feeding == this.sharkstarvetime){
                 if(Oceanrun.nth(indexadd1+1).creature==Ocean.SHARK && Oceanrun.nth(indexadd1+1).feeding==this.sharkstarvetime && Oceanrun.nth(indexadd1).repetition==1){
                 Oceanrun.nth(indexadd1-1).repetition = Oceanrun.nth(indexadd1-1).repetition + Oceanrun.nth(indexadd1+1).repetition + 1;
                 Oceanrun.nth(indexadd1-1).next = Oceanrun.nth(indexadd1+2);
                Oceanrun.nth(indexadd1+2).prev = Oceanrun.nth(indexadd1-1);
                Oceanrun.size = Oceanrun.size-2;
                 } else{
                
                    if (Oceanrun.nth(indexadd1).repetition == 1){
                        Oceanrun.nth(indexadd1-1).repetition = Oceanrun.nth(indexadd1-1).repetition + 1;
                        Oceanrun.nth(indexadd1-1).next = Oceanrun.nth(indexadd1+1);
                        Oceanrun.nth(indexadd1+1).prev = Oceanrun.nth(indexadd1-1);
                        Oceanrun.size--;
                    } else {
                        Oceanrun.nth(indexadd1).repetition--;
                        Oceanrun.nth(indexadd1-1).repetition++;
                }
             } 
             }
             else if(Oceanrun.nth(indexadd1+1).creature==Ocean.SHARK && Oceanrun.nth(indexadd1+1).feeding == this.sharkstarvetime){
               
                
                if (Oceanrun.nth(indexadd1).repetition == 1){
                    Oceanrun.nth(indexadd1+1).repetition = Oceanrun.nth(indexadd1+1).repetition + 1;
                Oceanrun.nth(indexadd1+1).prev = Oceanrun.nth(indexadd1-1);
                Oceanrun.nth(indexadd1-1).next = Oceanrun.nth(indexadd1+1);
                Oceanrun.size--;
                } else {
                    Oceanrun.nth(indexadd1).repetition--;
                    Oceanrun.nth(indexadd1+1).repetition++;
                }
                
                
             }  else if((Oceanrun.nth(indexadd1-1).creature==Ocean.FISH && Oceanrun.nth(indexadd1+1).creature==Ocean.FISH) ||((Oceanrun.nth(indexadd1-1).creature==Ocean.SHARK && Oceanrun.nth(indexadd1+1).creature==Ocean.SHARK) && (Oceanrun.nth(indexadd1-1).feeding!=this.sharkstarvetime && Oceanrun.nth(indexadd1+1).feeding!=this.sharkstarvetime)) ){
                 if(Oceanrun.nth(indexadd1).repetition == 1){
                     Oceanrun.nth(indexadd1).creature = Ocean.SHARK;
                     Oceanrun.nth(indexadd1).feeding = sharkstarvetime;
                 } else{
                     
                     if(Oceanrun.nth(indexadd1-1).creature==Ocean.FISH || (Oceanrun.nth(indexadd1-1).creature==Ocean.SHARK && Oceanrun.nth(indexadd1-1).feeding!=sharkstarvetime)){                        
                         inserted = new DListNode(Ocean.SHARK,this.sharkstarvetime,1,Oceanrun.nth(indexadd1-1),Oceanrun.nth(indexadd1));
                         Oceanrun.nth(indexadd1-1).next = inserted;
                         Oceanrun.nth(indexadd1).repetition = Oceanrun.nth(indexadd1).repetition-1;
                         Oceanrun.nth(indexadd1).creature=Ocean.EMPTY;
                         Oceanrun.nth(indexadd1).prev = inserted;
                         Oceanrun.size++;
                     } else if(Oceanrun.nth(indexadd1+1).creature==Ocean.FISH || (Oceanrun.nth(indexadd1+1).creature==Ocean.SHARK && Oceanrun.nth(indexadd1+1).feeding!=this.sharkstarvetime)){
                         inserted = new DListNode(Ocean.SHARK,this.sharkstarvetime,1,Oceanrun.nth(indexadd1),Oceanrun.nth(indexadd1+1));
                         Oceanrun.nth(indexadd1+1).prev=inserted;
                         Oceanrun.nth(indexadd1).repetition--;
                         Oceanrun.nth(indexadd1).creature = Ocean.EMPTY;
                         Oceanrun.nth(indexadd1).prev = inserted;
                         Oceanrun.size++;
                     }
                 }
             }
         } 
         }
         countshark2 = 2;
         indexadd1 = Oceanrun.length();
     } 
      }
    check();
  }

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same contents, or if the sum of all run
   *  lengths does not equal the number of cells in the ocean.
   */

  private void check() {
      
  int sumcounter = 0;
  
  for (int indexcheck=1;indexcheck<Oceanrun.length();indexcheck++){
      if(Oceanrun.nth(indexcheck).repetition==0){
          System.out.println("Invalid Run Length - Can't be Zero");
      } 
  }
  for(int indexcheck2=2;indexcheck2<Oceanrun.length();indexcheck2++){
      if(Oceanrun.nth(indexcheck2).creature == Oceanrun.nth(indexcheck2-1).creature){
          if(Oceanrun.nth(indexcheck2).creature==Ocean.SHARK && Oceanrun.nth(indexcheck2).feeding == Oceanrun.nth(indexcheck2-1).feeding){
              System.out.println("Invalid Shark Runs! - There are runs of sharks with the same hunger");           
          }
          else if(Oceanrun.nth(indexcheck2).creature!=Ocean.SHARK){
          System.out.println("Invalid Fish or Empty Runs! - There are fish/empty runs together");
          }
      }
  }
  
  for (int indexcheck3=1;indexcheck3<Oceanrun.length();indexcheck3++){
      sumcounter = sumcounter + Oceanrun.nth(indexcheck3).repetition;
  }
  if(sumcounter != (width*height)){
      System.out.println("Incorrect sum of run lengths!");
  }
  }
  

}
