import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;

public class MultiPlayer extends JPanel
{
	Snake snake1, snake2;
	Food food1, food2;
	ArrayList<Obstacle> obstacle;
	Image background = null;

	final int SPECIFIED_LENGTH = 5;
	Timer timer, timeOfGame;
	boolean gameOver;
	int freezeTime1, freezeTime2;
	int slowTime1, slowTime2;
	boolean freeze1, freeze2;
	String winner;
	int timeLimit;
	int obstacleNumber;
	InfoMulti info = null;
	boolean temp = false; // to show the info panel
	boolean pause = false; // pauses the game
	boolean obstaclesCreated = false;

	// constructor initializes variables
    public MultiPlayer(Snake s1, Snake s2)
    {
    	snake1 = s1;
    	snake2 = s2;

    	setBackground(Color.white);
    	setPreferredSize(new Dimension(1400, 800));
    	setFocusable(true);

		// initializations
		gameOver = false;
		freezeTime1 = 0;
		freezeTime2 = 0;
		slowTime1 = 0;
		slowTime2 = 0;
		freeze1 = false; freeze2 = false;
		winner = "";

		// start game
    	timer = new Timer(50, new TimerListener());
    	timeOfGame = new Timer(1000, new ClockListener());


		// set up Key Bindings
    	getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "up1");
    	getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "down1");
    	getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "right1");
    	getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "left1");

		Action turnUp1 = new AbstractAction() {
   			public void actionPerformed(ActionEvent e) {
        		snake1.turnUp();
    		}
		};
		Action turnDown1 = new AbstractAction() {
   			public void actionPerformed(ActionEvent e) {
        		snake1.turnDown();
    		}
		};
		Action turnRight1 = new AbstractAction() {
   			public void actionPerformed(ActionEvent e) {
        		snake1.turnRight();
    		}
		};
		Action turnLeft1 = new AbstractAction() {
   			public void actionPerformed(ActionEvent e) {
        		snake1.turnLeft();
    		}
		};

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "up2");
    	getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "down2");
    	getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "right2");
    	getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "left2");

    	Action turnUp2 = new AbstractAction() {
   			public void actionPerformed(ActionEvent e) {
        		snake2.turnUp();
    		}
		};
		Action turnDown2 = new AbstractAction() {
   			public void actionPerformed(ActionEvent e) {
        		snake2.turnDown();
    		}
		};
		Action turnRight2 = new AbstractAction() {
   			public void actionPerformed(ActionEvent e) {
        		snake2.turnRight();
    		}
		};
		Action turnLeft2 = new AbstractAction() {
   			public void actionPerformed(ActionEvent e) {
        		snake2.turnLeft();
    		}
		};

    	getActionMap().put("up1", turnUp1);
    	getActionMap().put("down1", turnDown1);
    	getActionMap().put("right1", turnRight1);
    	getActionMap().put("left1", turnLeft1);

    	getActionMap().put("up2", turnUp2);
    	getActionMap().put("down2", turnDown2);
    	getActionMap().put("right2", turnRight2);
    	getActionMap().put("left2", turnLeft2);
    }

	// creates obstacles and foods only once then only draws them and starts the timer
    public void paintComponent(Graphics page)
    {
    	super.paintComponent(page);
    	if (!gameOver)
    	{
    		if (background != null)
    			page.drawImage(background, 0, 0, 1400, 800, null);

			// create obstacles
			if (!obstaclesCreated)
			{
				obstacle = new ArrayList<Obstacle>();
				Obstacle temp;
				obstaclesCreated = true;
				for (int i = 0; i < obstacleNumber; i++)
				{
					do
					{
						temp = new Obstacle();
					} while(temp.isIntersect(obstacle) || temp.isOnSnake(snake1) || temp.isOnSnake(snake2));

				obstacle.add(temp);
				}

				// create foods
		    	food1 = new Food(0, 0, snake1.getColor(), 0);
		    	do
				{
					food1 = food1.createNewFood(snake1.getColor(), 0);
				}while(food1.isOnSnake(snake1) || food1.isOnSnake(snake2) || food1.isOnObstacle(obstacle));

				food2 = new Food(0, 0, snake2.getColor(), 0);
		    	do
				{
					food2 = food2.createNewFood(snake2.getColor(), 0);
				}while(food2.isOnSnake(snake1) || food2.isOnSnake(snake2) || food2.isOnObstacle(obstacle));
			}

			for (Obstacle obs: obstacle)
				obs.draw(page);

			food1.draw(page);
			food2.draw(page);

			snake1.draw(page);
    		snake2.draw(page);
    		if (info != null && temp)
    		{
    			info.setLocation(1400, 0);
    			info.paintComponent(page);
    			temp = false;
    		}

    	}
    	else // if game is over
    	{
			page.setFont(new Font("", Font.PLAIN, 80));
    		page.drawString(winner, 400, 370);
    	}

    }

	// starts the timers and game
    public void start()
    {
    	timer.start();
    	timeOfGame.start();
    }

    public int getTimeLimit()
    {
    	return timeLimit;
    }

    public void setTimeLimit(int time)
    {
    	timeLimit = time;
    }

    public void setObstacleNumber(int number)
    {
    	obstacleNumber = number;
    }

	public Snake getSnake1()
	{
		return snake1;
	}

	public Snake getSnake2()
	{
		return snake2;
	}

	// sets the info panel of the multiplayer game
	public void setInfo (InfoMulti newInfo)
	{
		info = newInfo;
		temp = true;
	}

	public void setPause(boolean b)
	{
		pause = b;
	}

	public boolean getPause()
	{
		return pause;
	}

	public void setBackground(Image i)
	{
		background = i;
	}

	// called every 50 ms
	// moves snakes and make changes corresponding to this movement
	private class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (!pause)
			{
				// update the times
				if (slowTime1 > 0)
					slowTime1--;
				if (slowTime2 > 0)
					slowTime2--;

				if (freezeTime1 > 0)
					freezeTime1--;
				if (freezeTime2 > 0)
					freezeTime2--;


				// move the snakes
				if ((slowTime1 == 0 || (slowTime1 > 0 && slowTime1 % 2 == 0)) && freezeTime1 == 0)
					if (freeze1)
					{
						freeze1 = false;
						snake1.moveOneBack();
						snake1.turnUp();
						snake1.move();
						for(int i = 0; i < obstacle.size(); i++)
							if (snake1.isCollide(obstacle.get(i)) || snake1.isBiteItself())
								freeze1 = true;

						if (freeze1)
						{
							freeze1 = false;
							snake1.moveOneBack();
							snake1.turnRight();
							snake1.move();
							for(int i = 0; i < obstacle.size(); i++)
								if (snake1.isCollide(obstacle.get(i)) || snake1.isBiteItself())
									freeze1 = true;

							if (freeze1)
							{
								freeze1 = false;
								snake1.moveOneBack();
								snake1.turnDown();
								snake1.move();
								for(int i = 0; i < obstacle.size(); i++)
									if (snake1.isCollide(obstacle.get(i)) || snake1.isBiteItself())
										freeze1 = true;

								if (freeze1)
								{
									freeze1 = false;
									snake1.moveOneBack();
									snake1.turnLeft();
									snake1.move();
									for(int i = 0; i < obstacle.size(); i++)
										if (snake1.isCollide(obstacle.get(i)) || snake1.isBiteItself())
											freeze1 = true;
								}
							}
						}
					}
					else
						snake1.move();

				if ((slowTime2 == 0 || (slowTime2 > 0 && slowTime2 % 2 == 0)) && freezeTime2 == 0)
					if (freeze2)
					{
						freeze2 = false;
						snake2.moveOneBack();
						snake2.turnUp();
						snake2.move();
						for(int i = 0; i < obstacle.size(); i++)
							if (snake2.isCollide(obstacle.get(i)) || snake2.isBiteItself())
								freeze2 = true;

						if (freeze2)
						{
							freeze2 = false;
							snake2.moveOneBack();
							snake2.turnRight();
							snake2.move();
							for(int i = 0; i < obstacle.size(); i++)
								if (snake2.isCollide(obstacle.get(i)) || snake2.isBiteItself())
									freeze2 = true;

							if (freeze2)
							{
								freeze2 = false;
								snake2.moveOneBack();
								snake2.turnDown();
								snake2.move();
								for(int i = 0; i < obstacle.size(); i++)
									if (snake2.isCollide(obstacle.get(i)) || snake2.isBiteItself())
										freeze2 = true;

								if (freeze2)
								{
									freeze2 = false;
									snake2.moveOneBack();
									snake2.turnLeft();
									snake2.move();
									for(int i = 0; i < obstacle.size(); i++)
										if (snake2.isCollide(obstacle.get(i)) || snake2.isBiteItself())
											freeze2 = true;
								}
							}
						}

					}
					else
						snake2.move();

				// check whether snakes eat any food
				if (snake1.isEat(food1))
				{
					snake1.eat(food1);
					do
					{
						food1 = food1.createNewFood(snake1.getColor(), 0);
					}while(food1.isOnSnake(snake1) || food1.isOnSnake(snake2) || food1.isOnObstacle(obstacle));
				}

				if (snake1.isEat(food2))
				{
					slowTime2 = 200;
					do
					{
						food2 = food2.createNewFood(snake2.getColor(), 0);
					}while(food2.isOnSnake(snake1) || food2.isOnSnake(snake2) || food2.isOnObstacle(obstacle));

				}

				if (snake2.isEat(food2))
				{
					snake2.eat(food2);
					do
					{
						food2 = food2.createNewFood(snake2.getColor(), 0);
					}while(food2.isOnSnake(snake1) || food2.isOnSnake(snake2) || food2.isOnObstacle(obstacle));
				}

				if (snake2.isEat(food1))
				{
					slowTime1 = 200;
					do
					{
						food1 = food1.createNewFood(snake1.getColor(), 0);
					}while(food1.isOnSnake(snake1) || food1.isOnSnake(snake2) || food1.isOnObstacle(obstacle));

				}


				// check whether snakes bite each other or themselves
				if (snake1.isBiteItself())
					snake1.cut();
				if (snake1.isBite(snake2))
					snake2.cut();

				if (snake2.isBiteItself())
					snake2.cut();
				if (snake2.isBite(snake1))
					snake1.cut();

				// check whether snakes hit an obstacle or not
				for(int i = 0; i < obstacle.size(); i++)
				{
					if (!freeze1)
						if (snake1.isCollide(obstacle.get(i)))
						{
							freezeTime1 = 100;
							freeze1 = true;
						}

					if (!freeze2)
						if (snake2.isCollide(obstacle.get(i)))
						{
							freezeTime2 = 100;
							freeze2 = true;
						}
				}

				// check game is over or not
				if (snake1.getDraw() || (snake1.getSnake().size() < SPECIFIED_LENGTH && snake2.getSnake().size() < SPECIFIED_LENGTH))
				{
					winner = "It is a draw!";
					gameOver = true;
				}
				else if (snake1.getSnake().size() < SPECIFIED_LENGTH && snake2.getSnake().size() >= SPECIFIED_LENGTH)
				{
					winner = snake2.getName() + " is the winner!";
					gameOver = true;
				}
				else if (snake2.getSnake().size() < SPECIFIED_LENGTH && snake1.getSnake().size() >= SPECIFIED_LENGTH)
				{
					winner = snake1.getName() + " is the winner!";
					gameOver = true;
				}
				if (timeLimit == 0)
				{
					gameOver = true;

					if (snake1.getSnake().size() > snake2.getSnake().size())
						winner = snake1.getName() + " is the winner!";
					else if (snake1.getSnake().size() < snake2.getSnake().size())
						winner = snake2.getName() + " is the winner!";
					else
						winner = "It is a draw!";
				}

				if (gameOver)
					timer.stop();
			}
			repaint();
		}
	}

	// calculates the time left to end the game
	private class ClockListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (!pause)
			{
				if (timeLimit == 0)
					timeOfGame.stop();
				if (gameOver)
					timeOfGame.stop();
				timeLimit--;
			}
		}
	}

}