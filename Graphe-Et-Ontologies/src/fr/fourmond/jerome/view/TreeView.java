package fr.fourmond.jerome.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fr.fourmond.jerome.framework.ColorDistribution;
import fr.fourmond.jerome.framework.Pair;
import fr.fourmond.jerome.framework.Placement;
import fr.fourmond.jerome.framework.Relation;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.Vertex;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class TreeView extends BorderPane {
private final double SCALE_DELTA = 1.1;
	
	private Placement placement;
	private ColorDistribution colorDistribution;
	
	private Tree tree;
	
	private Pane center;
		private List<VertexView> verticesView;
		private Map<String, List<EdgeView>> edgesView;
	private VBox east;
		private Label info_label;
		private TextArea info_area;
		private ListView<VertexView> info_list;
			private ObservableList<VertexView> verticesViewForList;
	
	private static MouseEvent pressed;
			
	public TreeView(Tree tree) {
		super();
		this.tree = tree;
		
		buildComposants();
		buildInterface();
		
		addEvents();
		
		setMinSize(800, 400);
	}
	
	private void buildComposants() {
		placement = new Placement();
		
		verticesView = new ArrayList<>();
		edgesView = new HashMap<>();
		
		buildVertices();
		buildEdges();
		
		verticesViewForList = FXCollections.observableArrayList(verticesView);
		
		center = new Pane();
		east = new VBox();
		east.setPrefHeight(east.getMaxHeight());
			info_label = new Label("Informations");
			info_area = new TextArea(tree.toString());
			info_area.setPrefWidth(200);
			info_area.setEditable(false);
			info_list = new ListView<>();
			info_list.setItems(verticesViewForList);
			info_list.setCellFactory(new Callback<ListView<VertexView>, ListCell<VertexView>>() {
				@Override
				public ListCell<VertexView> call(ListView<VertexView> param) {
					return new VertexList();
				}
			});
	}
	
	private void buildInterface() {
		drawVertices();
		drawEdges();
		
		east.getChildren().addAll(info_label, info_area, info_list);
		
		setCenter(center);
		setRight(east);
	}
	
	private void buildVertices() {
		verticesView.clear();
		List<Vertex> vertices = tree.getVertices();
		VertexView vertexView;
		for(Vertex vertex : vertices) {
			vertexView = new VertexView(vertex, placement.next());
			verticesView.add(vertexView);
		}
	}
	
	private void buildEdges() {
		edgesView.clear();
		List<Relation> relations = tree.getRelations();
		colorDistribution = new ColorDistribution();
		Color color;
		VertexView start, end;
		EdgeView edgeView;
		for(Relation relation : relations) {
			List<EdgeView> relationEdges = new ArrayList<>();
			color = colorDistribution.next();
			String relationName = relation.getName();
			List<Pair<Vertex, Vertex>> pairs = relation.getPairs();
			for(Pair<Vertex, Vertex> pair : pairs) {
				start = getViewFromVertex(pair.getFirst());
				end = getViewFromVertex(pair.getSecond());
				edgeView = new EdgeView(relationName, start.getCenterX(), start.getCenterY(),
						end.getCenterX(), end.getCenterY(), color);
				relationEdges.add(edgeView);
			}
			edgesView.put(relationName, relationEdges);
		}
	}
	
	private void drawVertices() { center.getChildren().addAll(verticesView); }
	
	private void drawEdges() {
		Set<Entry<String, List<EdgeView>>> relationSet = edgesView.entrySet();
		for(Entry<String, List<EdgeView>> entry : relationSet) {
			center.getChildren().addAll(entry.getValue());
		}
	}
	
	private void removeEdges() {
		Set<Entry<String, List<EdgeView>>> relationSet = edgesView.entrySet();
		for(Entry<String, List<EdgeView>> entry : relationSet) {
			center.getChildren().removeAll(entry.getValue());
		}
	}
	
	private void redrawLines() {
		removeEdges();
		buildEdges();
		drawEdges();
	}
	
	private void addEvents() {
		center.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				if (event.getDeltaY() == 0) {
					return;
				}
				double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1/SCALE_DELTA;
				center.setScaleX(center.getScaleX() * scaleFactor);
				center.setScaleY(center.getScaleY() * scaleFactor);
			}
		});
		center.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				double ecartx = event.getX() + center.getTranslateX() - pressed.getX();
				double ecarty = event.getY() + center.getTranslateY() - pressed.getY();
				System.out.println("Ecart : " + ecartx + " " + ecarty);
				
				// TODO ajouter une sécurité
				center.setTranslateX(ecartx);
				center.setTranslateY(ecarty);
			}
		});
		center.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				pressed = event;
			}
		});
		for(VertexView vertex : verticesView) {
			vertex.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					event.consume();
					Vertex v = vertex.getVertex();
					info_area.setText(v.toString());
					vertex.setSelected(true);
					for(VertexView other: verticesView) {
						if(other != vertex) other.setSelected(false);
					}
					info_list.getSelectionModel().select(vertex);
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
		info_list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<VertexView>() {
			@Override
			public void changed(ObservableValue<? extends VertexView> observable,
					VertexView oldValue, VertexView newValue) {
						Vertex vertex = newValue.getVertex();
						info_area.setText(vertex.toString());
						newValue.setSelected(true);
						if(oldValue != null) oldValue.setSelected(false);
			}
		});
	}
	
	private VertexView getViewFromVertex(Vertex vertex) {
		for(VertexView vertexView : verticesView) {
			if(vertexView.getVertex() == vertex)
				return vertexView;
		}
		return null;
	}
}
