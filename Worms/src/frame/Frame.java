package frame;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import map.Map;
import options.Options;

import panel.Panel;
import team.Team;
import weapon.*;

/**
 * Frame of the main game
 * Stores all the game control GUI and manages the events.
 * Controls the objects in connection with the gameplay
 */
public class Frame extends JFrame {
	
	/**Main panel with the graphics*/
	private Panel gamePanel;
	/**Stores the control GUI*/
	private JPanel ctrlPanel;
	/**Stores the Objects that display some information of the GUI and the game*/
	private JPanel infoPanel;
	/**Gives Information about the selected weapon*/
	private JLabel Name, icon, Dmg, Expl, Comment;	
	/**Control movement*/
	private JButton Left, Right;
	/**Shoots the selected Weapon*/
	private JButton Fire;
	/**Sets the strength of the shot*/
	private JSlider Strength;
	/**Stores all direction selecting radio buttons*/
	private JRadioButton[] rb = new JRadioButton[12];
	/**A group for all the radio buttons*/
	private ButtonGroup bg;
	/**The used weapon can be selected here*/
	private JComboBox WeaponChoose;
	/**Stores the currently selected Weapon*/
	private Weapon W;
	/**The map that is currently played*/
	private Map m;
	/**The game menu's base*/
	private JMenuBar menuBar;
	/**The current loaded game option*/
	private Options opt;
	/**Player's team*/
	private Team t1, t2;
	/**Storage for teams*/
	private Team[] teams;
	/**The index of team in teams who has the current turn*/
	private int turn = 0;
	
	/**
	 * Creates the main frame, sets positions of objects and adds the action listeners
	 * @throws IOException
	 */
	public Frame() throws IOException {
		opt = new Options(3,3,70,Color.RED,Color.CYAN,false,false);
		if(new File(System.getProperty("user.dir")+"/options/current.ser").exists()) {
			opt = opt.readOption(System.getProperty("user.dir")+"/options/current.ser");
		}		
		m = new Map(opt.maptype,opt.caves,opt.symmetric);
		t1 = new Team(opt.color1,opt.teamSize,opt.maxHP);
		t2 = new Team(opt.color2,opt.teamSize,opt.maxHP);
		teams = new Team[] {t1,t2};
		W = new Weapon();
		
		m.Fall(t1, t2);
		
		gamePanel = new Panel(m,t1,t2, W);
		menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu"); 
		menuBar.add(menu);
		JMenuItem newGame = new JMenuItem("New Game");
		JMenuItem opt = new JMenuItem("Options");
		JMenuItem Exit = new JMenuItem("Exit Game");
		menu.add(newGame);
		menu.add(opt);
		menu.add(Exit);
		
		setSize(1285,828);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Worms");
		add(gamePanel);
		
		String[] weapons = {"Rocket", "Bomb", "Gun"};
		
		ctrlPanel = new JPanel();
		Icon r_arrow = new ImageIcon("r_arrow.png");
		Icon l_arrow = new ImageIcon("l_arrow.png");
		Icon fire = new ImageIcon("fire.png");
		
		
		Left = new JButton(l_arrow);
		Left.setBackground(Color.GRAY);
		Right = new JButton(r_arrow);
		Right.setBackground(Color.GRAY);
		Fire = new JButton(fire);
		Fire.setBackground(Color.GRAY);
		Strength = new JSlider();
		Strength.setBackground(Color.DARK_GRAY);
		WeaponChoose = new JComboBox(weapons);
		
		
		add(ctrlPanel, BorderLayout.SOUTH);
		add(menuBar, BorderLayout.NORTH);
		ctrlPanel.add(Left);
		ctrlPanel.add(Fire);
		ctrlPanel.add(Right);
		ctrlPanel.add(Strength);
		ctrlPanel.setBackground(Color.darkGray);
		
		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(6,6));

		bg = new ButtonGroup();
		String Btns = "001100010010100001100001010010001100";
		int j = 0;
		for(int i = 0; i< 36; i++) {			
			if(Btns.charAt(i)=='0') {
				JPanel p = new JPanel();
				p.setBackground(Color.DARK_GRAY);
				radioPanel.add(p);
			}
			else {
				JRadioButton b = new JRadioButton();
				b.setBackground(Color.DARK_GRAY);
				radioPanel.add(b);
				bg.add(b);				
				b.setSelected(true);
				rb[j++] = b;
				
			}			
		}	
		
		ctrlPanel.add(radioPanel);
		ctrlPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("controls.png")))));
		ctrlPanel.add(WeaponChoose);
		
		W=getSelectedWeapon();
		Name = new JLabel(W.name);
		icon = new JLabel(new ImageIcon(W.getSrc()));
		Dmg = new JLabel(Integer.toString(W.dmg));
		Expl = new JLabel(Integer.toString(W.expl));
		Comment = new JLabel(W.desc);
		
		
		infoPanel = new JPanel(new GridLayout(4,2));
		infoPanel.setSize(125, 125);
		infoPanel.add(Name);		
		infoPanel.add(icon);
		infoPanel.add(new JLabel("Damage:"));
		infoPanel.add(Dmg);
		infoPanel.add(new JLabel("Explosion:"));
		infoPanel.add(Expl);
		infoPanel.add(new JLabel("Descripition:"));
		infoPanel.add(Comment);
		
		
		ctrlPanel.add(infoPanel);
		
		newGame.addActionListener(new NewGameOnClick());
		opt.addActionListener(new OptionsOnclick());
		Exit.addActionListener(new ExitOnClick());
		Left.addActionListener(new MoveLeftClick());
		Right.addActionListener(new MoveRightClick());
		Fire.addActionListener(new FireClick());
		WeaponChoose.addActionListener(new SelectedIndexChanged());
		
		
		gamePanel.setCurrent(teams[turn].get(teams[turn].currentTurn()));
	}
	
	
	/**
	 * actionPerformed() is called, when another weapon is selected, the info panel gets refreshed
	 */
	final class SelectedIndexChanged implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			W = getSelectedWeapon();
			Name.setText(W.name);
			icon.setIcon(new ImageIcon(W.getSrc()));
			Dmg.setText(Integer.toString(W.dmg));
			Expl.setText(Integer.toString(W.expl));
			Comment.setText(W.desc);
		}
		
	}
	
	/**
	 * actionPerformed() is called when new game is clicked in the menu.
	 * Generates new teams and new map, with the new settings if any
	 */
	final class NewGameOnClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Left.setEnabled(true);
			Right.setEnabled(true);
			Fire.setEnabled(true);
			turn=0;
			opt=opt.readOption(System.getProperty("user.dir")+"/options/current.ser");
			m.generate(opt.maptype,opt.caves,opt.symmetric);			
			try {
				t1.generate(opt.color1,opt.teamSize,opt.maxHP);
				t2.generate(opt.color2,opt.teamSize,opt.maxHP);
				m.Fall(t1, t2);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			gamePanel.setCurrent(teams[turn].get(teams[turn].currentTurn()));
			gamePanel.repaint();
		}
	}
	
	/**
	 * actionPerformed() is called when Options is clicked in the menu.
	 * Opens the options window, where the game settings can be changed
	 */	
	final class OptionsOnclick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			OptionsDialog oDialog = new OptionsDialog();
			oDialog.show();
		}
	}
	
	/**
	 * actionPerformed() is called when exit clicked in the menu.
	 * It closes the program
	 */
	final class ExitOnClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}		
	}
	
	/**
	 * actionPerformed() is called when left arrow button is clicked.
	 * Moves the current worm to the left if possible
	 */
	final class MoveLeftClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(!W.exists && t1.size()>0 && t2.size()>0) {
				m.getStep(teams[turn].get(teams[turn].currentTurn()), t1, t2, -1);
				gamePanel.repaint();
			}
		}		
	}
	
	/**
	 * actionPerformed() is called when right arrow is clicked
	 * Moves the current worm to the right if possible
	 */
	final class MoveRightClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(!W.exists && t1.size()>0 && t2.size()>0) {
				m.getStep(teams[turn].get(teams[turn].currentTurn()), t1, t2, 1);
				gamePanel.repaint();
			}
		}		
	}
	
	
	/**
	 * actionPerformed() is called when Fire button is clicked.
	 * starts the shooting method for the selected weapon
	 */
	final class FireClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			if(!W.exists && t1.size()>0 && t2.size()>0) {
				
				System.out.println(teams[turn].get(teams[turn].currentTurn()).getPos());
				
				W = getSelectedWeapon();
				
				W.exists=true;
				W.shoot(getDir(), Strength.getValue(), m, gamePanel, teams);
				turn++;
				turn%=2;
				teams[turn].nextTurn();
				gamePanel.setCurrent(teams[turn].get(teams[turn].currentTurn()));
			
			}			
		}		
	}
	
	
	/**
	 * By the selected radio button it gives the direction of the shot
	 * @return direction (x,y)
	 */
	public Point getDir() {
		int dir = 0;
		for (int i =0 ; i< 12; i++) {
			if(rb[i].isSelected()) {
				dir = i;
				break;
			}
		}
		Point p = null;
		switch(dir) {
		case 0: p = new Point(-1,-2); break;
		case 1: p = new Point(1,-2); break;
		case 2: p = new Point(-1,-1); break;
		case 3: p = new Point(1,-1); break;
		case 4: p = new Point(-2,-1); break;
		case 5: p = new Point(2,-1); break;
		case 6: p = new Point(-2,0); break;
		case 7: p = new Point(2,0); break;
		case 8: p = new Point(-1,1); break;
		case 9: p = new Point(1,1); break;
		case 10: p = new Point(-1,2); break;
		case 11: p = new Point(1,2); break;
		}
		return p;		
	}
	
	/**
	 * It defines which weapon is selected in the Combo Box.
	 * @return The selected weapon object
	 */
	public Weapon getSelectedWeapon() {
		try {
			switch(WeaponChoose.getSelectedIndex()) {
				case 0: return new Rocket("Rocket",25,5,10,"5x5 explosion", new Point(teams[turn].get(teams[turn].currentTurn()).getPos()));
				case 1: return new BombMarker("BombMarker",0,0,10,"Sets position of bomb", new Point(teams[turn].get(teams[turn].currentTurn()).getPos()));
				case 2: return new Gun("Gun",7,1,80,"3x1x1 explosion flies horizontally", new Point(teams[turn].get(teams[turn].currentTurn()).getPos()));
				default: return null;
			}
		}
		catch(IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
