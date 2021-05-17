package map;

import static org.junit.Assert.*;
import org.junit.*;

import org.junit.Test;

/**
 * Testing map class
 */
public class MapTest {

	/**
	 * Tests if setting a block on the map works
	 */
	@Test
	public void testSetBlock() {
		Map m = new Map(3,false,false);
		Assert.assertNotEquals(0, m.get(30,39));
		m.set(30, 39, 0);
		Assert.assertEquals(0, m.get(30,39));
	}
	
	/**
	 * Tests if generating generates a new map, 
	 * which is not identical with the previous one.
	 */
	@Test
	public void TestGenereate() {
		Map m1 = new Map(3, false, false);
		Map m2 = new Map(3, false, false);
		
		for (int i = 0; i< 80; i++) {
			for (int j = 0; j< 40; j++) {
				m2.set(i, j, m1.get(i,j));
			}
		}
				
		m1.generate(3, false, false);
	
		boolean diff = false;
		
		for (int i = 0; i< 80 && !diff; i++) {
			for (int j = 0; j< 40 && !diff; j++) {
				if(m1.get(i, j) != m2.get(i, j)) {
					diff = true;
				}
			}
		}
		
		Assert.assertTrue(diff);
		
	}
	
	/**
	 * Tests if getting a block gives the right block, 
	 * and if out of bounds it returns -1
	 */
	@Test
	public void testGet() {
		Map m1 = new Map(4, true, true);
		Assert.assertEquals(0, m1.get(0, 0));
		Assert.assertEquals(-1, m1.get(90, 47));
	}
}