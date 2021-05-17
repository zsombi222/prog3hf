package team;
import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import colors.Colors;
import worm.Worm;

/**
 * Stores all the living Worms in the team,
 * which Worm is next and the team color
 */
public class Team {
	
	/**Color of the team*/
	Color color;
	/**the current worm's index*/
	int turn = 0;
	/**All the living Worms in the team*/
	List<Worm> worms;
	
	/**
	 * Creates the new team
	 * @param c Team Color
	 * @param size Number of Worms to be generated
	 * @param HP Max Health Points of Worms
	 * @throws IOException
	 */
	public Team(Color c, int size, int HP) throws IOException {
		color = c;
		worms = new ArrayList<Worm>();
		generate(c, size, HP);
	}
	
	/**
	 * Get number of living worms
	 * @return Size of team
	 */
	public int size() {
		return worms.size();
	}
	
	/**
	 * Get a worm by index
	 * @param i index
	 * @return Worm with index of i
	 */
	public Worm get(int i) {
		if (i<size())
			return worms.get(i);
		else
			return null;
	}
	
	/**
	 * Generating new team
	 * @param c Team Color
	 * @param size Size of team
	 * @param hp Max Health Points of Worms
	 * @throws IOException
	 */
	public void generate(Color c, int size, int hp) throws IOException {
		worms.clear();
		turn = 0;
		Random r = new Random();
		for (int i = 0; i< size; i++) {
			worms.add(new Worm(new Point(r.nextInt(79),0),c,hp));
		}
	}
	
	
	/**
	 * Steps the current turn value
	 * @return current turn, -1 if no more worms available
	 */
	public int nextTurn() {
		
		int t = turn;
		if(size()!=0) {
			turn++;
			turn%=size();
			return t;
		}
		else
			return -1;
			
		
	}
	
	/**
	 * Gives the index of the current Worm
	 * @return the index of current Worm
	 */
	public int currentTurn() {
		if (size() !=0) return turn;
		return -1;
	}
	
	/**
	 * Deleting a dead Worm.
	 * Must be called after each kill.
	 * Checks if game has to an end, and displays the loser team
	 */
	public void update() {
		for (int i = 0; i< worms.size(); i++) {
			if(worms.get(i).getHP()<=0) {
				worms.remove(i);
				if(size()!=0)
					turn%=size();
				if(worms.size()==0) {
					JOptionPane.showMessageDialog(new JFrame(), Colors.ColorToString(color) + " Team Lost." ,
							"MATCH ENDED", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
}
