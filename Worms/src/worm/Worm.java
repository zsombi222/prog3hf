package worm;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import colors.Colors;

/**
 * Worm character
 */
public class Worm {
	/**Position*/
	private Point p;
	/**Health points*/
	private int hp;
	/**Color*/
	private Color color;
	/**Image*/
	private BufferedImage src;
	
	/**
	 * Constructor
	 * @param P Point
	 * @param c Color
	 * @param HP Health points
	 * @throws IOException
	 */
	public Worm(Point P, Color c, int HP) throws IOException{
		p=P;
		src = ImageIO.read(new File("w"+ Colors.ColorToInt(c) +".png"));
		color = c;
		hp = HP;
	}
	
	/**
	 * Moves worm to a position
	 * @param P Position
	 */
	public void move(Point P){
		p = P;
	}
	
	/**
	 * getting current position of the worm
	 * @return position 
	 */
	public Point getPos() {
		return p;
	}
	
	/**
	 * Image of the worm
	 * @return The source image of the character
	 */
	public BufferedImage getSrc()  {
		return src;
	}
	
	/**
	 * Damage
	 * @param n HP to be extracted 
	 */
	public void dmg(int n) {
		hp-=n;
	}

	/**
	 * Health points
	 * @return HP of the worm
	 */
	public int getHP() {
		return hp;
	}
	
	/**
	 * Color
	 * @return Color of the worm
	 */
	public Color getColor() {
		return color;
	}
}
