import java.util.Random;
import java.util.ArrayList;
import java.awt.*;

public class Obstacle
{
	final int OBSTACLE_LENGTH = 25;
	final int OBSTACLE_THICKNESS = 8;
	ArrayList<Rectangle> obstacle;
	Rectangle temp;
	boolean horizontal;
	int x, y;
	Random generator;

	// constructor creates an obstacle at random location
    public Obstacle()
    {
    	generator = new Random();

    	if (generator.nextInt(2) == 0)
    		horizontal = true;
    	else
    		horizontal = false;
    	if (horizontal)
    	{
    		x = generator.nextInt(1401 - OBSTACLE_LENGTH * OBSTACLE_THICKNESS);
    		x = (x / 8) * 8;
    		y = generator.nextInt(800);
    		y = (y / 8) * 8;
    	}
    	else
    	{
    		x = generator.nextInt(1400);
    		x = (x / 8) * 8;
    		y = generator.nextInt(801 - OBSTACLE_LENGTH * OBSTACLE_THICKNESS);
    		y = (y / 8) * 8;
    	}

    	obstacle = new ArrayList<Rectangle>();
    	for (int i = 0; i < OBSTACLE_LENGTH; i++)
    	{
    		temp = new Rectangle(x, y, OBSTACLE_THICKNESS, OBSTACLE_THICKNESS, Color.black);
    		obstacle.add(temp);
    		if (horizontal)
    			x += OBSTACLE_THICKNESS;
    		else
    			y += OBSTACLE_THICKNESS;
    	}

    }

	// paints the obstacle on the page
    public void draw(Graphics page)
    {
		for (int i = 0; i < obstacle.size(); i++)
			obstacle.get(i).paint(page);
    }

	// returns true if any two of obstacles intersect each other
	public boolean isIntersect(ArrayList<Obstacle> obs)
	{
		for (int i = 0; i < obs.size(); i++)
		{
			ArrayList<Rectangle> list = obs.get(i).getObstacle();
			for (int k = 0; k < OBSTACLE_LENGTH; k++)
				for (int j = 0; j < OBSTACLE_LENGTH; j++)
					if (list.get(k).contains(obstacle.get(j)))
						return true;
		}
		return false;
	}

	// returns true if obstacle is created on the starting place of the snake
	public boolean isOnSnake (Snake s)
	{
		ArrayList<Rectangle> snake = s.getSnake();
		for (int i = 0; i < snake.size(); i++)
			for (int j = 0; j < obstacle.size(); j++)
				if (snake.get(i).contains(obstacle.get(j)))
					return true;

		// make sure there is no obstacle in the movement area of snake for 1.5 seconds
		Rectangle front = new Rectangle(snake.get(0).getX(), snake.get(0).getY(), snake.get(0).getWidth(), snake.get(0).getHeight(), snake.get(0).getColor());
		for (int i = 0; i < 30; i++)
		{
			front.setLocation(new Point(front.getX() + front.getWidth(), front.getY()));
			for (int j = 0; j < obstacle.size(); j++)
				if (obstacle.get(j).contains(front))
					return true;
		}
		return false;
	}

	// returns the arraylist data of the obstacle
	public ArrayList getObstacle()
	{
		return obstacle;
	}


}