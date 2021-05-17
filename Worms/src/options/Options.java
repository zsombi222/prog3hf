package options;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *Stores the game setting values
 */
public class Options implements Serializable{
	/**Size of both teams */
	public int teamSize; 
	/** Type of map, the size of mountains generated*/
	public int maptype;
	/** Max health point of a character*/
	public int maxHP;
	
	/** Color of team, the colors cannot be the same*/
	public Color color1, color2;
	/** True if the map has caves*/
	public boolean caves;
	/** True if the map is symmetric */
	public boolean symmetric;	
	
	/**
	 * Constructor of Options
	 * @param ts Team Size
	 * @param mt Map TYpe
	 * @param hp Max Health Points
	 * @param c1 Team color 1
	 * @param c2 Team color 2
	 * @param c caves
	 * @param s symmetric
	 */
	public Options(int ts, int mt, int hp, Color c1, Color c2, boolean c, boolean s){
		teamSize = ts;
		maptype = mt;
		maxHP = hp;
		color1 = c1;
		color2 = c2;
		caves = c;
		symmetric = s;
	}
	
	/**
	 * Reads serialized options file
	 * @param fname File name
	 * @return the read object
	 */
	public Options readOption(String fname) {
		Options opt = null;
		try {
			File f = new File(fname);
			FileInputStream fr = new FileInputStream(f);
			ObjectInputStream is = new ObjectInputStream(fr);
			opt = (Options)is.readObject();
			is.close();
			fr.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return opt;
	}
	
	
	/**
	 * Saves the Options to serialized file
	 * @param fname File name
	 */
	public void writeOption(String fname) {
		
        try {
        	//opt = createOpt();	        	 	        	 
        	FileOutputStream f = new FileOutputStream(fname);
            ObjectOutputStream out = new ObjectOutputStream(f);
			out.writeObject(this);
			out.close();
	        f.close();
	        //hide();
		} catch (IOException e) {
			e.printStackTrace();
		}       
	}
}
