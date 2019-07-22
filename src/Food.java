import java.util.*;
import java.awt.*;

public class Food
{
	final int FOOD_WIDTH = 8, FOOD_HEIGHT = 8;
	int foodX, foodY;
	Color color;
	Rectangle food;
	int type; // 0 represents normal food, 1 represents special food that gives extra point
	Random generator;

	// constructor sets the color and coordinates of the food
    public Food(int x, int y, Color c, int type)
    {
    	foodX = x;
    	foodY = y;
    	color = c;
    	this.type = type;
    	food = new Rectangle(x, y, FOOD_WIDTH, FOOD_HEIGHT, color);
    	generator = new Random();
    }

	// create a new food at random location
    public Food createNewFood(Color c, int type)
    {
    	int x = generator.nextInt(1400);
    	x = (x / 8) * 8;
    	int y = generator.nextInt(800);
    	y = (y / 8) * 8;
		Food f = new Food(x, y, c, type);
    	return f;
    }

	// returns true if the food's coordinates overlaps with snake
	// prevents creating new food on the snakes locations
    public boolean isOnSnake(Snake s)
    {
    	ArrayList<Rectangle> ekans = s.getSnake();
    	for (int i = 0; i < ekans.size(); i++)
    		if (ekans.get(i).contains(food))
				return true;
    	return false;
    }

	// returns true if the new created food is on obstacles
    public boolean isOnObstacle(ArrayList<Obstacle> obs)
    {
    	for (int j = 0; j < obs.size(); j++)
    	{
    		ArrayList<Rectangle> blocks = obs.get(j).getObstacle();
	    	for (int i = 0; i < blocks.size(); i++)
	    		if (blocks.get(i).contains(food))
	    			return true;
    	}
    	return false;
    }

	// paints the food in the map
    public void draw (Graphics page)
    {
    	page.setColor(color);
    	food.paint(page);
    }


    public Rectangle getFood()
    {
    	return food;
    }


    public Color getColor()
    {
    	return color;
    }


	public int getX()
	{
		return foodX;
	}


	public int getY()
	{
		return foodY;
	}

}