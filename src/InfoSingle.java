import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InfoSingle extends JPanel
{
	SinglePlayer sp;
	JLabel label1, label2, label3;
	JButton backToMenu, pause;
	MainMenu main = null;
	JPanel panel;

	// creates the labels to show the necessary informations
    public InfoSingle(SinglePlayer single)
   	{
   		sp = single;
   		setPreferredSize(new Dimension(400, 785));
		setBackground(Color.pink);
		setLayout(new GridLayout(4,1));

		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();

		backToMenu = new JButton("Back");
		pause = new JButton("Pause");
		backToMenu.addActionListener(new ButtonListener());
		pause.addActionListener(new ButtonListener());

		panel = new JPanel();
    	panel.setLayout(new GridLayout(1, 2));
    	panel.add(backToMenu);
    	panel.add(pause);
    }

	// draws the components in the panel and updates them
    public void paintComponent(Graphics page)
    {
    	super.paintComponent(page);


    	label1.setText("Score: " + sp.getScore());
    	label1.setFont(new Font("", Font.PLAIN, 50));
    	add(label1);


    	label2.setText("Eat " + (5 - sp.getSpecialTime()) + " foods for Bonus");
    	label2.setFont(new Font("", Font.PLAIN, 40));
    	add(label2);


    	label3.setText("Time Left for Bonus Food: " + sp.getTimeLeftForSpecial());
    	label3.setFont(new Font("", Font.PLAIN, 25));
    	add(label3);

		add(panel);
    }

	// make connection between main menu and this class
    public void setMenu (MainMenu main)
    {
    	this.main = main;
    }

	// listens the buttons to do corresponding assignments
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			if (event.getActionCommand().equals("Back"))
			{
				main.setSingle(false);
				Runner.runAgain();
			}
			else
			{
				if (sp.getPause())
				{
					sp.setPause(false);
					pause.setText("Pause");
				}
				else
				{
					sp.setPause(true);
					pause.setText("Play");
				}
			}

		}
	}
}