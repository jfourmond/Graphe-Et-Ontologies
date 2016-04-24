package test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.fourmond.jerome.framework.Placement;

public class TestPlacement {

	private static Placement placement;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception { }

	@AfterClass
	public static void tearDownAfterClass() throws Exception { }

	@Before
	public void setUp() throws Exception {
		placement = new Placement();
	}

	@After
	public void tearDown() throws Exception { }

	@Test
	public void testPlacement() {
		assertTrue(placement.current == 0);
	}

	@Test
	public void testPlacementInt() {
		placement = new Placement(2);
		assertTrue(placement.current == 2);
	}

	@Test
	public void testNext() {
		Point point = placement.next();
		assertTrue(point != null);
	}

}
