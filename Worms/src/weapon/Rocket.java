package weapon;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import javax.imageio.ImageIO;

import map.Map;
import panel.Panel;
import team.Team;
import worm.Worm;

/**
 * Weapon which flies and makes 5x5 explosion.
 */
public class Rocket extends Weapon{
	
	/**
	 * Creates Rocket object
	 * @param name name
	 * @param dmg damage
	 * @param expl explosion size
	 * @param range max range(not compulsory)
	 * @param desc description
	 * @param p position
	 * @throws IOException
	 */
	public Rocket(String name, int dmg, int expl, int range, String desc, Point p) throws IOException {
		super(name, dmg, expl, range, desc, p, ImageIO.read(new File("gun1.png")));
		
		//System.out.println(name + "created");
	}
	
	/**
	 * Starts a timer and gives the parameters for the shot
	 */
	@Override
	public void shoot(Point dir, int strength, Map m, final Panel gp, Team[] teams) {
		System.out.println(name + " Shot " + strength + "dir: " + dir+ "pos: " + p);		
		
		Timer t;
		ActionListener a = new TimesUp(gp, this, dir, m, teams, strength/10, true, true);
		t = new Timer(90,a);
		t.start();
	}
	

	/**
	 * Sets the map's blocks where necessary and decreases HP of affected worms
	 */
	@Override
	public void Explode(Point po, Map m, Team[] teams) {
		for(int i = 0; i< expl; i++) {
			for(int j = 0; j< expl; j++) {
				if(!((i==0 && j==0) || (i==0 && j==expl-1) || (i==expl-1 && j==0) || (i==expl-1 && j==expl-1))) {
					if(m.get(po.x-expl/2+i,po.y-expl/2+j) != 0) {
						m.set(po.x-expl/2+i,po.y-expl/2+j,3);
					}
					Worm w;
					if((w = m.wormHasPos(teams, new Point(po.x-expl/2+i,po.y-expl/2+j)))!=null) {
						w.dmg(dmg);
						
						for(Team t : teams) t.update();
					}			
				}			
			}		
		}
	}
	
	
}
