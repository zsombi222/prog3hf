package worm;

import static org.junit.Assert.*;


import java.awt.Color;
import java.io.IOException;

import org.junit.*;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;

import org.junit.Test;

import colors.Colors;


/**
 * Testing Worm class
 */
public class WormTest {
	
	
	/**
	 * Tests if creating a worm works.
	 * A not existing color is given so there should be no file to be read
	 * @throws IOException
	 */
	@Test (expected = IOException.class)
	public void testCreateWormWithWrongParams() throws IOException {
		Worm w = new Worm(new Point(1,5), Color.BLACK, 40);
	}
	
	/**
	 * Test if the correct value is returned as new health.
	 * @throws IOException
	 */
	@Test
	public void testDmg() throws IOException {
		Worm w = new Worm(new Point(1,5), Color.BLUE, 40);
		w.dmg(23);
		Assert.assertEquals(17,w.getHP());
	}
	
	
	/**
	 * Tests if correct color is returned.
	 * @throws IOException
	 */
	@Test
	public void testGetColor() throws IOException {
		Worm w = new Worm(new Point(1,5), Color.BLUE, 40);
		int c = Colors.ColorToInt(w.getColor());
		Assert.assertEquals(Colors.ColorToInt(Color.BLUE), c);
	}

}
