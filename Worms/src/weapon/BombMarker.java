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
 * Weapon which marks the place of Bomb to be dropped.
 */
public class BombMarker extends Weapon{
	
	/**Reference to the main gamepanel*/
	private Panel gamepanel;
	
	/**
	 * Creates BombMarker object
	 * @param name name
	 * @param dmg damage
	 * @param expl explosion size
	 * @param range max range(not compulsory)
	 * @param desc description
	 * @param p position
	 * @throws IOException
	 */
	public BombMarker(String name, int dmg, int expl, int range, String desc, Point p) throws IOException {
		super(name, dmg, expl, range, desc, p, ImageIO.read(new File("gun2.png")));
	}
	
	/**
	 * Starts a timer and gives the parameters for the shot
	 */
	@Override
	public void shoot(Point dir, int strength, Map m, final Panel gp, Team[] teams) {
		System.out.println(name + " Shot " + strength + " dir: " + dir+ " pos: " + p);		
		gamepanel = gp;
		Timer t;
		ActionListener a = new TimesUp(gp, this, dir, m, teams, strength/10, false, true);
		t = new Timer(90,a);
		t.start();
	}
	
	/**
	 * Sets the Position of the Bomb to be dropped, and initiates the drop
	 */
	@Override
	public void Explode(Point po, Map m, Team[] teams) {
		
		try {
			Bomb b = new Bomb("Bomb", 23, 5, 0, "Falls from above", new Point(po.x, 0));
			b.shoot(new Point(0,1), 0 , m, gamepanel, teams);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
