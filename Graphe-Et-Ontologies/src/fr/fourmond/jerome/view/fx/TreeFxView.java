package fr.fourmond.jerome.view.fx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.fourmond.jerome.framework.Edge;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.Vertex;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

/**
 * {@link TreeFxView} est un {@link Group} représentant
 * la totalité du graphe
 * @param <T_Vertex> : type implémentant l'interface {@link Vertex}
 * @param <T_Edge> : type de la valeur de {@link Edge}
 * @author jfourmond
 */
public class TreeFxView<T_Vertex extends Vertex, T_Edge> extends Group {

	private static Random rand;
	
	private Tree<T_Vertex, T_Edge> tree;
	
	private List<VertexFxView<T_Vertex>> verticesView;
	private List<EdgeFxView<T_Edge>> edgesView;
	
	public TreeFxView(Tree<T_Vertex, T_Edge> tree) {
		super();
		this.tree = tree;
		
		rand = new Random();
		
		buildVertices();
		buildEdges();
		drawVertices();
		drawEdges();
		
		addEvents();
	}
	
	private void buildVertices() {
		verticesView = new ArrayList<>();
		
		List<T_Vertex> vertices = tree.getVertices();
		VertexFxView<T_Vertex> vertexView;
		for(T_Vertex vertex : vertices) {
			vertexView = new VertexFxView<T_Vertex>(vertex, rand.nextInt(500), rand.nextInt(500));
			verticesView.add(vertexView);
		}
	}
	
	private void buildEdges() {
		edgesView = new ArrayList<>();
		
		List<Edge<T_Vertex, T_Edge>> edges = tree.getEdges();
		EdgeFxView<T_Edge> edgeView;
		VertexFxView<T_Vertex> start, end;
		for(Edge<T_Vertex, T_Edge> edge : edges) {
			start = getViewFromVertex(edge.getFirstVertex());
			end = getViewFromVertex(edge.getSecondVertex());
			edgeView = new EdgeFxView<T_Edge>(edge.getValue(), start.getCenterX(), start.getCenterY(),
					end.getCenterX(), end.getCenterY());
			edgesView.add(edgeView);
		}
	}
	
	private void drawVertices() {
		this.getChildren().addAll(verticesView);
	}
	
	private void drawEdges() {
		this.getChildren().addAll(edgesView);
	}
	
	private void redrawLines() {
		this.getChildren().removeAll(edgesView);
		buildEdges();
		drawEdges();
	}
	
	private void addEvents() {
		for(VertexFxView<T_Vertex> vertex : verticesView) {
			vertex.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					System.out.println("Clicked : " + vertex.getVertex());
				}
			});
			vertex.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					vertex.setCenterX(event.getX());
					vertex.setCenterY(event.getY());
					
					redrawLines();
					
					// root.requestLayout();
				}
			});
		}
	}
	
	private VertexFxView<T_Vertex> getViewFromVertex(T_Vertex vertex) {
		for(VertexFxView<T_Vertex> vertexView : verticesView) {
			if(vertexView.getVertex() == vertex)
				return vertexView;
		}
		return null;
	}
}
