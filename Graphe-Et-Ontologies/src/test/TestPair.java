package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.fourmond.jerome.framework.Pair;

public class TestPair {

	private static Pair<String, String> pair;
		private static String firstValue = "Premier";
			private static String setFirstValue="First";
		private static String secondValue = "Second";
			private static String setSecondValue="Second";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		pair = new Pair<>();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		pair = new Pair<String, String>(firstValue, secondValue);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPair() {
		pair = new Pair<String, String>();
		assertTrue(pair.getFirst() == null);
		assertTrue(pair.getSecond() == null);
	}

	@Test
	public void testPairT_FirstT_Second() {
		pair = new Pair<String, String>(setFirstValue, setSecondValue);
		assertEquals("La première valeur ne correspond pas.", pair.getFirst(), setFirstValue);
		assertEquals("La seconde valeur ne correspond pas.", pair.getSecond(), setSecondValue);
	}

	@Test
	public void testGetFirst() {
		assertEquals("La valeur ne correspond pas.", pair.getFirst(), firstValue);
	}

	@Test
	public void testGetSecond() {
		assertEquals("La valeur ne correspond pas.", pair.getSecond(), secondValue);
	}

	@Test
	public void testSetFirst() {
		pair.setFirst(setFirstValue);
		assertEquals("La valeur ne correspond pas.", pair.getFirst(), setFirstValue);
	}

	@Test
	public void testSetSecond() {
		pair.setSecond(setSecondValue);
		assertEquals("La valeur ne correspond pas.", pair.getSecond(), setSecondValue);
	}

	@Test
	public void testEqualsObject() {
		Pair<String, String> otherPair = new Pair<String, String>(firstValue, secondValue);
		assertTrue(pair.equals(otherPair));
	}

}
