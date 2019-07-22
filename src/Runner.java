import java.awt.*;
import javax.swing.*;

public class Runner {

	static MainMenu menu;
	static JFrame frame;
	static SinglePlayer sp;
	static MultiPlayer mp;
	static Snake snake1;
	static Snake snake2;
	static InfoSingle infoS;
	static InfoMulti infoM;
	static OptionsPanel opt;
	static HowToPlay howTo;

	// main method of the whole game
	// first puts the main menu to the frame and run the game
    public static void main(String[] args)
    {

    	frame = new JFrame("Snake");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menu = new MainMenu();

		frame.getContentPane().add(menu);

    	frame.pack();
    	frame.setVisible(true);
    	frame.setResizable(false);

    }

	// when the displayed panel will change, this method is used to change the panel inside the frame
    public static void runAgain()
    {

    	snake2 = new Snake("Can", Color.blue);
    	snake2.reverse();

		if (menu.getSingle())
		{
			snake1 = new Snake("", Color.red);
			sp = new SinglePlayer(snake1);
			infoS = new InfoSingle(sp);
			sp.setInfo(infoS);
			infoS.setMenu(menu);
			JPanel main = new JPanel();
			main.add(sp);
			main.add(infoS);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(main);
		}
		else if (menu.getOptions())
		{
			opt = new OptionsPanel();
			opt.setMain(menu);

			JPanel main = new JPanel();
			main.add(opt);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(main);
		}
		else if (menu.getMulti())
		{
			snake1 = new Snake(opt.getPlayer1(), Color.red);
			snake2 = new Snake(opt.getPlayer2(), Color.blue);
    		snake2.reverse();
    		mp = new MultiPlayer(snake1, snake2);
    		mp.setTimeLimit(opt.getTimeLimit() * 60);
    		mp.setObstacleNumber(opt.getObstacleNumber());
    		mp.setBackground(opt.getbackground());
    		infoM = new InfoMulti(mp);
    		mp.setInfo(infoM);
    		infoM.setMenu(menu);
    		mp.start();

			JPanel main = new JPanel();
			main.add(mp);
			main.add(infoM);
    		frame.getContentPane().removeAll();
			frame.getContentPane().add(main);
		}
		else if (menu.getHowToPlay())
		{
			howTo = new HowToPlay();
			howTo.setMain(menu);

			JPanel main = new JPanel();
			main.add(howTo);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(main);
		}
		else
		{
			frame.getContentPane().removeAll();
			frame.getContentPane().add(menu);
		}


    	frame.pack();
    	frame.setVisible(true);
    	frame.setResizable(false);
    }




}