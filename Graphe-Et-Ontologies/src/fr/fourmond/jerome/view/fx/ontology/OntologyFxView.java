package fr.fourmond.jerome.view.fx.ontology;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.fourmond.jerome.framework.Edge;
import fr.fourmond.jerome.framework.Placement;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.ontology.Pair;
import fr.fourmond.jerome.ontology.TreeOntology;
import fr.fourmond.jerome.ontology.VertexOntology;
import fr.fourmond.jerome.view.fx.EdgeFxView;
import fr.fourmond.jerome.view.fx.VertexFxList;
import fr.fourmond.jerome.view.fx.VertexFxView;
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
import javafx.util.Callback;

public class OntologyFxView extends BorderPane {
private final double SCALE_DELTA = 1.1;
	
	private Placement placement;
	
	private TreeOntology ontology;
	
	private Pane center;
		private List<VertexFxView<VertexOntology>> verticesView;
		private List<EdgeOntologyFxView> edgesView;
	private VBox east;
		private Label info_label;
		private TextArea info_area;
		private ListView<VertexFxView<VertexOntology>> info_list;
			private ObservableList<VertexFxView<VertexOntology>> verticesViewForList;
	
	private static MouseEvent pressed;
			
	public OntologyFxView(TreeOntology ontology) {
		super();
		this.ontology = ontology;
		
		buildComposants();
		buildInterface();
		
		addEvents();
	}
	
	private void buildComposants() {
		placement = new Placement();
		
		verticesView = new ArrayList<>();
		edgesView = new ArrayList<>();
		
		buildVertices();
		buildEdges();
		
		verticesViewForList = FXCollections.observableArrayList(verticesView);
		
		center = new Pane();
		east = new VBox();
		east.setPrefHeight(east.getMaxHeight());
			info_label = new Label("Informations");
			info_area = new TextArea(ontology.info());
			info_area.setPrefWidth(200);
			info_area.setEditable(false);
			info_list = new ListView<>();
			info_list.setItems(verticesViewForList);
			info_list.setCellFactory(new Callback<ListView<VertexFxView<VertexOntology>>, ListCell<VertexFxView<VertexOntology>>>() {
				@Override
				public ListCell<VertexFxView<VertexOntology>> call(ListView<VertexFxView<VertexOntology>> param) {
					return new VertexFxList<VertexOntology>();
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
		List<VertexOntology> vertices = ontology.getVertices();
		VertexFxView<VertexOntology> vertexView;
		for(VertexOntology vertex : vertices) {
			vertexView = new VertexFxView<VertexOntology>(vertex, placement.next());
			verticesView.add(vertexView);
		}
	}
	
	private void buildEdges() {
		edgesView.clear();
		Map<String, List<Pair<String, String>>> relations = ontology.getRelations();
		
		EdgeFxView<String> edgeView;
		VertexFxView<VertexOntology> start, end;
		for(Edge<VertexOntology, T_Edge> edge : edges) {
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
		for(VertexFxView<VertexOntology> vertex : verticesView) {
			vertex.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					event.consume();
					VertexOntology v = vertex.getVertex();
					info_area.setText(v.fullData());
					vertex.setSelected(true);
					for(VertexFxView<VertexOntology> other: verticesView) {
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
		info_list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<VertexFxView<VertexOntology>>() {
			@Override
			public void changed(ObservableValue<? extends VertexFxView<VertexOntology>> observable,
					VertexFxView<VertexOntology> oldValue, VertexFxView<VertexOntology> newValue) {
						VertexOntology vertex = newValue.getVertex();
						info_area.setText(vertex.fullData());
						newValue.setSelected(true);
						if(oldValue != null) oldValue.setSelected(false);
			}
		});
	}
	
	private VertexFxView<VertexOntology> getViewFromVertex(VertexOntology vertex) {
		for(VertexFxView<VertexOntology> vertexView : verticesView) {
			if(vertexView.getVertex() == vertex)
				return vertexView;
		}
		return null;
	}
}
