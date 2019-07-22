import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InfoMulti extends JPanel
{
	MultiPlayer mp;
	JLabel timeLeft, player1, player2;
	int timeLimit;
	String minute, second;
	JButton backToMenu, pause;
	MainMenu main = null;
	JPanel panel;

	// creates labels to show the information of the game
    public InfoMulti(MultiPlayer multi)
    {
    	mp = multi;
    	setPreferredSize(new Dimension(400, 785));
		setBackground(Color.cyan);
		setLayout(new GridLayout(4,1));

		timeLeft = new JLabel();
		player1 = new JLabel();
		player2 = new JLabel();

		minute = ""; second = "";

		backToMenu = new JButton("Back");
		pause = new JButton("Pause");
		backToMenu.addActionListener(new ButtonsListener());
		pause.addActionListener(new ButtonsListener());

		panel = new JPanel();
    	panel.setLayout(new GridLayout(1, 2));
    	panel.add(backToMenu);
    	panel.add(pause);
    }

	// draws the components in the panel and updates them
    public void paintComponent(Graphics page)
    {
    	super.paintComponent(page);

    	timeLimit = mp.getTimeLimit();
    	minute = "" + timeLimit / 60;
    	if (timeLimit % 60 < 10)
    		second = "0" + timeLimit % 60;
    	else
    		second = "" + timeLimit % 60;

    	timeLeft.setText(minute + " : " + second);
    	timeLeft.setFont(new Font("", Font.PLAIN, 50));
    	timeLeft.setLocation(150, 70);
    	add(timeLeft);

    	player1.setText(mp.getSnake1().getName());
    	player1.setFont(new Font("", Font.BOLD, 50));
    	player1.setLocation(200 - mp.getSnake1().getName().length() / 2 * 25, 200);
    	add(player1);

    	page.setFont(new Font("", Font.PLAIN, 40));
    	page.drawString("vs", 190, 361);

    	player2.setText(mp.getSnake2().getName());
    	player2.setFont(new Font("", Font.BOLD, 50));
    	player2.setLocation(200 - mp.getSnake2().getName().length() / 2 * 25, 300);
    	add(player2);

    	add(panel);
    }

	// make connection between main menu and this class
    public void setMenu (MainMenu main)
    {
    	this.main = main;
    }

	// listens the buttons and do corresponding assignments
	private class ButtonsListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			if (event.getActionCommand().equals("Back"))
			{
				main.setMulti(false);
				Runner.runAgain();
			}
			else
			{
				if (mp.getPause())
				{
					mp.setPause(false);
					pause.setText("Pause");
				}
				else
				{
					mp.setPause(true);
					pause.setText("Play");
				}
			}
		}
	}


}