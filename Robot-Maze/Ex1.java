/*
* File: DumboController.java
* Created: 17 September 2002, 00:34
* Author: Stephen Jarvis
*/


/*
Avoiding collisions:
Using the while(true) loop, only when robot.look(direction)!= IRobot.WALL(no collisions), the loop can finish.

Why did you design your code the way you did?
Using loops to make the code more concise.
*/

import uk.ac.warwick.dcs.maze.logic.IRobot;

public class Ex1{

	public void controlRobot(IRobot robot) {

		int randno;
		int direction;
		int walls = 0;


		// Select a random number
		while(true){
			randno = (int) Math.round(Math.random()*3);
			// Convert this to a direction
			if (randno == 0)
			direction = IRobot.LEFT;
			else if (randno == 1)
			direction = IRobot.RIGHT;
			else if (randno == 2)
			direction = IRobot.BEHIND;
			else
			direction = IRobot.AHEAD;

			if (robot.look(direction)!= IRobot.WALL) break;
		}

		robot.face(direction); /* Face the robot in this direction */

		for (int i = IRobot.AHEAD; i <= IRobot.LEFT; i ++){
			if (robot.look(i) == IRobot.WALL){
				walls = walls + 1;
			}
		}
		String[] status = new String[] {"at a crossroad", "at a junction", "down a corridor", "at a deadend"};

		//print the direction
		if (direction == IRobot.LEFT){
			System.out.println("I’m going left" + " " + status[walls]);
		}
		else if(direction == IRobot.RIGHT){
			System.out.println("I’m going right" + " " + status[walls]);
		}
		else if(direction == IRobot.BEHIND){
			System.out.println("I’m going backwards" + " " + status[walls]);
		}
		else if(direction == IRobot.AHEAD){
			System.out.println("I’m going forward" + " " +status[walls]);
		}
		//end


	}

}
