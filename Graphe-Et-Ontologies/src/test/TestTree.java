package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
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
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.TreeException;
import fr.fourmond.jerome.framework.Vertex;
import fr.fourmond.jerome.framework.VertexException;

public class TestTree {
	private static final String testFileName="../Ontologies/Villes.xml";
	
	private Tree tree;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception { }

	@AfterClass
	public static void tearDownAfterClass() throws Exception { }

	@Before
	public void setUp() throws Exception { tree = new Tree(); }

	@After
	public void tearDown() throws Exception { }

	@Test
	public void testTree() {
		assertTrue(tree.getFile() == null);
		assertTrue(tree.getVertices().isEmpty());
		assertTrue(tree.getRelations().isEmpty());
	}

	@Test
	public void testGetFile() {
		assertTrue(tree.getFile() == null);
	}

	@Test
	public void testGetVertices() {
		assertTrue(tree.getVertices().isEmpty());
	}

	@Test
	public void testGetRelations() {
		assertTrue(tree.getRelations().isEmpty());
	}

	@Test
	public void testGetAutoID() {
		assertTrue(tree.getAutoID() == 0);
	}
	
	@Test
	public void testSetFile() {
		File file = new File(testFileName);
		tree.setFile(file);
		assertTrue(tree.getFile().equals(file));
	}

	@Test
	public void testSetVertices() {
		try {
			List<Vertex> vertices = new ArrayList<>();
			vertices.add(new Vertex("1", "nom"));
			tree.setVertices(vertices);
			assertEquals("La taille ne correspond pas.", tree.getVertices().size(), 1);
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSetRelations() {
		try {
			List<Relation> relations = new ArrayList<>();
			relations.add(new Relation("relation"));
			tree.setRelations(relations);
			assertEquals("La taille ne correspond pas.", tree.getRelations().size(), 1);
		} catch (RelationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSetAutoID() {
		int value = 10;
		tree.setAutoID(value);
		assertTrue(tree.getAutoID() == value);
	}
	
	@Test
	public void testCreateVertexString() {
		try {
			tree.createVertex("vertex", "nom");
			assertEquals("La taille ne correspond pas.", tree.getVertices().size(), 1);
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateVertexVertex() {
		try {
			Vertex vertex = new Vertex("vertex", "nom");
			tree.createVertex(vertex);
			assertEquals("La taille ne correspond pas.", tree.getVertices().size(), 1);
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetVertex() {
		try {
			tree.createVertex("vertex", "nom");
			assertEquals("Le sommet ne correspond pas.", tree.getVertex("vertex").getID(), "vertex");
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetVertexID() {
		try {
			tree.createVertex("vertex", "nom");
			tree.addAttribute("vertex", "attribut", "un");
			assertTrue(tree.getVertexID("un") != null);
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRemoveVertex() {
		try {
			Vertex vertex = new Vertex("vertex", "nom");
			tree.createVertex(vertex);
			tree.removeVertex(vertex);
			assertEquals("La taille ne correspond pas.", tree.getVertices().size(), 0);
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNbVertices() {
		assertEquals(tree.nbVertices(), 0);
	}
	
	@Test
	public void testIsID() {
		try {
			tree.createVertex("vertex", "nom");
			assertTrue(tree.isID("vertex"));
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCurrentID() {
		assertEquals(tree.currentID(), 0);
	}
	
	@Test
	public void testNextID() {
		tree.nextID();
		assertEquals(tree.currentID(), 1);
	}
	
	@Test
	public void testIsVerticesEmpty() {
		assertTrue(tree.isVerticesEmpty());
	}
	
	@Test
	public void testCreateAttribute() {
		try {
			tree.createVertex("vertex", "nom");
			tree.createAttribute("vertex", "attribut");
			tree.setAttribute("vertex", "attribut", "un");
			assertTrue(tree.getVertex("vertex").get("attribut").equals("un"));
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSetAttribute() {
		try {
			tree.createVertex("vertex", "nom");
			tree.createAttribute("vertex", "attribut");
			tree.setAttribute("vertex", "attribut", "un");
			assertTrue(tree.getVertex("vertex").get("attribut").equals("un"));
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddAttribute() {
		try {
			tree.createVertex("vertex", "nom");
			tree.addAttribute("vertex", "attribut", "un");
			assertTrue(tree.getVertex("vertex").get("attribut").equals("un"));
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateRelationString() {
		try {
			tree.createRelation("relation");
			assertEquals(tree.getRelations().size(), 1);
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (RelationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateRelationRelation() {
		try {
			Relation relation = new Relation("relation");
			tree.createRelation(relation);
			assertEquals(tree.getRelations().size(), 1);
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (RelationException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetRelation() {
		try {
			tree.createRelation("relation");
			assertTrue(tree.getRelation("relation") != null);
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (RelationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testContainsRelation() {
		try {
			tree.createRelation("relation");
			assertTrue(tree.containsRelation("relation"));
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (RelationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRemoveRelation() {
		try {
			tree.createRelation("relation");
			tree.removeRelation("relation");
			assertEquals(tree.getRelations().size(), 0);
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (RelationException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddPairString() {
		try {
			List<Vertex> vertices = new ArrayList<>();
			vertices.add(new Vertex("1", "nom1"));
			vertices.add(new Vertex("2", "nom1"));
			tree.setVertices(vertices);
			
			tree.createRelation("relation");
			tree.addPair("relation", "1", "2");
			assertEquals(tree.getRelation("relation").getPairs().size(), 1);
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (RelationException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddPairVertex() {
		try {
			Vertex v1 = new Vertex("1", "nom1");
			Vertex v2 = new Vertex("2", "nom2");
			List<Vertex> vertices = new ArrayList<>();
			vertices.add(v1);
			vertices.add(v2);
			tree.setVertices(vertices);
			
			tree.createRelation("relation");
			tree.addPair("relation", v1, v2);
			assertEquals(tree.getRelation("relation").getPairs().size(), 1);
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (RelationException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddPairPair() {
		try {
			Vertex v1 = new Vertex("1", "nom1");
			Vertex v2 = new Vertex("2", "nom2");
			List<Vertex> vertices = new ArrayList<>();
			vertices.add(v1);
			vertices.add(v2);
			tree.setVertices(vertices);
			
			Pair<Vertex, Vertex> pair = new Pair<Vertex, Vertex>(v1, v2);
			
			tree.createRelation("relation");
			tree.addPair("relation", pair);
			assertEquals(tree.getRelation("relation").getPairs().size(), 1);
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (RelationException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRemovePair() {
		try {
			Vertex v1 = new Vertex("1", "nom1");
			Vertex v2 = new Vertex("2", "nom2");
			List<Vertex> vertices = new ArrayList<>();
			vertices.add(v1);
			vertices.add(v2);
			tree.setVertices(vertices);
			
			Pair<Vertex, Vertex> pair = new Pair<Vertex, Vertex>(v1, v2);
			
			tree.createRelation("relation");
			tree.addPair("relation", pair);
			tree.removePair("relation", pair);
			assertEquals(tree.getRelation("relation").getPairs().size(), 0);
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (RelationException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRemoveRelatedPair() {
		try {
			Vertex v1 = new Vertex("1", "nom1");
			Vertex v2 = new Vertex("2", "nom2");
			List<Vertex> vertices = new ArrayList<>();
			vertices.add(v1);
			vertices.add(v2);
			tree.setVertices(vertices);
			
			Pair<Vertex, Vertex> pair = new Pair<Vertex, Vertex>(v1, v2);
			
			tree.createRelation("relation");
			tree.addPair("relation", pair);
			tree.removeRelatedPair(v1);
			assertEquals(tree.getRelation("relation").getPairs().size(), 0);
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (RelationException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRelationCount() {
		try {
			Vertex v1 = new Vertex("1", "nom1");
			Vertex v2 = new Vertex("2", "nom2");
			List<Vertex> vertices = new ArrayList<>();
			vertices.add(v1);
			vertices.add(v2);
			tree.setVertices(vertices);
			
			tree.createRelation("relation");
			assertEquals(tree.getRelation("relation").getPairs().size(), 0);
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (RelationException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testClear() {
		try {
			Vertex v1 = new Vertex("1", "nom1");
			Vertex v2 = new Vertex("2", "nom2");
			List<Vertex> vertices = new ArrayList<>();
			vertices.add(v1);
			vertices.add(v2);
			tree.setVertices(vertices);
			
			tree.createRelation("relation");
			tree.clear();
			
			assertTrue(tree.getVertices().size() == 0 && tree.getRelations().size() == 0);
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (RelationException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}
}
