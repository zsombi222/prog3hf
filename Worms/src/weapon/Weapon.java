package weapon;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import map.Map;
import panel.Panel;
import team.Team;

/**
 * Weapon parent class, needs to be extended to use it properly.
 */
public class Weapon {
	/**Name of the weapon*/
	public String name;
	/**Damage*/
	public int dmg;
	/**Explosion size expl x expl*/
	public int expl;
	/**max range*/
	public int range;
	/**Description*/
	public String desc;
	/**Position*/
	protected Point p = null;
	/**Image*/
	private BufferedImage src = null;
	/**true if not exploded yet but is already shot*/
	public boolean exists = false;
	
	/**
	 * Constructor without parameters
	 */
	public Weapon() {
	}
	
	/**
	 * Constructor to be called as a super method from children.
	 * @param Name Name
	 * @param Dmg Damage
	 * @param Expl Explosion
	 * @param Range Range
	 * @param Desc Description
	 * @param P Position
	 * @param Src Source Image
	 */
	public Weapon(String Name, int Dmg, int Expl, int Range, String Desc, Point P, BufferedImage Src) {
		name = Name;
		dmg = Dmg;
		expl = Expl;
		range = Range;
		desc = Desc;
		p = P;
		src=Src;
	}
	
	/**
	 * Starts a shot, to be overridden
	 * @param dir Direction
	 * @param strength Strength
	 * @param m Map
	 * @param gp gamepanel
	 * @param teams teams
	 */
	public void shoot(Point dir, int strength, Map m, Panel gp, Team[] teams) {}
	
	/**
	 * Getting the image of the weapon
	 * @return The Image
	 */
	public BufferedImage getSrc(){
		return src;
	}

	/**
	 * Get Position
	 * @return Current position
	 */
	public Point getPos() {
		return p;
	}
	
	/**
	 * Set position
	 * @param P Point
	 */
	public void setPos(Point P) {
		p = new Point(P);
	}
	
	/**
	 * Explosion, to be overridden
	 * @param P Point
	 * @param m Map
	 * @param teams Teams
	 */
	public void Explode(Point P, Map m, Team[] teams) {}
	
	/**
	 * Hitting map block, or worm
	 * @param p Point
	 * @param m Map
	 * @param teams Teams
	 * @return true if block is hit
	 */
	public boolean isHit(Point p, Map m, Team[] teams) {
		if (m.wormHasPos(teams, p) == null && (m.get(p.x,p.y)==0 || m.get(p.x,p.y)==3)) {			
			return false;
		}
		return true;
	}
	
	/**
	 * Timer ticking, for animation of shot 
	 */
	final class TimesUp implements ActionListener{

		/**game panel*/
		private Panel gp;
		/**weapon in use*/
		private Weapon r;
		/**direction*/
		private Point dir;
		/**original direction*/
		private Point dirprev;
		/**Map*/
		private Map m;
		/**Teams*/
		private Team[] teams;
		/**Strength of shot*/
		private int stren;
		/**If the first movement is one block up*/
		private boolean first;
		/**If the weapon is able to fall down*/
		private boolean fallable;
		
		/**
		 * Constructor
		 * @param P Panel
		 * @param R Weapon
		 * @param Dir Direction
		 * @param M Map
		 * @param Teams teams
		 * @param strength Strength
		 * @param frst First
		 * @param fall Fallable
		 */
		public TimesUp(Panel P, Weapon R, Point Dir, Map M, Team[] Teams, int strength, boolean frst, boolean fall) {
			first = frst;
			gp=P;
			r=R;
			dir = new Point(Dir);
			dirprev = new Point(dir);
			m = M;
			teams = Teams;
			stren = strength;
			fallable = fall;
		}
		
		
		/**
		 * Called when a tick occurs.
		 * Defines the position of the weapon while moving
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(first) {
				r.getPos().y--;
				first=false;
			}
			else if (dir.x>0) {
				dir.x--;
				r.getPos().x++;
			}
			else if (dir.x<0) {
				dir.x++;
				r.getPos().x--;
			}
			else if(dir.y>0) {
				dir.y--;
				r.getPos().y++;
			}
			else if(dir.y<0) {
				dir.y++;
				r.getPos().y--;
			}
			
			
			
			if(dir.getX()==0 && dir.getY()==0) { 
				if(stren>0) stren--;
				if(stren==0) {
					dirprev.y = (int)Math.abs(dirprev.getY());
					if (dirprev.y==0 && fallable) dirprev.y=1;
				}				
				dir = new Point(dirprev);
			}
			
			//System.out.println("POS: "+r.getPos());
			
			if(r.isHit(r.getPos(), m, teams)) {
				((Timer)arg0.getSource()).stop();
				if(m.get(r.getPos().x, r.getPos().y) != -1) {
					r.Explode(r.getPos(),m,teams);
					m.Fall(teams[0], teams[1]);
				}
				src=null;
				exists = false;
			}
			
			gp.paintShoot(r,m);
		}		
	}
}
