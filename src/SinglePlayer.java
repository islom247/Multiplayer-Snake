import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;

public class SinglePlayer extends JPanel
{
	Snake snake;
	Food food, specialFood;
	ArrayList<Obstacle> obstacle;

	ArrayList<Rectangle> ekans;
	Timer timer;
	int score;
	int specialTime; // When snake eats 5 normal foods, special food will appear
	int timeLeftForSpecial;
	boolean gameOver;
	InfoSingle infoS = null;
	boolean temp = false;
	boolean pause = false;

	// constructor creates 10 obstacles, 1 food and initializes variables and start timer
    public SinglePlayer(Snake s)
    {
    	// set screen properties
    	snake = s;
    	setBackground(Color.white);
    	setPreferredSize(new Dimension(1400, 800));
    	setFocusable(true);

		// create obstacles
    	obstacle = new ArrayList<Obstacle>();
		Obstacle temp;
		for (int i = 0; i < 10; i++)
		{
			do
			{
				temp = new Obstacle();
			}while(temp.isIntersect(obstacle) || temp.isOnSnake(snake));

			obstacle.add(temp);
		}

		// create food
    	food = new Food(0, 0, Color.red, 0);
    	do
		{
			food = food.createNewFood(snake.getColor(), 0);
		}while(food.isOnSnake(snake) || food.isOnObstacle(obstacle));

		specialFood = new Food(-10, -10, Color.black, 0);

		// initializations
		score = 0;
		specialTime = 0;
		timeLeftForSpecial = 0;
		gameOver = false;

		//addKeyListener(this);

		// start game
    	timer = new Timer(50, new TimeListener());
    	timer.start();

    	getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "up");
    	getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "down");
    	getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "right");
    	getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "left");

		Action turnUp = new AbstractAction() {
   			public void actionPerformed(ActionEvent e) {
        		snake.turnUp();
    		}
		};
		Action turnDown = new AbstractAction() {
   			public void actionPerformed(ActionEvent e) {
        		snake.turnDown();
    		}
		};
		Action turnRight = new AbstractAction() {
   			public void actionPerformed(ActionEvent e) {
        		snake.turnRight();
    		}
		};
		Action turnLeft = new AbstractAction() {
   			public void actionPerformed(ActionEvent e) {
        		snake.turnLeft();
    		}
		};

		getActionMap().put("up", turnUp);
    	getActionMap().put("down", turnDown);
   		getActionMap().put("right", turnRight);
   		getActionMap().put("left", turnLeft);
    }

	// draws snake, foods and obstacles on the same page
	public void paintComponent(Graphics page)
    {
    	super.paintComponent(page);

    	if (!isGameOver())
    	{
    		snake.draw(page);
			food.draw(page);
			specialFood.draw(page);
			for (Obstacle obs: obstacle)
				obs.draw(page);

			if (infoS != null && temp)
    		{
    			infoS.setLocation(1400, 0);
    			infoS.paintComponent(page);
    			temp = false;
    		}
    	}
    	else // if game is Over
    	{
			page.setFont(new Font("", Font.PLAIN, 80));
    		page.drawString("Game is Over!", 500, 370);

			page.setFont(new Font("", Font.PLAIN, 40));
    		page.drawString("Your Score: " + score, 500, 500);

    	}

    }

	public Snake getSnake()
	{
		return snake;
	}

	public int getScore()
	{
		return score;
	}

	public boolean isGameOver()
	{
		return gameOver;
	}

	public int getSpecialTime()
	{
		return specialTime;
	}

	public int getTimeLeftForSpecial()
	{
		return timeLeftForSpecial;
	}

	// sets the info panel for the single player game
	public void setInfo (InfoSingle is)
	{
		infoS = is;
		temp = true;
	}

	public void setPause (boolean b)
	{
		pause = b;
	}

	public boolean getPause()
	{
		return pause;
	}

	// called every 50 ms
	// moves the snake and make corresponding changes
	private class TimeListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (!pause)
			{
				if (timeLeftForSpecial > 0)
					timeLeftForSpecial--;
				else
					specialFood = new Food(-10, -10, Color.black, 0);

				snake.move();

				if (snake.isEat(food))
				{
					snake.eat(food);
					do
					{
						food = food.createNewFood(snake.getColor(), 0);
					}while(food.isOnSnake(snake) || food.isOnObstacle(obstacle));

					score += 5;
					specialTime++;

					if (specialTime == 5)
					{
						specialTime = 0;
						do
						{
							specialFood = specialFood.createNewFood(Color.black, 0);
						}while(specialFood.isOnSnake(snake) || food.isOnObstacle(obstacle));
						timeLeftForSpecial = 200;
					}
				}
				if (snake.isEat(specialFood))
				{
					snake.eat(specialFood);
					specialFood = new Food(-10, -10, Color.black, 0);
					score += timeLeftForSpecial * 3;
					timeLeftForSpecial = 0;
				}

				// check game is over or not
				if (snake.isBiteItself())
					gameOver = true;

				for(int i = 0; i < obstacle.size(); i++)
					if (snake.isCollide(obstacle.get(i)))
						gameOver = true;
			}
			repaint();
		}
	}

}