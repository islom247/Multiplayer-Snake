import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.lang.*;
import java.util.*;
import java.net.URL;
public class HowToPlay extends JPanel
{
	JLabel label1, label2, label3, label4, label5, label6, label7, label8, label9,label11,label12;
	JLabel label13, label14, label15, label16, label17, label18, label19, label20, label21, label22, label23, label24, label25;
	JLabel ylabel, zlabel, tlabel, GIFlabel;
	JButton back;
	ImageIcon arrows,wasd, gameS ;
	MainMenu main;

	// creates the variables and add to the main panel
    public HowToPlay() {

		arrows = new ImageIcon("Arrows.jpg");
		wasd = new ImageIcon("wasd.jpg");
		gameS = new ImageIcon(getClass().getResource("GameS.gif"));

		setPreferredSize(new Dimension(800, 800));
    	setBackground (Color.white);
    	label1= new JLabel("	Let's learn how to play!");
    	label1.setFont(new Font("Verdana",1,40));
    	label2= new JLabel("                                                                                                        MULTIPLAYER SCENARIO ");
     	label3= new JLabel("Multiplayer choice can be selected from options in main menu. Also from there, players name, map, duration time and number of ");
    	zlabel= new JLabel("obstacles can be adjusted respect to player decision.Aim of this scenario is to compete with your opposite, but how? Winning condition");
    	tlabel= new JLabel("of the game is that when a snake becomes smaller than a specified length before the time is up, the other player will win the game.");
    	label4= new JLabel("If none of the snakes becomes smaller than this specified length, time limitation will determine the winner. When time is up, the bigger");
    	ylabel =new JLabel("snake will win the game.");

		label6=new JLabel("	For Player1 (RED)");
		label7=new JLabel("'W' key for up movement");
		label8=new JLabel("'A' key for left movement");
		label9=new JLabel("'S' key for down movement");
		label11=new JLabel("'D' key for rigth movement");

		label12=new JLabel("	For Player2 (BLUE)");
		label13=new JLabel("Up arrow key for up movement");
		label14=new JLabel("Left arrow key for left movement");
		label15=new JLabel("Down arrow key for down movement");
		label16=new JLabel("Right arrow key for right movement");

		label17=new JLabel("                                                                                                        SINGLE-PLAYER SCENARIO ");
		label18=new JLabel("In this mode, One type of food will gain the player a few points and other one will gain many points.Game will end when snake hits itself or");
		label19=new JLabel("an obstacle.There will be no time duration since the goal of the single player mode is playing as much as longer as collecting points ");
		label20=new JLabel("as many as possible.");
		label21=new JLabel("Single Player Controllers");
		label22=new JLabel("Up arrow key for up movement");
		label23=new JLabel("Left arrow key for left movement");
    	label24=new JLabel("Down arrow key for down movement");
    	label25=new JLabel("Right arrow key for right movement");

    	back = new JButton ("Back");
    	back.setPreferredSize(new Dimension(100, 100));
    	back.setBackground(Color.yellow);
    	back.addActionListener(new BackListener());

    	add(label1);
    	add(label2);
    	add(label3);
    	add(label4);

    	add(label6);
    	add(label7);
    	add(label8);
    	add(label9);
    	add(ylabel);
    	add(zlabel);
    	add(tlabel);
    	add(label11);
    	add(label12);
    	add(label13);
    	add(label14);
    	add(label15);
    	add(label16);
		add(label17);
		add(label18);
		add(label19);
		add(label20);
		add(label21);
		add(label22);
		add(label23);
		add(label24);
		add(label25);

    	add(back);
    }

	@Override
	// sets the locations of the labels and add images and gif to the page
    protected void paintComponent(Graphics page)
    {
    	super.paintComponent(page);

		label2.setForeground(Color.red);
		label17.setForeground(Color.red);

		label6.setForeground(Color.red);
		label12.setForeground(Color.blue);
		label21.setForeground(Color.orange);
		label2.setLocation(20, 60);
		label3.setLocation(20, 80);
		zlabel.setLocation(20, 100);
		tlabel.setLocation(20,120);
		label4.setLocation(20, 140);
		ylabel.setLocation(20,160);
		label6.setLocation(40, 220);
		label7.setLocation(60, 240);
		label8.setLocation(60, 260);
		label9.setLocation(60, 280);
		label11.setLocation(60, 300);
		label12.setLocation(40, 320);
		label13.setLocation(60, 340);
		label14.setLocation(60, 360);
		label15.setLocation(60, 380);
		label16.setLocation(60, 400);
		label17.setLocation(20,440);
		label18.setLocation(20,460);
		label19.setLocation(20,480);
		label20.setLocation(20,500);
		label21.setLocation(40, 520);
		label22.setLocation(60, 540);
		label23.setLocation(60, 560);
		label24.setLocation(60, 580);
		label25.setLocation(60, 600);

		page.drawImage(arrows.getImage(), 330, 325, 200, 100, null);
		page.drawImage(wasd.getImage(), 330, 210, 200, 100, null);
		page.drawImage(gameS.getImage(), 330, 525, 200, 100, null);
		repaint();
		back.setLocation(500, 700);
    }

    public void setMain(MainMenu m)
    {
    	main = m;
    }

    private class BackListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			main.setHowToPlay(false);
			Runner.runAgain();
		}
	}


}