package colors;

import java.awt.Color;

/**
 * Helps to convert between Integer Color and String
 */
public class Colors{
	
	/**
	 * Converts Color to Integer
	 * @param c Color
	 * @return The index of color in the color setter Combo box
	 */
	public static int ColorToInt(Color c) {
		if (c.equals(Color.BLUE)) {
			return 0;
		}
		if (c.equals(Color.RED)) {
			return 1;
		}
		if (c.equals(Color.GREEN)) {
			return 2;
		}
		if (c.equals(Color.YELLOW)) {
			return 3;
		}
		return 4;
	}
	
	/**
	 * Converts Integer to Color
	 * @param n Number
	 * @return The color in the color setter Combo box indexed with n
	 */
	public static Color IntToColor(int n) {
		if (n==0) {
			return Color.BLUE;
		}
		if (n==1) {
			return Color.RED;
		}
		if (n==2) {
			return Color.GREEN;
		}
		if (n==3) {
			return Color.YELLOW;
		}
		return null;
	}
	
	/**
	 * Converts Color to String
	 * @param c Color
	 * @return A string which is the name of the color
	 */
	public static String ColorToString(Color c) {
		if (c.equals(Color.BLUE)) {
			return "Blue";
		}
		if (c.equals(Color.RED)) {
			return "Red";
		}
		if (c.equals(Color.GREEN)) {
			return "Green";
		}
		if (c.equals(Color.YELLOW)) {
			return "Yellow";
		}
		return null;
	}

}
