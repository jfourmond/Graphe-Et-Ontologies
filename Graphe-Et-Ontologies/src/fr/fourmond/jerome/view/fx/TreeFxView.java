package fr.fourmond.jerome.view.fx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.fourmond.jerome.framework.Edge;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.Vertex;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * {@link TreeFxView} est un {@link BorderPane} représentant
 * la totalité du graphe
 * @param <T_Vertex> : type implémentant l'interface {@link Vertex}
 * @param <T_Edge> : type de la valeur de {@link Edge}
 * @author jfourmond
 */
public class TreeFxView<T_Vertex extends Vertex, T_Edge> extends BorderPane {
	private final double SCALE_DELTA = 1.1;
	private static Random rand;
	
	private Tree<T_Vertex, T_Edge> tree;
	
	private Pane center;
		private List<VertexFxView<T_Vertex>> verticesView;
		private List<EdgeFxView<T_Edge>> edgesView;
	private VBox east;
		private Label info_label;
		private TextArea info_area;
	
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

		east = new VBox();
		east.setPrefHeight(east.getMaxHeight());
			info_label = new Label("Informations");
			info_area = new TextArea(tree.info());
			info_area.setPrefWidth(200);
			
			info_area.setEditable(false);
		verticesView = new ArrayList<>();
		edgesView = new ArrayList<>();
		
		buildVertices();
		buildEdges();
	}
	
	private void buildInterface() {
		drawVertices();
		drawEdges();
		
		east.getChildren().addAll(info_label, info_area);
		
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
		center.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				System.out.println("SCROLLED : DeltaX " + event.getDeltaX() + " DeltaY " + event.getDeltaY() +
						"\n X " + event.getX() + " Y " + event.getY());
				if (event.getDeltaY() == 0) {
					return;
				}
				double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1/SCALE_DELTA;
				center.setScaleX(center.getScaleX() * scaleFactor);
				center.setScaleY(center.getScaleY() * scaleFactor);
			}
		});
		
		for(VertexFxView<T_Vertex> vertex : verticesView) {
			vertex.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					event.consume();
					T_Vertex v = vertex.getVertex();
					info_area.setText(v.fullData());
				}
			});
			vertex.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					event.consume();
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
