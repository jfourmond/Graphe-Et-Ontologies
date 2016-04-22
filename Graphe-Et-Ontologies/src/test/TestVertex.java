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

/**
 * @author Jérôme
 *
 */
public class TestVertex {

	private static Vertex vertex;
		private static String ID="0";
		private static String name="Nom";
			private static String nameValue="Sommet";
			private static String setNameValue="Vertex";
		private static String age="age";
		
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		vertex = new Vertex(ID);
		vertex.add(name);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		vertex.set(name, nameValue);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link fr.fourmond.jerome.framework.Vertex#Vertex(java.lang.String)}.
	 */
	@Test
	public void testVertex() {
		assertEquals("L'ID est incorrect.", ID, vertex.getID());
	}

	/**
	 * Test method for {@link fr.fourmond.jerome.framework.Vertex#get(java.lang.String)}.
	 */
	@Test
	public void testGet() {
		assertEquals("L'attribut " + name + " est incorrect.", nameValue, vertex.get(name));
	}

	/**
	 * Test method for {@link fr.fourmond.jerome.framework.Vertex#set(java.lang.String, java.lang.String)}.
	 */
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

	/**
	 * Test method for {@link fr.fourmond.jerome.framework.Vertex#containsAttribute(java.lang.String)}.
	 */
	@Test
	public void testContainsAttribute() {
		assertTrue(vertex.containsAttribute(name));
	}

	/**
	 * Test method for {@link fr.fourmond.jerome.framework.Vertex#add(java.lang.String)}.
	 */
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

	/**
	 * Test method for {@link fr.fourmond.jerome.framework.Vertex#isValueOfAttribute(java.lang.String)}.
	 */
	@Test
	public void testIsValueOfAttribute() {
		assertTrue(vertex.isValueOfAttribute(nameValue));
	}

	/**
	 * Test method for {@link fr.fourmond.jerome.framework.Vertex#equals(java.lang.Object)}.
	 */
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
