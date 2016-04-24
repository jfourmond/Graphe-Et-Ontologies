package test;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.fourmond.jerome.framework.ColorDistribution;
import javafx.scene.paint.Color;

public class TestColorDistribution {

	private static ColorDistribution colorDistribution;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception { }

	@AfterClass
	public static void tearDownAfterClass() throws Exception { }

	@Before
	public void setUp() throws Exception {
		colorDistribution = new ColorDistribution();
	}

	@After
	public void tearDown() throws Exception { }

	@Test
	public void testColorDistribution() {
		assertTrue(colorDistribution.current == 0);
	}

	@Test
	public void testColorDistributionInt() {
		colorDistribution = new ColorDistribution(2);
		assertTrue(colorDistribution.current == 2);
	}

	@Test
	public void testNext() {
		Color color = colorDistribution.next();
		assertTrue(color != null);
	}

}
