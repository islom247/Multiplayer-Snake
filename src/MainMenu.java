import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MainMenu extends JPanel
{
    ImageIcon gameName;
    JButton play, multiPlay, howToPlay;
    SinglePlayer sp;
    MultiPlayer mp;
    boolean single, multi, options, howTo;

	// constructor creates buttons to present user different choices
    public MainMenu()
    {
    	gameName = new ImageIcon("gameName.png");
    	setPreferredSize(new Dimension(800, 800));
    	setBackground (Color.white);

    	// Single Player button
    	play = new JButton ("Single Play");
    	play.setPreferredSize(new Dimension(300, 100));
    	play.setBackground(Color.gray);
    	play.addActionListener(new SingleListener());
    	add(play);

    	// Multi Player button
    	multiPlay = new JButton("Multi Player");
    	multiPlay.setPreferredSize(new Dimension(300, 100));
    	multiPlay.setBackground(Color.pink);
    	multiPlay.addActionListener(new SingleListener());
    	add(multiPlay);

    	// How to play button
    	howToPlay = new JButton("How To Play");
    	howToPlay.setPreferredSize(new Dimension(300, 100));
    	howToPlay.setBackground(Color.lightGray);
    	howToPlay.addActionListener(new SingleListener());
    	add(howToPlay);

    	single = false;
    	multi = false;
    	options = false;
    	howTo = false;

    }

	// draws the name of the game and buttons onto the main panel
    public void paintComponent(Graphics page)
    {
    	super.paintComponent(page);

    	page.drawImage(gameName.getImage(), 50, 0, 800, 300, null);
    	page.drawImage((new ImageIcon("left_snake.png")).getImage(), 0, 670, 130, 130, null);
    	page.drawImage((new ImageIcon("right_snake.png")).getImage(), 670, 670, 150, 150, null);
   		play.setLocation(250, 350);
		multiPlay.setLocation(250, 500);
		howToPlay.setLocation(250, 650);
    }

    public boolean getSingle()
    {
    	return single;
    }

    public void setSingle(boolean b)
    {
    	single = b;
    }

    public boolean getMulti()
    {
    	return multi;
    }

    public void setMulti(boolean b)
    {
    	multi = b;
    }

    public boolean getOptions()
    {
    	return options;
    }

    public void setOptions(boolean b)
    {
    	options = b;
    }

    public boolean getHowToPlay()
    {
    	return howTo;
    }

    public void setHowToPlay(boolean b)
    {
    	howTo = b;
    }

    public MultiPlayer getMultiPlayer()
    {
    	return mp;
    }

	// listens buttons and according to choice of the user, set booleans of the other classes and run the frame again
	private class SingleListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			if (event.getActionCommand().equals("Single Play"))
			{
				single = true;
				Runner.runAgain();
			}
			else if (event.getActionCommand().equals("Multi Player"))
			{
				options = true;
				Runner.runAgain();
			}
			else if (event.getActionCommand().equals("How To Play"))
			{
				howTo = true;
				Runner.runAgain();
			}


		}
	}


}