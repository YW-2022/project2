/*
 * File:    Broken	.java
 * Created: 7 September 2001
 * Author:  Stephen Jarvis
 */

/*Preamble:
First: isTargetEast() and isTargetNorth() use robot.getLocation() compared with robot.getTargetLocation() to return 0, -1 and 1.
This can help determine the relative position of robot and target.

Second: lookHeading() can estimate the heading(WALL, PASSAGE and BEENFORE)

Third: headingController() can determine the probability of choosing different directions by the direction of the target relative to the robot.
For example: if the target is southeast relative to the robot, it is more likely to choose east or south.
  if(isTargetEast(robot) == 1 && isTargetNorth(robot) == -1){
  do {
  randno = (int) Math.round(Math.random()*3);
  if ( randno == 0)
  heading = IRobot.WEST;
  else if (randno == 1)
  heading = IRobot.EAST;
  else if (randno == 2)
  heading = IRobot.SOUTH;
  else
  heading = IRobot.NORTH;
  } while (lookHeading(robot, heading) == IRobot.WALL);
}
Because use Math.round(Math.random()*3) can cause different probabilities and it is more likely to choose between randno==1 and randno==2. Therefore, it is more likely to choose EAST and SOUTH;

Finally: controlRobot() can setHeading(heading). The small robot moves forward according to the selected direction.

*/


import uk.ac.warwick.dcs.maze.logic.IRobot;

public class Ex3{



  public void controlRobot(IRobot robot) {
    int heading;


    heading = headingController(robot);
    ControlTest.test(heading, robot);
    robot.setHeading(heading);


  }

  public void reset(){
    ControlTest.printResults();
  }



  private byte isTargetNorth(IRobot robot){
    byte result_y = 0;
    //Use the method getLocation() and getTargetLocation(), to compare the y-coordinates of robot and target.
    if (robot.getLocation().y < robot.getTargetLocation().y){
       return result_y = -1;
    }
    else if (robot.getLocation().y > robot.getTargetLocation().y){
       return result_y = 1;
    }
    else{
       return result_y = 0;
    }
  }

  private byte isTargetEast(IRobot robot){
    byte result_x = 0;

    if (robot.getLocation().x < robot.getTargetLocation().x){
       return result_x = 1;
    }
    else if (robot.getLocation().x > robot.getTargetLocation().x){
       return result_x = -1;
    }

    else{
       return result_x = 0;
    }
  }


  private int lookHeading(IRobot robot, int heading){
    int look_heading;
    if (heading == robot.getHeading()){
      look_heading = robot.look(IRobot.AHEAD);
    }
    else if (heading == robot.getHeading() - 1 || heading == robot.getHeading() + 3){
      look_heading = robot.look(IRobot.LEFT);
    }
    else if (heading == robot.getHeading() + 1 || heading == robot.getHeading() - 3){
      look_heading = robot.look(IRobot.RIGHT);
    }
    else{
      look_heading = robot.look(IRobot.BEHIND);
    }
    return look_heading;
  }


  private int headingController(IRobot robot){
    int heading;
    int randno;
    int direction;

    heading = IRobot.NORTH;
    if(isTargetEast(robot) == 0){
      if(isTargetNorth(robot) == 1){
        do {
        randno = (int) Math.round(Math.random()*3);

        if ( randno == 0)
        heading = IRobot.WEST;
        else if (randno == 1)
        heading = IRobot.EAST;
        else if (randno == 2)
        heading = IRobot.NORTH;
        else
        heading = IRobot.SOUTH;
        } while (lookHeading(robot, heading) == IRobot.WALL);
      }
      else if(isTargetNorth(robot) == -1){
        do {
        randno = (int) Math.round(Math.random()*3);

        if ( randno == 0)
        heading = IRobot.WEST;
        else if (randno == 1)
        heading = IRobot.EAST;
        else if (randno == 2)
        heading = IRobot.SOUTH;
        else
        heading = IRobot.NORTH;
        } while (lookHeading(robot, heading) == IRobot.WALL);
      }
    }
    else if(isTargetNorth(robot) == 0){
      if(isTargetEast(robot) == 1){
        do {
        randno = (int) Math.round(Math.random()*3);

        if ( randno == 0)
        heading = IRobot.WEST;
        else if (randno == 1)
        heading = IRobot.EAST;
        else if (randno == 2)
        heading = IRobot.NORTH;
        else
        heading = IRobot.SOUTH;
        } while (lookHeading(robot, heading) == IRobot.WALL);
      }
      else if(isTargetEast(robot) == -1){
        do {
        randno = (int) Math.round(Math.random()*3);

        if ( randno == 0)
        heading = IRobot.EAST;
        else if (randno == 1)
        heading = IRobot.WEST;
        else if (randno == 2)
        heading = IRobot.NORTH;
        else
        heading = IRobot.SOUTH;
        } while (lookHeading(robot, heading) == IRobot.WALL);
      }
    }
    else if(isTargetEast(robot) == 1 && isTargetNorth(robot) == 1){

      do {
      randno = (int) Math.round(Math.random()*3);

      if ( randno == 0)
      heading = IRobot.WEST;
      else if (randno == 1)
      heading = IRobot.EAST;
      else if (randno == 2)
      heading = IRobot.NORTH;
      else
      heading = IRobot.SOUTH;
      } while (lookHeading(robot, heading) == IRobot.WALL);
    }
    else if(isTargetEast(robot) == -1 && isTargetNorth(robot) == 1){

      do {
      randno = (int) Math.round(Math.random()*3);

      if ( randno == 0)
      heading = IRobot.EAST;
      else if (randno == 1)
      heading = IRobot.WEST;
      else if (randno == 2)
      heading = IRobot.NORTH;
      else
      heading = IRobot.SOUTH;
      } while (lookHeading(robot, heading) == IRobot.WALL);
    }
    else if(isTargetEast(robot) == 1 && isTargetNorth(robot) == -1){

      do {
      randno = (int) Math.round(Math.random()*3);

      if ( randno == 0)
      heading = IRobot.WEST;
      else if (randno == 1)
      heading = IRobot.EAST;
      else if (randno == 2)
      heading = IRobot.SOUTH;
      else
      heading = IRobot.NORTH;
      } while (lookHeading(robot, heading) == IRobot.WALL);
    }
    else if(isTargetEast(robot) == -1 && isTargetNorth(robot) == -1){

      do {
      randno = (int) Math.round(Math.random()*3);

      if ( randno == 0)
      heading = IRobot.EAST;
      else if (randno == 1)
      heading = IRobot.WEST;
      else if (randno == 2)
      heading = IRobot.SOUTH;
      else
      heading = IRobot.NORTH;
      } while (lookHeading(robot, heading) == IRobot.WALL);
    }

    while(lookHeading(robot, heading) == IRobot.WALL){
      heading = randomHeading();
    }

    return heading;
  }



  private int randomHeading(){
    int heading;
    int randno;

    randno = (int) Math.floor(Math.random() * 4);

    if (randno < 1){
      heading = IRobot.NORTH;
    }
    else if (randno < 2){
      heading = IRobot.SOUTH;
    }
    else if (randno < 3){
      heading = IRobot.WEST;
    }
    else{
      heading = IRobot.EAST;
    }

    return heading;
  }

}
