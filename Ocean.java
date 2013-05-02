/* Ocean.java */

/**
 *  The Ocean class defines an object that models an ocean full of sharks and
 *  fish.  Descriptions of the methods you must implement appear below.  They
 *  include a constructor of the form
 *
 *      public Ocean(int i, int j, int starveTime);
 *
 *  that creates an empty ocean having width i and height j, in which sharks
 *  starve after starveTime timesteps.
 *
 *  See the README file accompanying this project for additional details.
 */

public class Ocean {

  /**
   *  Do not rename these constants.  WARNING:  if you change the numbers, you
   *  will need to recompile Test4.java.  Failure to do so will give you a very
   *  hard-to-find bug.
   */

  public final static int EMPTY = 0;
  public final static int SHARK = 1;
  public final static int FISH = 2;

  /**
   *  Define any variables associated with an Ocean object here.  These
   *  variables MUST be private.
   */
private int xwidth;
private int yheight;
private int sharkstarvetime;
private int[][] currentocean;
private int[][] hungrysharktable = null;
private static int timestepno = 0;
private int[] neighbourarray;
private int fishcount;
private int sharkcount;
private int sharkhunger;




  /**
   *  The following methods are required for Part I.
   */

  /**
   *  Ocean() is a constructor that creates an empty ocean having width i and
   *  height j, in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public Ocean(int i, int j, int starveTime) {
      
    xwidth = i;
    yheight = j;
    sharkstarvetime = starveTime;
    currentocean = new int[i][j];
    for (int xcounter=0; xcounter<i; xcounter++){
        for (int ycounter=0; ycounter<j; ycounter++){
           currentocean[xcounter][ycounter] = EMPTY;    //Creating an Empty Ocean
        }        
    }
     if (hungrysharktable == null){
         
         HungrySharks(this.xwidth,this.yheight,this.sharkstarvetime);
     }
 
  }

  
  private int[][] HungrySharks(int i, int j, int starveTime) {
      hungrysharktable = new int[i][j];
      for (int xcounter1=0; xcounter1<i; xcounter1++){
        for (int ycounter1=0; ycounter1<j; ycounter1++){
           hungrysharktable[xcounter1][ycounter1] = 0;
        } 
      }
      return hungrysharktable;
  }

  /**
   *  width() returns the width of an Ocean object.
   *  @return the width of the ocean.
   */

  public int width() {
    return xwidth;
  }

  /**
   *  height() returns the height of an Ocean object.
   *  @return the height of the ocean.
   */

  public int height() {
    return yheight;
  }
  
    private int wraparoundx(int x) {
        if (x >= 0) {
            int wrappedx = x % (width());
            return wrappedx;

        } else {
            return wraparoundx(x + (width()));
            
        }
    }
    
    private int wraparoundy(int y) {
        if (y >= 0) {
            int wrappedy = y % (height());
            return wrappedy;
        } else {
            return wraparoundy(y + (height()));
         
        }
    }
    
  /**
   *  starveTime() returns the number of timesteps sharks survive without food.
   *  @return the number of timesteps sharks survive without food.
   */

  public int starveTime() {
    return sharkstarvetime;
  }

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
    
    if (cellContents(x,y) == EMPTY){
        currentocean[wraparoundx(x)][wraparoundy(y)] = FISH;
    }       
  }

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
   
    if (cellContents(x,y)==EMPTY){
        currentocean[wraparoundx(x)][wraparoundy(y)] = SHARK;
        hungrysharktable[wraparoundx(x)][wraparoundy(y)] = sharkstarvetime;
    } 
  }

  /**
   *  cellContents() returns EMPTY if cell (x, y) is empty, FISH if it contains
   *  a fish, and SHARK if it contains a shark.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int cellContents(int x, int y) {
    
    if (currentocean[wraparoundx(x)][wraparoundy(y)]==EMPTY){
        return EMPTY;
    } else if(currentocean[wraparoundx(x)][wraparoundy(y)] == FISH){
        return FISH;
    } else if(currentocean[wraparoundx(x)][wraparoundy(y)] == SHARK){
        return SHARK;
    } else{
        
        System.exit(0);
        return 0;
  }
  }

  private void neighbours(int x,int y){
      fishcount=0;
      sharkcount=0;
      neighbourarray = new int[8];
      neighbourarray[0] = this.cellContents((x+1),(y+1));
      neighbourarray[1] = this.cellContents((x+1),(y));
      neighbourarray[2] = this.cellContents((x+1),(y-1));
      neighbourarray[3] = this.cellContents((x),(y-1));
      neighbourarray[4] = this.cellContents((x-1),(y-1));
      neighbourarray[5] = this.cellContents((x-1),(y));
      neighbourarray[6] = this.cellContents((x-1),(y+1));
      neighbourarray[7] = this.cellContents((x),(y+1));
      for(int ncount=0; ncount<8;ncount++){
          if (neighbourarray[ncount]==FISH){
              fishcount++;
          } else if(neighbourarray[ncount]==SHARK){
              sharkcount++;
          } 
  }
  }
  
  /**
   *  timeStep() performs a simulation timestep as described in README.
   *  @return an ocean representing the elapse of one timestep.
   */

    public Ocean timeStep() {
        Ocean.timestepno++;
        Ocean newOcean = new Ocean(width(), height(), starveTime());
        for (int xcounter2 = 0; xcounter2 < width(); xcounter2++) {
            for (int ycounter2 = 0; ycounter2 < height(); ycounter2++) {
                neighbours(wraparoundx(xcounter2),wraparoundy(ycounter2));
                if (cellContents(xcounter2,ycounter2) == SHARK) {
                    if (fishcount != 0) {
                        newOcean.addShark(xcounter2, ycounter2,starveTime());
                        
                    } else {
                        if (hungrysharktable[wraparoundx(xcounter2)][wraparoundy(ycounter2)] == 0) {
                            newOcean.currentocean[wraparoundx(xcounter2)][wraparoundy(ycounter2)] = EMPTY;
                            newOcean.hungrysharktable[wraparoundx(xcounter2)][wraparoundy(ycounter2)] = 0;
                        } else {
                            newOcean.addShark(wraparoundx(xcounter2),wraparoundy(ycounter2),(hungrysharktable[wraparoundx(xcounter2)][wraparoundy(ycounter2)]-1));
                            
                        }
                    }
                }
                else if (cellContents(xcounter2,ycounter2) == FISH) {
                    if (sharkcount > 1){
                        newOcean.addShark(xcounter2,ycounter2,starveTime());
                        
                    }
                    else if (sharkcount != 0) {
                        newOcean.currentocean[wraparoundx(xcounter2)][wraparoundy(ycounter2)] = EMPTY;
                    } else if(sharkcount==0){
                        newOcean.currentocean[wraparoundx(xcounter2)][wraparoundy(ycounter2)]=this.currentocean[wraparoundx(xcounter2)][wraparoundy(ycounter2)];
                    }

                } else if(cellContents(xcounter2,ycounter2)==EMPTY){
                    if (fishcount >= 2 && sharkcount <= 1){
                        newOcean.addFish(xcounter2,ycounter2);
                    } else if(fishcount>=2 && sharkcount>=2){
                        newOcean.addShark(xcounter2,ycounter2,starveTime());
                       
                    } else if(fishcount<2){
                        newOcean.currentocean[wraparoundx(xcounter2)][wraparoundy(ycounter2)]=EMPTY;
                    }
                }


            }
        }
        
        return newOcean;      
    }

  /**
   *  The following method is required for Part II.
   */

  /**
   *  addShark() (with three parameters) places a shark in cell (x, y) if the
   *  cell is empty.  The shark's hunger is represented by the third parameter.
   *  If the cell is already occupied, leave the cell as it is.  You will need
   *  this method to help convert run-length encodings to Oceans.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   *  @param feeding is an integer that indicates the shark's hunger.  You may
   *         encode it any way you want; for instance, "feeding" may be the
   *         last timestep the shark was fed, or the amount of time that has
   *         passed since the shark was last fed, or the amount of time left
   *         before the shark will starve.  It's up to you, but be consistent.
   */

  public void addShark(int x, int y, int feeding) {
    if (cellContents(x,y)==EMPTY){
        currentocean[wraparoundx(x)][wraparoundy(y)]=SHARK;
        sharkhunger = feeding;
        hungrysharktable[wraparoundx(x)][wraparoundy(y)] = sharkhunger;
    }      
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  sharkFeeding() returns an integer that indicates the hunger of the shark
   *  in cell (x, y), using the same "feeding" representation as the parameter
   *  to addShark() described above.  If cell (x, y) does not contain a shark,
   *  then its return value is undefined--that is, anything you want.
   *  Normally, this method should not be called if cell (x, y) does not
   *  contain a shark.  You will need this method to help convert Oceans to
   *  run-length encodings.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int sharkFeeding(int x, int y) {
    if (cellContents(x,y)==SHARK){
        return this.hungrysharktable[wraparoundx(x)][wraparoundy(y)];
    } else{
        return 0;
    }
  }

}
