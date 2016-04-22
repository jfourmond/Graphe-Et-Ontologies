/**
 * 
 */
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.fourmond.jerome.framework.Vertex;
import fr.fourmond.jerome.framework.VertexException;

public class TestVertex {

	private static Vertex vertex;
		private static String ID="0";
		private static String name="Nom";
			private static String nameValue="Sommet";
			private static String setNameValue="Vertex";
		private static String age="age";
		
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		vertex = new Vertex(ID);
		vertex.add(name);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception { }

	@Before
	public void setUp() throws Exception { vertex.set(name, nameValue); }

	@After
	public void tearDown() throws Exception {}

	@Test
	public void testVertex() {
		assertEquals("L'ID est incorrect.", ID, vertex.getID());
	}

	@Test
	public void testGet() {
		assertEquals("L'attribut " + name + " est incorrect.", nameValue, vertex.get(name));
	}

	@Test
	public void testSet() {
		try {
			vertex.set(name, setNameValue);
			assertEquals("L'attribut " + name + " est incorrect.", setNameValue, vertex.get(name));
		} catch (VertexException e) {
			e.printStackTrace();
			// fail("L'attribut n'existe pas.");
		}
	}

	@Test
	public void testContainsAttribute() {
		assertTrue(vertex.containsAttribute(name));
	}

	@Test
	public void testAdd() {
		try {
			vertex.add(age);
			assertTrue(vertex.containsAttribute(age));
		} catch (VertexException e) {
			e.printStackTrace();
			// fail("L'attribut n'a pas pu être ajouté.");
		}
	}

	@Test
	public void testIsValueOfAttribute() {
		assertTrue(vertex.isValueOfAttribute(nameValue));
	}

	@Test
	public void testEqualsObject() {
		try {
			Vertex otherVertex = new Vertex("0");
			assertTrue(vertex.equals(otherVertex));
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}

}
