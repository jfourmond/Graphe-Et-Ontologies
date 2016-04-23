package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

import fr.fourmond.jerome.framework.Relation;
import fr.fourmond.jerome.framework.RelationException;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.TreeException;
import fr.fourmond.jerome.framework.Vertex;
import fr.fourmond.jerome.framework.VertexException;

public class TestTree {
	private static DocumentBuilderFactory domFactory;
	private static DocumentBuilder domBuilder;
	
	private static final String testFileName="../Ontologies/Villes.xml";
	
	private Tree tree;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception { tree = new Tree(); }

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTree() {
		assertTrue(tree.getFile() == null);
		assertTrue(tree.getDocument() == null);
		assertTrue(tree.getVertices().isEmpty());
		assertTrue(tree.getRelations().isEmpty());
	}

	@Test
	public void testGetFile() {
		assertTrue(tree.getFile() == null);
	}

	@Test
	public void testGetDocument() {
		assertTrue(tree.getDocument() == null);
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
	public void testSetFile() {
		File file = new File(testFileName);
		tree.setFile(file);
		assertTrue(tree.getFile().equals(file));
	}

	@Test
	public void testSetDocument() {
		try {
			File file = new File(testFileName);
			domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setValidating(true);
			
			domBuilder = domFactory.newDocumentBuilder();
			domBuilder.setErrorHandler(tree);
			
			Document document = domBuilder.parse(file);
			tree.setDocument(document);
			assertTrue(tree.getDocument().equals(document));
		} catch(Exception E) {
			E.printStackTrace();
		}
	}

	@Test
	public void testSetVertices() {
		try {
			List<Vertex> vertices = new ArrayList<>();
			vertices.add(new Vertex("1"));
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
	public void testCreateVertex() {
		try {
			tree.createVertex("vertex");
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
			tree.createVertex("vertex");
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
			tree.createVertex("vertex");
			tree.addAttribute("vertex", "attribut", "un");
			assertTrue(tree.getVertexID("un") != null);
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIsID() {
		try {
			tree.createVertex("vertex");
			assertTrue(tree.isID("vertex"));
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateAttribute() {
		try {
			tree.createVertex("vertex");
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
			tree.createVertex("vertex");
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
			tree.createVertex("vertex");
			tree.addAttribute("vertex", "attribut", "un");
			assertTrue(tree.getVertex("vertex").get("attribut").equals("un"));
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateRelation() {
		try {
			tree.createRelation("relation");
			assertTrue(tree.getRelations().size() == 1);
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
	public void testAddPair() {
		try {
			List<Vertex> vertices = new ArrayList<>();
			vertices.add(new Vertex("1"));
			vertices.add(new Vertex("2"));
			tree.setVertices(vertices);
			
			tree.createRelation("relation");
			tree.addPair("relation", "1", "2");
			assertTrue(tree.getRelation("relation").getPairs().size() == 1);
		} catch (TreeException e) {
			e.printStackTrace();
		} catch (RelationException e) {
			e.printStackTrace();
		} catch (VertexException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testReadFromFile() {
		fail("Not yet implemented"); // TODO
	}

}
