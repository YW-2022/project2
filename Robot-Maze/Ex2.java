/*
* File: DumboController.java
* Created: 17 September 2002, 00:34
* Author: Stephen Jarvis
*/

/*
Fair probabilities:
Change Math.round() to Math.floor()
Math.floor(): Returns the largest integral value less than or equal to the specified number.
Because Math.round() rounds a value to the nearest integer or to the specified number of fractional digits:

0<randno<0.5: LEFT
0.5<=randno<1.5: RIGHT
1.5<=randno<2.5: BEHIND
2.5<=randno<3: AHEAD

Therefore, the probabilities are uneven.

However, the Math.floor():

0<randno<1: LEFT
1<=randno<2: RIGHT
2<=randno<3: BEHIND
3<=randno<4: AHEAD

Therefore, it can ensure fair probabilities.


Incorporate the 1-in-8 chance into your design:

Only if robot.look() is wall or step equals to 8, the robot regenerate a new randno and choose a direction.


*/


import uk.ac.warwick.dcs.maze.logic.IRobot;

public class Ex2{

	public void controlRobot(IRobot robot) {

		int randno;
		int direction;
		int walls = 0;
		int step;


		// Select a random number
		do {
			randno = (int) Math.floor(Math.random()*4);
			/*
			//change Math.round() to Math.floor
			Math.round(): Rounds a value to the nearest integer or to the specified number of fractional digits.
			Math.floor(): Returns the largest integral value less than or equal to the specified number.
			*/
			// Convert this to a direction
			if (randno == 0)
			direction = IRobot.LEFT;
			else if (randno == 1)
			direction = IRobot.RIGHT;
			else if (randno == 2)
			direction = IRobot.BEHIND;
			else
			direction = IRobot.AHEAD;



		} while (robot.look(direction) == IRobot.WALL);

			//The robot selecting a random direction on average every 1 in 8 moves.
			step = (int) Math.round(Math.random()*8);
			//if the robot.look() is wall, or step equals 8, the robot will rechoose the direction.
			if (robot.look(IRobot.AHEAD) == IRobot.WALL || step == 7){
				robot.face(direction); /* Face the robot in this direction */
			}



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
