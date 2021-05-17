package team;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.IOException;

import org.junit.*;

import worm.Worm;

/**
 * Testing Teams class
 */
public class TeamTest {
	
	Team[] teams;
	
	/**
	 * Creates two teams
	 * @throws IOException
	 */
	@Before
	public void init() throws IOException {
		teams = new Team[] {new Team(Color.BLUE,3,20), new Team(Color.RED,3,20)};
	}

	
	/**
	 * Tests if worm is returned from storage,
	 * if out of bounds returns null
	 */
	@Test
	public void testGet() {
		Worm w1 = teams[0].get(3);
		Worm w2 = teams[1].get(0);
		Assert.assertNull(w1);
		Assert.assertNotNull(w2);
	}
	
	/**
	 * Tests if update deletes worms when dead
	 */
	@Test
	public void testUpdate() {
		teams[0].get(0).dmg(30);
		teams[0].update();
		teams[0].get(0).dmg(30);
		teams[0].update();
		teams[0].get(0).dmg(30);
		teams[0].update();
		teams[1].get(0).dmg(30);
		teams[1].update();
		Assert.assertEquals(0, teams[0].size());
		Assert.assertEquals(2, teams[1].size());
	}
	
	/**
	 * tests if next turn is counted 
	 * correctly according to the team size
	 */
	@Test
	public void testTurn() {		
		for (int i = 0 ; i< 7; i++) {
			Assert.assertEquals(i%3, teams[0].currentTurn());
			teams[0].nextTurn();
		}
	}
	
	/**
	 * Tests if new teams are generated when called
	 * @throws IOException
	 */
	@Test
	public void TestGenerate() throws IOException {
		teams[0].get(0).dmg(30);
		teams[0].update();
		Assert.assertEquals(2, teams[0].size());
		teams[0].generate(Color.BLUE, 4, 20);
		Assert.assertEquals(4, teams[0].size());
	}

}
