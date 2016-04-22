package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.fourmond.jerome.framework.Pair;
import fr.fourmond.jerome.framework.Relation;
import fr.fourmond.jerome.framework.RelationException;
import fr.fourmond.jerome.framework.Vertex;
import fr.fourmond.jerome.framework.VertexException;

public class TestRelation {

	private static Relation relation;
		private static String name="Relation";
		private static String setName="relation";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception { }

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		relation = new Relation(name);
	}

	@After
	public void tearDown() throws Exception { }

	@Test
	public void testRelation() {
		assertEquals("Le nom ne correspond pas.", name, relation.getName());
		assertTrue(relation.getPairs().isEmpty());
	}

	@Test
	public void testGetName() {
		assertEquals("Le nom ne correspond pas.", name, relation.getName());
	}

	@Test
	public void testGetPairs() {
		assertTrue(relation.getPairs().isEmpty());
	}

	@Test
	public void testSetName() {
		try {
			relation.setName(setName);
			assertEquals("Le nom ne correspond pas.", setName, relation.getName());
		} catch (RelationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSetPairs() {
		List<Pair<Vertex, Vertex>> pairs = new ArrayList<>();
		Vertex vertex1;
		Vertex vertex2;
		try {
			vertex1 = new Vertex("0");
			vertex2 = new Vertex("1");
			pairs.add(new Pair<>(vertex1, vertex2));
			relation.setPairs(pairs);
			assertEquals("La taille ne correspond pas.", 1, relation.getPairs().size());
		} catch (VertexException e) {
			e.printStackTrace();
		}	
	}

	@Test
	public void testAdd() {
		Vertex vertex1;
		Vertex vertex2;
		try {
			vertex1 = new Vertex("0");
			vertex2 = new Vertex("1");
			relation.add(new Pair<Vertex, Vertex>(vertex1, vertex2));
		} catch (VertexException e) {
			e.printStackTrace();
		} catch (RelationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testEqualsObject() {
		Relation otherRelation;
		try {
			otherRelation = new Relation(name);
			assertTrue(relation.equals(otherRelation));
		} catch (RelationException e) {
			e.printStackTrace();
		}
	}

}
