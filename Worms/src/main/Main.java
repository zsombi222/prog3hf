package main;
import java.io.IOException;
import frame.Frame;

/**
 * The only purpose of the class is to 
 * create the main frame and start the program
 */


public class Main {
	
	/**
	 * Creating game frame
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Frame game = new Frame();
		game.show();
	}
}
