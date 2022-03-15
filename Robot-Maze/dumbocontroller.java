/*
* File: DumboController.java
* Created: 17 September 2002, 00:34
* Author: Stephen Jarvis
*/

import uk.ac.warwick.dcs.maze.logic.IRobot;

public class DumboController{

	int random_cnt = 0;

	public void controlRobot(IRobot robot) {

		int randno;
		int direction = IRobot.AHEAD;
		int walls = 0;
		int step;
		int random_prob = 8;


		random_cnt++;

		while (robot.look(direction) == IRobot.WALL | random_cnt % random_prob == 0) {
		 // Select a random number within the range of 0 and 3
		 randno = (int) Math.round(Math.random()*4);

		 // Convert this to a direction
		 if (randno > 0 && randno < 1)
			direction = IRobot.LEFT;
		 else if (randno > 1 && randno < 2)
			direction = IRobot.RIGHT;
		 else if (randno > 2 && randno < 3)
			direction = IRobot.BEHIND;
		 else
			direction = IRobot.AHEAD;

		 /* cnt hits 8, robot has chosen a new direction.
			Increase the cnt once to get the robot out of this loop */
		 if(random_cnt % random_prob == 0)
			random_cnt++;
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
