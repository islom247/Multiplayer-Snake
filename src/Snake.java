import java.util.ArrayList;
import java.awt.*;

public class Snake
{
	private String name;
	private Color color;
	private ArrayList<Rectangle> snake;
	private final int RECT_WIDTH = 8, RECT_HEIGHT = 8;
	private final int startingLength = 20;
	private boolean up, down, right, left;
	private final int MOVEMENT_LENGTH = 8;
	private Rectangle lastPixel;
	private int cuttingPoint;
	private boolean draw;


	public Snake (String playerName, Color c)
	{
		name = playerName;
		color = c;
		right = true; left = false; up = false; down = false; draw = false;
		cuttingPoint = 0;
		lastPixel = new Rectangle(-40, -40, RECT_WIDTH, RECT_HEIGHT, color);
		snake = new ArrayList<Rectangle>();
		Rectangle rect;

		// the head of the snake
		rect = new Rectangle(200, 400, RECT_WIDTH, RECT_HEIGHT, Color.darkGray);
		snake.add(rect);

		// the Arraylist contains the rectangles from head to tail of the snake
		for (int i = 1; i < 20; i++)
		{
			rect = new Rectangle (200 - i * RECT_WIDTH, 400, RECT_WIDTH, RECT_HEIGHT, color);
			snake.add(rect);
		}
	}

	public void draw(Graphics page)
	{
		page.setColor(color);
		for (int i = 0; i < snake.size(); i++)
			snake.get(i).paint(page);
	}

	// when game is multiplayer, to start oen of the snakes in the right half of the page
	public void reverse()
	{
		for (int i = 0; i < snake.size(); i++)
			snake.get(i).setX(snake.get(i).getX() + 920);
	}

	// returns true if snake hits an obstacle
	public boolean isCollide (Obstacle obs)
	{
		ArrayList<Rectangle> list = obs.getObstacle();
		for (int i = 0; i < list.size(); i++)
			if (snake.get(0).contains(list.get(i)))
				return true;
		return false;
	}

	// returns true if snake bites itself
	public boolean isBiteItself()
	{
		for (int i = 1; i < snake.size(); i++)
			if (snake.get(0).contains(snake.get(i)))
			{
				setCuttingPoint(i);
				return true;
			}
		return false;
	}

	// returns true if this snake bites another snake
	public boolean isBite (Snake s)
	{
		for (int i = 1; i < s.getSnake().size(); i++)
			if (snake.get(0).contains(s.getSnake().get(i)))
			{
				s.setCuttingPoint(i);
				return true;
			}
		if (snake.get(0).contains(s.getSnake().get(0)))
		{
			draw = true;
			s.setDraw(true);
		}
		return false;
	}

	// if snake is bitten, the tail of this snake will be cutten with this method
	public void cut()
	{
		for (int i = cuttingPoint; snake.size() > cuttingPoint; )
			snake.remove(i);
	}

	// returns true if snake eats food
	public boolean isEat(Food food)
	{
		return snake.get(0).contains(food.getFood());
	}

	// if snake eats its own colored food, it will be longer. if snake eats other colored food, returns -1
	// -1 will be used to make slow the other snake
	public int eat(Food food)
	{
		if (color == food.getColor())
			snake.add(new Rectangle(-20, -20, RECT_WIDTH, RECT_HEIGHT, color));
		else
			return -1;
		return 1;
	}

	// moves one pixel back the snake, this will be used when snake hits an obstacle
	public void moveOneBack()
	{
		for (int i = 0; i < snake.size() - 1; i++)
			snake.get(i).setLocation(snake.get(i + 1).getLocation());

		snake.get(snake.size() - 1).setLocation(lastPixel.getLocation());
	}

	// snake moves according to this method
	public void move()
	{
		lastPixel.setLocation(snake.get(snake.size() - 1).getLocation());
		for (int i = snake.size() - 1; i > 0; i--)
			snake.get(i).setLocation(snake.get(i - 1).getLocation());

		if (right)
		{
			int newX = snake.get(0).getX() + MOVEMENT_LENGTH;
			if (newX >= 1400) // snake exceeds the screen
				snake.get(0).setX( newX - 1400 );
			else
				snake.get(0).setX( newX );
		}

		if (left)
		{
				int newX = snake.get(0).getX() - MOVEMENT_LENGTH;
				if (newX < 0) // snake exceeds the screen
					snake.get(0).setX( newX + 1400 );
				else
					snake.get(0).setX( newX );
		}

		if (up)
		{
			int newY = snake.get(0).getY() - MOVEMENT_LENGTH;
			if (newY < 0) // snake exceeds the screen
				snake.get(0).setY( newY + 800 );
			else
				snake.get(0).setY( newY );
		}

		if (down)
		{
			int newY = snake.get(0).getY() + MOVEMENT_LENGTH;
			if (newY >= 800) // snake exceeds the screen
				snake.get(0).setY( newY - 800 );
			else
				snake.get(0).setY( newY );
		}
	}

	// turns the snake up
	public void turnUp()
	{
		if (!down)
		{
			String way = movingWay();
			setAllFalse();
			up = true;
			move();
			if (isBiteItself())
			{
				up = false;
				lastCase(way);
			}
			moveOneBack();
		}
	}

	// turns the snake down
	public void turnDown()
	{
		if (!up)
		{
			String way = movingWay();
			setAllFalse();
			down = true;
			move();
			if (isBiteItself())
			{
				down = false;
				lastCase(way);
			}
			moveOneBack();
		}
	}

	// turns the snake right
	public void turnRight()
	{
		if (!left)
		{
			String way = movingWay();
			setAllFalse();
			right = true;
			move();
			if (isBiteItself())
			{
				right = false;
				lastCase(way);
			}
			moveOneBack();
		}
	}

	// turns the snake left
	public void turnLeft()
	{
		if (!right)
		{
			String way = movingWay();
			setAllFalse();
			left = true;
			move();
			if (isBiteItself())
			{
				left = false;
				lastCase(way);
			}
			moveOneBack();
		}
	}

	// returns the current moving direction of the snake as a String
	public String movingWay()
	{
		if (right)
			return "right";
		else if (left)
			return "left";
		else if (up)
			return "up";
		else if (down)
			return "down";
		else
			return "";
	}

	// sets the direction booleans according to last situation, this will be used to get rid of 50 ms bug
	public void lastCase(String str)
	{
		String lastWay = str;
		if (lastWay.equals("right"))
			right = true;
		if (lastWay.equals("left"))
			left = true;
		if (lastWay.equals("up"))
			up = true;
		if (lastWay.equals("down"))
			down = true;
	}

	// returns the arraylist data of the snake
	public ArrayList<Rectangle> getSnake()
	{
		return snake;
	}

	// sets all direction booleans false
	public void setAllFalse()
	{
		right = false;
		left = false;
		up = false;
		down = false;
	}

	// returns color of the snake
	public Color getColor()
	{
		return color;
	}

	// sets the point of the snake which it is bitten
	public void setCuttingPoint(int place)
	{
		cuttingPoint = place;
	}

	// draw represents result of the game, it is true when snakes hits head to head
	public boolean getDraw()
	{
		return draw;
	}

	public void setDraw(boolean b)
	{
		draw = b;
	}

	// returns the name
	public String getName()
	{
		return name;
	}

	// sets the name
	public void setName(String str)
	{
		name = str;
	}

}
