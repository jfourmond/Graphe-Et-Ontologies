package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.fourmond.jerome.framework.Edge;
import fr.fourmond.jerome.framework.Tree;

public class TreeTest {

	private static Tree<VertexForTest, Integer> treeTest;
	private static List<VertexForTest> vertices;
	private static int expectedVerticesNumber = 3;
		private static VertexForTest vertex1;
		private static VertexForTest vertex2;
		private static VertexForTest vertex3;
	private static List<Edge<VertexForTest, Integer>> edges;
	private static int expectedEdgesNumber = 2;
		private static Edge<VertexForTest, Integer> edge1;
		private static Edge<VertexForTest, Integer> edge2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		treeTest = new Tree<>();
		vertices = new ArrayList<>();
			vertex1 = new VertexForTest(10);
			vertex2 = new VertexForTest(20);
			vertex3 = new VertexForTest(30);
		edges = new ArrayList<>();
			edge1 = new Edge<VertexForTest, Integer>(vertex1, vertex2, 12);
			edge2 = new Edge<VertexForTest, Integer>(vertex2, vertex3, 23);
		vertices.add(vertex1);
		vertices.add(vertex2);
		vertices.add(vertex3);
		edges.add(edge1);
		edges.add(edge2);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		for(VertexForTest vertex : vertices) {
			treeTest.addVertex(vertex);
		}
		for(Edge<VertexForTest, Integer> edge : edges) {
			treeTest.addEdges(edge);
		}
	}

	@After
	public void tearDown() throws Exception {
		treeTest.clear();
	}

	@Test
	public void testGetVertices() {
		assertEquals(expectedVerticesNumber, treeTest.getVertexNumber());
		List<VertexForTest> vertices = treeTest.getVertices();
		assertEquals(expectedVerticesNumber, treeTest.getVertexNumber());
	}

	@Test
	public void testGetEdges() {
		assertEquals(expectedEdgesNumber, treeTest.getEdgeNumber());
		List<Edge<VertexForTest, Integer>> edges = treeTest.getEdges();
		assertEquals(expectedEdgesNumber, treeTest.getEdgeNumber());
	}

	@Test
	public void testAddVertex() {
		assertEquals(expectedVerticesNumber, treeTest.getVertexNumber());
		treeTest.addVertex(new VertexForTest(40));
		assertEquals(expectedVerticesNumber+1, treeTest.getVertexNumber());
	}

	@Test
	public void testAddEdges() {
		assertEquals(expectedEdgesNumber, treeTest.getEdgeNumber());
		treeTest.addEdges(new Edge<VertexForTest, Integer>(vertex1, vertex3, 13));
		assertEquals(expectedEdgesNumber+1, treeTest.getEdgeNumber());
	}
}
