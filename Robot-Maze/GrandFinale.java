import uk.ac.warwick.dcs.maze.logic.IRobot;
import java.util.*;

/*
Preamble:

This GrandFinale controller can store the data of the first run and avoid unnecessary loops when next visit the maze.

The same Preamble like Ex1:

The method nonwallExits(),beenbeforeExits(),passageExits() mainly utilize for loop:

Firstly,  initialize int number_of_XXX = 0;

Secondly, using a for loop (int i = IRobot.AHEAD; i <= IRobot.LEFT; i ++), then use the robot.look() method, if robot.look(i) is (not) equal to IRobot.WALL/IRobot.BEENBEFORE/IRobot.PASSAGE, number_of_XXX = number_of_XXX + 1;

Finally, return then the number_of_XXX.

The method deadEnd(),corridor() also mainly use for loop:

Firstly, initialize int direction = 0;

Secondly, using a for loop (int i = IRobot.AHEAD; i <= IRobot.LEFT; i ++), then also use if condition and the robot.look(i) to determine the direction;

Finally, return the direction.

The method junction(),crossroads() should determine numOfPassageExits > 0 or number_of_passageExits < 0:

If passage exits, then randomly selecting an unexplored exit. Therefore, call method of randomchoosefromPassageExits().

If there are no unexplored exits, randomly selecting a direction that doesn’t cause a collision.

Then return the direction.

The method randomchoosefromPassageExits()/ randomchoosefromnonwallExits():

Randomly choosing from passageExits. Utilize ArrayList to store the direction(Must satisfy robot.look() equals to IRobot.PASSAGE). Meanwhile, generate random number. Let position equals to the number. Then direction equals to ranchopass.get(position)/ranchononwall. Finally, return the direaction.

The method controlRobot():

Using if else condition to determine which explore method to use:

if explorerMode = 1, then choose exploreControl.

if explorerMode = 0, then choose backtrackControl.

The method exploreControl()/backtrackControl use switch case:

When nonwallExits =  1 or 0, direction = deadEnd(robot)/corridor(robot) respectively. Meanwhile, if direction = deadEnd, switch the explorerMode = 0, enter the backtrackControl mode.

Use if else condition to determine beenbeforeExits/nonwallExits, if(beenbeforeExits(robot) == 1), then call the recordJunction() method, if (passageExits(robot) > 0), the direction = crossroads(robot), and switch the mode to exploreControl. Else direction = IRobot.BEHIND, and switch explorerMode = 0;/ Else call the searchJunction() method, and reverse the direction.\

Finally, call the face(direction) method of robot.

RobotData can store the information of junction. It has the printJunction(),searchJunction(),recordJunction(), resetJunctionCounter() method.

The JunctionRecorder Object can store the number of juncX, juncY, heading.

The printJunction() method use if else condition to determine junctions[junctionCounter].arrived is equal to NORTH, EAST, SOUTH and WEST; then print the x-coordinate, y-coordinate and heading.

And use the same Tr ́emaux theory like in Ex3 to visit the loopy maze.

*/


public class GrandFinale{

  private int pollRun = 0;  // Incremented after each pass
  private RobotData robotData;
  private int explorerMode; // 1 = explore, 0 = backtrack

  private int[][] coor = new int[400][400]; //An integer array is used to store the X and Y coordinates of the robot. 400 is the maximum of the X and Y coordinates.


  //Utilize exits variable to store the number of nonwallExits. Then call subsidiary method by comparing the number of nonwallExits with 1234.
  public void controlRobot(IRobot robot){

    // On the first move of the first run of a new maze
    if ((robot.getRuns() == 0) && (pollRun == 0 || pollRun == 1)){
      robotData = new RobotData(); //reset the data store
      explorerMode = 1; //set explorerMode = 1
    }
    pollRun++; // Increment pollRun so that the data is not
    // reset each time the robot moves

    //if getRuns()>0, then explorerMode, and will call revisitMaze() method later.
    if(robot.getRuns() > 0){
      explorerMode = 2;
    }
    //if explorerMode = 1, then call the exploreControl() method
    if(explorerMode == 1){
      exploreControl(robot);
    }
    //if explorerMode = 0, then call the backtrackControl() method
    else if(explorerMode == 0){
      backtrackControl(robot);
    }
    //call revisitMaze() method
    else{
      revisitMaze(robot);
    }
  }

  //The exploreControl() method use switch case to determine the number of nonwallExits and call the corresponding methods. Finally, set the robot.face() and get the current X and Y coordinates of the robot.
  public void exploreControl(IRobot robot){

    int direction;

		switch(nonwallExits(robot)){
		case 1: direction = deadEnd(robot); explorerMode = 0; break;
		case 2: direction = corridor(robot); break;
		default:
      //if beenbeforeExits equal to 1, then...
			if(beenbeforeExits(robot) == 1){
				robotData.recordJunction(robot.getLocation().x,robot.getLocation().y,robot.getHeading()); //record the junction(x, y and heading).
				direction = crossroads(robot);
			}
      //else, ...
			else{
				direction = IRobot.BEHIND;
				explorerMode = 0; //switch the explorerMode to 0.
			}
		}
		robot.face(direction);
    coor[robot.getLocation().x][robot.getLocation().y] = robot.getHeading();

  }
  //The backtrackControl() method can let the robot return the original junction.
  public void backtrackControl(IRobot robot){
    int direction = IRobot.AHEAD;

    switch(nonwallExits(robot)){
      case 1: direction = deadEnd(robot); break;
      case 2: direction = corridor(robot); break;
      default:
      if (passageExits(robot) > 0){
        direction = crossroads(robot);
        explorerMode = 1;
      }
      else{
        int redirection = robotData.searchJunction(robot.getLocation().x,robot.getLocation().y); //get the X and Y coordinates of the original junction.
        robot.setHeading(((redirection+2)%4) + IRobot.NORTH); //reverse the direction
      }
    }
    robot.face(direction);
    coor[robot.getLocation().x][robot.getLocation().y] = robot.getHeading();
  }

  public void revisitMaze(IRobot robot){
    robot.setHeading(coor[robot.getLocation().x][robot.getLocation().y]);
		robot.face(IRobot.AHEAD);
  }

  //Subsidiary method of deadEnd
  private int deadEnd(IRobot robot){
    int direction = 0;
    for(int i = IRobot.AHEAD; i <= IRobot.LEFT; i ++){
      if(robot.look(i) != IRobot.WALL){
        direction = i;
        break;
      }
    }
    return direction;
  }
  //Subsidiary method of corridor
  private int corridor(IRobot robot){
    int direction = 0;
    for(int i = IRobot.AHEAD; i <= IRobot.LEFT; i ++){
      if((robot.look(i) != IRobot.WALL) && (i != IRobot.BEHIND)){
        direction = i;
        break;
      }
    }
    return direction;
  }

  //Subsidiary method of junction
  private int junction(IRobot robot){
    int direction = 0;
    int numOfPassageExits = passageExits(robot);
    if(numOfPassageExits > 0){
      //If passage exits, then randomly selecting an unexplored exit. Therefore, call method of randomchoosefromPassageExits().
      direction = randomchoosefromPassageExits(robot);
    }
    else{
      //If there are no unexplored exits, randomly selecting a direction that doesn’t cause a collision.
      direction = randomchoosefromnonwallExits(robot);
    }
    return direction;
  }

  //Subsidiary method of crossroads
  private int crossroads(IRobot robot){
    int direction = 0;
    int numOfPassageExits = passageExits(robot);
    if(numOfPassageExits > 0){
      //If passage exits, then randomly selecting an unexplored exit. Therefore, call method of randomchoosefromPassageExits().
      direction = randomchoosefromPassageExits(robot);
    }
    else{
      //If there are no unexplored exits, randomly selecting a direction that doesn’t cause a collision.
      direction = randomchoosefromnonwallExits(robot);
    }
    return direction;
  }

  //Randomly choosing from passageExits. Utilize ArrayList to store the direction(Must satisfy robot.look() equals to IRobot.PASSAGE). Meanwhile, generate random number. Let position equals to the number. Then direction equals to ranchopass.get(position). Finally, return the direaction.
  private int randomchoosefromPassageExits(IRobot robot){
    ArrayList<Integer> ranchopass = new ArrayList<Integer>();
    for(int i = IRobot.AHEAD; i <= IRobot.LEFT; i ++){
      if(robot.look(i) == IRobot.PASSAGE){
        ranchopass.add(i);
      }
    }
    int position = (int)(Math.random() * ranchopass.size());
    int direction = ranchopass.get(position);
    return direction;
  }

  //Randomly choosing from nonwallExits. Utilize ArrayList to store the direction(Must satisfy robot.look() not equals to IRobot.WALL). Meanwhile, generate random number. Let position equals to the number. Then direction equals to ranchononwall.get(position). Finally, return the direaction.
  private int randomchoosefromnonwallExits(IRobot robot){
    ArrayList<Integer> ranchononwall = new ArrayList<Integer>();
    for(int i = IRobot.AHEAD; i <= IRobot.LEFT; i ++){
      if(robot.look(i) != IRobot.WALL){
        ranchononwall.add(i);
      }
    }
    int position = (int)(Math.random() * ranchononwall.size());
    int direction = ranchononwall.get(position);
    return direction;
  }

  //Return the number of passageExits. When robot.look() equals to IRobot.PASSAGE, number_of_passageExits = number_of_passageExits + 1; Then return the number.
  private int passageExits(IRobot robot){
    int number_of_passageExits = 0;
    for(int i = IRobot.AHEAD; i <= IRobot.LEFT; i ++){
      if(robot.look(i) == IRobot.PASSAGE){
        number_of_passageExits ++;
      }
    }
    return number_of_passageExits;
  }

  //Return the number of beenbeforeExits.
  private int beenbeforeExits(IRobot robot){
    int number_of_beenbeforeExits = 0;
    for(int i = IRobot.AHEAD; i <= IRobot.LEFT; i++){
      if(robot.look(i) == IRobot.BEENBEFORE){
        number_of_beenbeforeExits ++;
      }
    }
    return number_of_beenbeforeExits;
  }
  //Utilize for loop and if condition to determine when robot.look() not equal to Wall; then number_of_nonwallExits =  number_of_nonwallExits + 1. Then return the number of nonwallExits.
  private int nonwallExits(IRobot robot){
    int number_of_nonwallExits = 0;
    for(int i = IRobot.AHEAD; i <= IRobot.LEFT; i ++){
      if(robot.look(i) != IRobot.WALL){
        number_of_nonwallExits ++;
      }
    }
    return number_of_nonwallExits;
  }
  public void reset(){
    robotData.resetJunctionCounter(); //resetJunctionCounter
    explorerMode = 1;
  }

}

//This Class mainly records the content of robot'junctions.
class RobotData{
  private static int maxJunctions = 160000; //maxJunctions = 400 X 400
  private static int junctionCounter;
  private JunctionRecorder[] junctions = new JunctionRecorder[maxJunctions];

  public void resetJunctionCounter(){
    junctionCounter = 0;
    // private int explorerMode;  // 1 = explore, 0 = backtrack
  }
  public void recordJunction(int juncX, int juncY, int heading){
    JunctionRecorder JR = new JunctionRecorder(juncX, juncY, heading);
    junctions[junctionCounter] = JR;
    printJunction();
    junctionCounter++;
  }
  //This method can print the content of the junctions. It uses if..elif..else.. to determine junctions[junctionCounter].arrived == ?, so can have the effect of converting integers into strings.
  public void printJunction(){
    String dir = "";
    if(junctions[junctionCounter].arrived == IRobot.NORTH){
      dir = "NORTH";
    }
    else if(junctions[junctionCounter].arrived == IRobot.EAST){
      dir = "EAST";
    }
    else if(junctions[junctionCounter].arrived == IRobot.SOUTH){
      dir = "SOUTH";
    }
    else{
      dir = "WEST";
    }
    //Print the x, y and heading of junctions
    System.out.println("Junction " + (junctionCounter + 1) + " (x=" + junctions[junctionCounter].juncX + ",y=" + junctions[junctionCounter].juncY + ") heading " + dir);
  }
  //return the headings of junctions
  public int searchJunction(int juncX, int juncY){
    for(int i = 0; i < maxJunctions; i++){
      if(juncX == junctions[i].juncX && juncY == junctions[i].juncY)
        return junctions[i].arrived;
    }
    return -1;
  }
}

  //JunctionRecorder Object
  class JunctionRecorder{
      public int juncX;
      public int juncY;
      public int arrived;

      //Control method
      public JunctionRecorder(int juncX, int juncY, int arrived){

      this.juncX = juncX;
      this.juncY = juncY;
      this.arrived = arrived;
      }
}
