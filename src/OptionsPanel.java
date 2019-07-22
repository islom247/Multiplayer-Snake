
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// options panel
public class OptionsPanel extends JPanel {

    //instance variables
    private JLabel options; //options label at the top
    private JLabel player1, player2; // label next to the first player's name textfield
    private JLabel playerInfo; // label next to the second player's name textfield
    private JLabel gameOptions;
    private JLabel maps;
    private JLabel time; // label that is next to the time box
    private JLabel obs; // label that is next to number of obstacles' box
    private JTextField p1name; // field to enter first player's name
    private JTextField p2name; // field to enter second player's name
    private JComboBox<Integer> numOfObs; // combobox to choose number of obstacles
    private JComboBox<Integer> duration; // combobox to choose time (duration)
    private JButton save; //save&play button at the bottom
    private JRadioButton m1, m2, m3, m4, m5; // buttons to choose from maps
    private ImageIcon pic1, pic2, pic3, pic4, pic5;
    private Image p1, p2, p3, p4, p5, current = null;
    private MainMenu mainMenu = null;
    private int obsNum = 0, timeLimit = 1;

    String first = "Ali", second = "Ahmet";

	// creates all components and add to the main panel
    public OptionsPanel() {

        //setting texts and sizes for the labels
        setPreferredSize(new Dimension(800, 800));
        setBackground(Color.pink);
        setLayout(null);

		// Options label
		options = new JLabel("~~OPTIONS~~");
		options.setFont(new Font("", Font.BOLD, 50));
		add(options);
		options.setBounds(250, 0, 350, 100);

		// Player Info Label
		playerInfo = new JLabel("Player Information");
		playerInfo.setFont(new Font("", Font.BOLD, 25));
		add(playerInfo);
		playerInfo.setBounds(0, 100, 230, 100);

		// Players
        player1 = new JLabel("Player 1 Name: ");
        player2 = new JLabel("Player 2 Name: ");

        //initializing textfields for player names and setting their sizes
        p1name  = new JTextField(35);
        p2name  = new JTextField(35);

		// Obstacles and Time
        obs = new JLabel("Number of obstacles (0-20)");
        time = new JLabel("Time Duration(1-10 minutes)");

        //initializng Comboboxes and adding items to them
        numOfObs = new JComboBox<>();
        duration = new JComboBox<>();
        for (int i = 0; i < 21; ++i)
            numOfObs.addItem(i);
        for (int i = 1; i < 11; ++i)
            duration.addItem(i);

		// add the player field
        Playerfield p = new Playerfield();
        p.setBounds(0, 200, 800, 150);
        add(p);

        // game options
		gameOptions = new JLabel("Game Options");
		gameOptions.setFont(new Font("", Font.BOLD, 25));
		add(gameOptions);
		gameOptions.setBounds(400, 100, 200, 100);

		// maps Label
		maps = new JLabel("Map Options");
		maps.setFont(new Font("", Font.BOLD, 25));
		add(maps);
		maps.setBounds(0, 350, 200, 100);


        //initializing radiobuttons
        m1 = new JRadioButton("Map 1");
        m2 = new JRadioButton("Map 2");
        m3 = new JRadioButton("Map 3");
        m4 = new JRadioButton("Map 4");
        m5 = new JRadioButton("Map 5");

		// radio buttons to choose map
        RadioButtonsPanel buttons = new RadioButtonsPanel();
        add(buttons);
        buttons.setBounds(0, 610, 800, 100);

        pic1 = new ImageIcon("1.jpg");
    	pic2 = new ImageIcon("2.jpg");
    	pic3 = new ImageIcon("3.jpg");
    	pic4 = new ImageIcon("4.jpg");
    	pic5 = new ImageIcon("5.jpg");

    	p1 = pic1.getImage();
    	p2 = pic2.getImage();
    	p3 = pic3.getImage();
    	p4 = pic4.getImage();
    	p5 = pic5.getImage();

    	//save button
        save = new JButton("Save & Play");
        add(save);
        save.setBounds(300, 710, 200, 90);

        //adding listener to components
        p1name.addActionListener(new FieldListener());
        p2name.addActionListener(new FieldListener());

        numOfObs.addActionListener(new BoxListener());
        duration.addActionListener(new BoxListener());

        save.addActionListener(new ButtonListener());

        m1.addActionListener(new RadioListener());
        m2.addActionListener(new RadioListener());
        m3.addActionListener(new RadioListener());
        m4.addActionListener(new RadioListener());
        m5.addActionListener(new RadioListener());
    }

	// draws the background images to show the user
    public void paintComponent(Graphics page)
    {
    	super.paintComponent(page);

    	page.drawImage(p1, 0, 450, 160, 160, null);
    	page.drawImage(p2, 160, 450, 160, 160, null);
    	page.drawImage(p3, 320, 450, 160, 160, null);
    	page.drawImage(p4, 480, 450, 160, 160, null);
    	page.drawImage(p5, 640, 450, 160, 160, null);
    }

    public void setMain(MainMenu main)
    {
    	mainMenu = main;
    }

    public String getPlayer1()
    {
    	return first;
    }

    public String getPlayer2()
    {
    	return second;
    }

    public int getObstacleNumber()
    {
    	return obsNum;
    }

    public int getTimeLimit()
    {
    	return timeLimit;
    }

    public Image getbackground()
    {
    	return current;
    }


    // listens the save and play button
    public class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
        	first = p1name.getText();
        	second = p2name.getText();
        	mainMenu.setOptions(false);
			mainMenu.setMulti(true);
			Runner.runAgain();
        }
    }

	// listens the radio buttons to choose the map
    public class RadioListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
			if (e.getActionCommand().equals("Map 1"))
				current = p1;
			if (e.getActionCommand().equals("Map 2"))
				current = p2;
			if (e.getActionCommand().equals("Map 3"))
				current = p3;
			if (e.getActionCommand().equals("Map 4"))
				current = p4;
			if (e.getActionCommand().equals("Map 5"))
				current = p5;
        }
    }

	// listens the JTextFields
    public class FieldListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
        	first = p1name.getText();
        	second = p2name.getText();
        }
    }

	// Listens the comboboxes
    public class BoxListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
			obsNum = Integer.parseInt(numOfObs.getSelectedItem().toString());

			timeLimit = Integer.parseInt(duration.getSelectedItem().toString());

        }
    }

    //new panel for players name field and label to get a better layout
    public class Playerfield extends JPanel
    {
        public Playerfield() {
            setLayout(new GridLayout(2, 4));
            add(player1);
            add(p1name);
            add(obs);
            add(numOfObs);
            add(player2);
            add(p2name);
            add(time);
            add(duration);
        }
    }

    //the same for maps radiobuttons
    public class RadioButtonsPanel extends JPanel
    {
        public RadioButtonsPanel() {
            setLayout(new GridLayout(1, 5));
            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(m1);
            buttonGroup.add(m2);
            buttonGroup.add(m3);
            buttonGroup.add(m4);
            buttonGroup.add(m5);
            add(m1);
            add(m2);
            add(m3);
            add(m4);
            add(m5);
        }
    }

}
