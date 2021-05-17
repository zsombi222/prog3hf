package map;

import java.awt.Point;
import java.util.*;

import team.Team;
import worm.Worm;

/**
 * Stores a map, containing blocks, on which the game is played.
 */
public class Map {
	/**Stores the block of the map*/
	int[][] map = new int[40][80];
	
	/**
	 * Creates Map
	 * @param h Height of mountains
	 * @param cave Caves turned on/off
	 * @param sym Symmetric or not
	 */
	public Map(int h, boolean cave, boolean sym) {
		generate(h, cave, sym);
	}
	
	/**
	 * Get a block of the map
	 * @param i coordinate x
	 * @param j coordinate y
	 * @return The indexed block, -1 if out of bounds
	 */
	public int get(int i, int j) {
		if(j>=40 || i>=80 || i<0) {
			return -1;
		}
		if(j<0) return 0;
		return map[j][i];
	}
	
	/**
	 * Sets a block
	 * @param i coordinate x
	 * @param j coordinate y
	 * @param block Block (id)
	 */
	public void set(int i, int j, int block) {
		try{
			map[j][i] = block;
		}
		catch(ArrayIndexOutOfBoundsException e) {
			//do nothing
			//tile cannot be set
		}
	}
	
	
	/**
	 * Generates new map
	 * @param h Height of mountains
	 * @param cave Caves turned on/off
	 * @param sym Symmetric or not
	 */
	public void generate(int h, boolean cave, boolean sym) {
		for(int i = 0; i< 80; i++) {
			for(int j = 0; j< 40; j++){
				map[j][i]=0;
			}
		}		
		Random r = new Random();
		int prev = 20;
		for (int l = 0; l< 80; l++) {
			prev = prev + r.nextInt(2*h-1)-((2*h-1)/2); //5-2
			int high_step = r.nextInt(3*h)-(3*h/2); //9-4
			if(Math.abs(high_step)<h) prev+=high_step; //3
			if(prev<10) prev = 10;
			if(prev>35) prev = 35;
						
			for (int k = prev; k<40; k++) {	
				if(k==prev) {
					map[k][l]=1;
				}
				else {
					map[k][l] = 2;
				}			
			}
		}
		
		if (cave) {
			for (int i = 0; i<5; i++) {
				int caveheight=r.nextInt(4)+3;
				int cavelen=r.nextInt(20)+5;
				int cavex=r.nextInt(49)+5;
				int cavey=r.nextInt(17)+15;
				for(int j = 0; j < cavelen; j++) {
					for(int k = 0; k < caveheight; k++) {
						if(map[cavey+k][cavex+j]!=0) {
							map[cavey+k][cavex+j]=3;
						}
					}
					if(map[cavey+caveheight][cavex+j]!=0) {
						map[cavey+caveheight][cavex+j]=1;
					}
				}
			}			
		}
		
		if (sym) {
			for(int i = 0; i< 40; i++) {
				for(int j = 0; j< 40; j++) {
					map[j][79-i]=map[j][i];
				}
			}
		}
	}
	
	
	/**
	 * Sets the current worm's next position on the map
	 * @param w Current worm
	 * @param t1 team 1
	 * @param t2 team 2
	 * @param dir Direction of step
	 */
	public void getStep(Worm w, Team t1, Team t2, int dir) {
		
		Team[] ts = {t1, t2};
		Point p = new Point(w.getPos().x, w.getPos().y);
		
		if((get(p.x+dir,p.y)==0 || get(p.x+dir,p.y)==3) && 
				wormHasPos(ts,new Point(p.x+dir,p.y)) == null) {
			p.x=p.x+dir;
		}
		else if ((get(p.x+dir,p.y-1)==0 || get(p.x+dir,p.y-1)==3) && 
				wormHasPos(ts,new Point(p.x+dir,p.y-1)) == null) {
			p.x=p.x+dir;
			p.y--;
		}
		else if ((get(p.x+dir,p.y-2)==0 || get(p.x+dir,p.y-2)==3) && 
				wormHasPos(ts,new Point(p.x+dir,p.y-2)) == null) {
			p.x=p.x+dir;
			p.y-=2;
		}
		
		w.move(p);
		Fall(t1,t2);
	}
	
	/**
	 * If there is nothing under a Worm, then it falls down until there is
	 * @param t1 team 1
	 * @param t2 team 2
	 */
	public void Fall(Team t1, Team t2) {
		Team[] ts = {t1, t2};
		for (Team t: ts) {
			for (int i = 0; i< t.size(); i++) {
				Point p = new Point(t.get(i).getPos().x,t.get(i).getPos().y);
				while((get(p.x,p.y+1)==0 || get(p.x,p.y+1)==3)){
					if(wormHasPos(ts,new Point(p.x,p.y+1)) != null) {
						t.get(i).move(p);
						break;
					}
					p.y++;
				}
				if(!t.get(i).getPos().equals(p)) {
					t.get(i).move(p);
					Fall(t1,t2);
				}				
			}
		}		
	}
	
	
	/**
	 * Checks if any worm has a position at the given point
	 * @param t teams
	 * @param p Position
	 * @return true if any worm is on p
	 */
	public Worm wormHasPos(Team[] t, Point p) {
		for (Team team : t) {
			for(int j = 0; j< team.size(); j++) {
				if(team.get(j).getPos().equals(p)) {
					return team.get(j);
				}
			}
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
