package weapon;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import map.Map;
import panel.Panel;
import team.Team;
import weapon.Weapon.TimesUp;
import worm.Worm;
/**
 * Weapon which is shot max 3 times in a row, left or right
 */
public class Gun extends Weapon{
	
	/**Reference to the main gamepanel*/
	private Panel gamepanel;
	/**The original staring point*/
	private Point start;
	/**How many bullets left*/
	private int stren;
	/**left or right shot*/
	private Point direction;
	
	/**
	 * Creates Gun object
	 * @param name name
	 * @param dmg damage
	 * @param expl explosion size
	 * @param range max range(not compulsory)
	 * @param desc description
	 * @param p position
	 * @throws IOException
	 */
	public Gun(String name, int dmg, int expl, int range, String desc, Point p) throws IOException {
		super(name, dmg, expl, range, desc, p, ImageIO.read(new File("gun4.png")));
		start = new Point(p);
	}
	
	/**
	 * Starts the timer and gives the parameters of the shot
	 */
	@Override
	public void shoot(Point dir, int strength, Map m, final Panel gp, Team[] teams) {
		if(strength >2) strength = 2;
		stren = strength;
		if(dir.x<0) direction=new Point(-1,0);
		else direction = new Point(1,0);
		System.out.println(name + " Shot " + strength + " dir: " + direction+ " pos: " + p);		
		gamepanel = gp;
		Timer t;
		ActionListener a = new TimesUp(gp, this, direction, m, teams, strength/10, false, false);
		t = new Timer(50,a);
		t.start();
	}
	
	/**
	 * Sets the map's one block where necessary and decreases HP of affected worm
	 * Starts another shot if any bullets left (stren > 0)
	 */
	@Override
	public void Explode(Point po, Map m, Team[] teams) {
		if(m.get(p.x, p.y)!=0){
			m.set(p.x, p.y, 3);
		}		
		
		Worm w;
		if((w = m.wormHasPos(teams, new Point(po.x,po.y)))!=null) {
			w.dmg(dmg);
			
			for(Team t : teams) t.update();			
		}
		
		try {			
			if(stren>0) {
				Gun g = new Gun("Gun", 7, 1, 80, "Shoots 3 bullets", start);
				g.shoot(direction, --stren, m, gamepanel, teams);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
