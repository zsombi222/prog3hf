package panel;
import javax.imageio.ImageIO;
import map.Map;
import team.Team;
import weapon.Weapon;
import worm.Worm;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * This panel draws the main game, with the blocks, 
 * players, current player, hp-s and weapons.
 */
public class Panel extends JPanel{
	
	/** number of block types */
	private int blocknum = 4; 
	/** stores all the block images */
	private BufferedImage[] img_s = new BufferedImage[blocknum];
	/** block size in pixels*/
	private int block = 16;
	/** current character arrow image*/
	private BufferedImage current;
	/** current character*/
	private Worm curr;
	/** map to draw*/
	private Map M;
	/** the tow teams on the map*/
	private Team t1, t2;
	/** the weapon used, if null, nothing to draw*/
	private Weapon W;
	
	/**
	 * Reads the necessary block sources, and constructs the object
	 * @param m  reference to map
	 * @param T1  reference to team 1
	 * @param T2  reference to team 2
	 * @param Weap  reference to used weapon
	 * @throws IOException
	 */
	public Panel(Map m, Team T1, Team T2, Weapon Weap) throws IOException {
		M=m;
		t1=T1;
		t2=T2;
		W=Weap;
		
		setSize(80*block,40*block);
		for (int i = 0; i< blocknum; i++) {
			img_s[i] = ImageIO.read(new File(i+".jpg"));
		}
		current = ImageIO.read(new File("curr.png"));
	}
	
	/**
	 * Paints all the components of the game
	 * @param g  Graphics
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0; i< 80; i++) {
			for(int j = 0; j< 40; j++) {
				g.drawImage(img_s[M.get(i, j)], i*block, j*block, this);
			}
		}
		Team[] ts = {t1,t2};
		for(Team t : ts) {
			for (int i = 0; i < t.size(); i++) {
				Worm w = t.get(i);
				g.drawImage(w.getSrc(), w.getPos().x*block, w.getPos().y*block, this);
				g.setColor(w.getColor());
				g.drawString(Integer.toString(w.getHP()),w.getPos().x*block, (w.getPos().y)*block-2);
				if(w.equals(curr)) {
					g.drawImage(current, curr.getPos().x*block, curr.getPos().y*block-30, this);
				}
			}
		}

		if(W.getSrc() != null) {
			g.drawImage(W.getSrc(), W.getPos().x*block, W.getPos().y*block, this);
		}
			
	}
	
	/**
	 * Sets the current weapon used and the map,
	 * it is used when new objects are created
	 * and the reference needs to be changed
	 * @param w  new Weapon
	 * @param m  new Map
	 */
	public void paintShoot(Weapon w, Map m) {
		W=w;
		M=m;
		repaint();
	}
	
	
	/**
	 * Sets the current character
	 * @param c  the current Worm
	 */
	public void setCurrent(Worm c) {
		curr = c;
	}
}
