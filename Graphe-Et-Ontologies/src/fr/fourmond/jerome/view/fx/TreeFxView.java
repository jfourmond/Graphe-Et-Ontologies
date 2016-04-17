package fr.fourmond.jerome.view.fx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.fourmond.jerome.framework.Edge;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.Vertex;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * {@link TreeFxView} est un {@link BorderPane} représentant
 * la totalité du graphe
 * @param <T_Vertex> : type implémentant l'interface {@link Vertex}
 * @param <T_Edge> : type de la valeur de {@link Edge}
 * @author jfourmond
 */
public class TreeFxView<T_Vertex extends Vertex, T_Edge> extends BorderPane {

	private static Random rand;
	
	private Tree<T_Vertex, T_Edge> tree;
	
	private Pane center;
		private List<VertexFxView<T_Vertex>> verticesView;
		private List<EdgeFxView<T_Edge>> edgesView;
	private Pane east;
		private VBox vbox;
			private TextArea textArea;
	
	public TreeFxView(Tree<T_Vertex, T_Edge> tree) {
		super();
		this.tree = tree;
		
		rand = new Random();
		
		buildComposants();
		buildInterface();
		
		addEvents();
	}
	
	private void buildComposants() {
		center = new Pane();
		east = new Pane();
			vbox = new VBox();
				textArea = new TextArea(tree.toString());
				textArea.setEditable(false);
		verticesView = new ArrayList<>();
		edgesView = new ArrayList<>();
		
		buildVertices();
		buildEdges();
	}
	
	private void buildInterface() {
		
		drawVertices();
		drawEdges();
		
		vbox.getChildren().add(textArea);
		east.getChildren().add(vbox);
		
		setCenter(center);
		setRight(east);
	}
	
	private void buildVertices() {
		verticesView.clear();
		List<T_Vertex> vertices = tree.getVertices();
		VertexFxView<T_Vertex> vertexView;
		for(T_Vertex vertex : vertices) {
			vertexView = new VertexFxView<T_Vertex>(vertex, rand.nextInt(500), rand.nextInt(500));
			verticesView.add(vertexView);
		}
	}
	
	private void buildEdges() {
		edgesView.clear();
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
	
	private void drawVertices() { center.getChildren().addAll(verticesView); }
	
	private void drawEdges() { center.getChildren().addAll(edgesView); }
	
	private void redrawLines() {
		center.getChildren().removeAll(edgesView);
		buildEdges();
		drawEdges();
	}
	
	private void addEvents() {
		for(VertexFxView<T_Vertex> vertex : verticesView) {
			vertex.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					T_Vertex v = vertex.getVertex();
					textArea.setText(v.fullData());
				}
			});
			vertex.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					vertex.setCenterX(event.getX());
					vertex.setCenterY(event.getY());
					
					redrawLines();
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
