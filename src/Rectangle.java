import java.awt.*;

public class Rectangle
{
	private int x, y; // locations
    private int height, width;
    private Color color;

    public Rectangle(int x, int y, int width, int height, Color color)
    {
    	this.width = width;
    	this.height = height;
    	this.x = x;
    	this.y = y;
    	this.color = color;
    }

    public void paint(Graphics page)
    {
    	page.setColor(color);
    	page.fillRect(x, y, width, height);
    }

    public boolean contains (Rectangle r)
    {
    	if (x <= r.getX() && r.getX() + r.getWidth() <= x + width && y <= r.getY() && r.getY() + r.getHeight() <= y + height)
    		return true;
    	return false;
    }

	/*
    public boolean equals (Rectangle r)
    {
    	if (x == r.getX() && r.getWidth() == width && y == r.getY() && r.getHeight() == height)
    		return true;
    	return false;
    }*/

    public int getX()
    {
    	return x;
    }

        public int getY()
    {
    	return y;
    }

    public Point getLocation()
   	{
    	return new Point(x, y);
    }

        public int getWidth()
    {
    	return width;
    }

        public int getHeight()
    {
    	return height;
    }

    public Color getColor()
    {
    	return color;
    }

    public void setColor (Color c)
    {
    	color = c;
    }

    public void setX (int x)
    {
    	this.x = x;
    }

    public void setY (int y)
    {
    	this.y = y;
    }

    public void setLocation (Point p)
    {
    	x = p.x;
    	y = p.y;
    }

    public void setWidth (int width)
    {
    	this.width = width;
    }

    public void setHeight (int height)
    {
    	this.height = height;
    }
}