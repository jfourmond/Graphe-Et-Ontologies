package fr.fourmond.jerome.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
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
import fr.fourmond.jerome.framework.RelationException;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.TreeException;
import fr.fourmond.jerome.framework.TreeLoader;
import fr.fourmond.jerome.framework.TreeSaver;
import fr.fourmond.jerome.framework.Vertex;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * {@link TreeView} est un {@link BorderPane} affichant le graphe et ses informations.
 * En son centre se place un {@link Pane} composé de {@link VertexView} et de {@link EdgeView}.
 * A droite se place un {@link TextArea} affichant les informations du graphe, ainsi qu'une
 * liste des sommets cliquables.
 * @author jfourmond
 */
public class TreeView extends BorderPane {
	private final double SCALE_DELTA = 1.1;
	
	private Placement placement;
	private ColorDistribution colorDistribution;
	
	private Tree tree;
	
	private MenuBar menuBar;
	private Menu menu_file;
		private MenuItem item_new;
		private MenuItem item_open;
		private MenuItem item_save;
		private MenuItem item_save_under;
		private MenuItem item_quit;
	private Menu menu_edit;
		private MenuItem item_ontologie;
		private SeparatorMenuItem item_separator;
		private MenuItem item_add_vertex;
		private MenuItem item_add_relation;
		private Menu menu_add_edge;
			private List<MenuItem> item_add_edge_relations;
	private Menu menu_view;
		private Menu menu_view_relations;
		private List<CheckMenuItem> item_view_relations;
	private Menu menu_tools;
		private MenuItem item_tools_data;
	private Menu menu_settings;
		private CheckMenuItem item_auto_save;
	
	private Pane center;
		private List<VertexView> verticesView;
		private Map<String, List<EdgeView>> edgesView;
	private VBox east;
		private Label info_label;
		private TextArea info_area;
		private ListView<VertexView> info_list;
			private ObservableList<VertexView> verticesViewForList;
	private HBox bottom;
		private ProgressBar pb;
		private Label info_progress;
		
	private ContextMenu vertexContextMenu;
		private MenuItem vCM_edit;
		private MenuItem vCM_delete;
		private SeparatorMenuItem vCM_separator;
		private MenuItem vCM_add_vertex;
		private MenuItem vCM_add_relation;
		private Menu vCM_add_edge;
			private List<MenuItem> vCM_add_edge_relations;
	
	private ContextMenu edgeContextMenu;
		private MenuItem eCM_edit;
		private MenuItem eCM_delete;
		private SeparatorMenuItem eCM_separator;
		private MenuItem eCM_add_vertex;
		private MenuItem eCM_add_relation;
		private Menu eCM_add_edge;
			private List<MenuItem> eCM_add_edge_relations;
			
	private ContextMenu treeContextMenu;
		private MenuItem tCM_add_vertex;
		private MenuItem tCM_add_relation;
		private Menu tCM_add_edge;
			private List<MenuItem> tCM_add_edge_relations;
			
	private FileChooser fileChooser;
			
	private static MouseEvent pressed;
	private static VertexView vertexViewSelected;
	private static String relationToEdit;
	private static Pair<Vertex, Vertex> pairToEdit;
	
	public TreeView(Tree tree) {
		super();
		this.tree = tree;
		build();
		setMinSize(800, 400);
	}
	
	private void build() {
		buildComposants();
		buildInterface();
		buildEvents();
	}
	
	private void buildComposants() {
		placement = new Placement();
		colorDistribution = new ColorDistribution();
		fileChooser = new FileChooser();
		
		item_add_edge_relations = new ArrayList<>();
		item_view_relations = new ArrayList<>();
		vCM_add_edge_relations = new ArrayList<>();
		eCM_add_edge_relations = new ArrayList<>();
		tCM_add_edge_relations = new ArrayList<>();
		
		verticesView = new ArrayList<>();
		edgesView = new HashMap<>();
		
		// TODO gestion dans le cas d'une ouverture directe de fichier
		// buildVertices();
		// buildEdges();
		
		verticesViewForList = FXCollections.observableArrayList(verticesView);
		
		// Barre de Menu
		menuBar = new MenuBar();
		menu_file = new Menu("Fichier");
			item_new = new MenuItem("Nouveau");
			item_open = new MenuItem("Ouvrir");
			item_save = new MenuItem("Enregistrer");
			item_save_under = new MenuItem("Enregistrer sous");
			item_quit = new MenuItem("Quitter");
		menu_edit = new Menu("Edition");
			item_ontologie = new MenuItem("Ontologie");
			item_separator = new SeparatorMenuItem();
			item_add_vertex = new MenuItem("Nouveau sommet");
			item_add_relation = new MenuItem("Nouvelle relation");
			menu_add_edge = new Menu("Nouvel arc");
		menu_view = new Menu("Affichage");
			item_tools_data = new MenuItem("Données");
		menu_tools = new Menu("Outils");
		menu_settings = new Menu("Options");
			item_auto_save = new CheckMenuItem("Sauvegarde automatique");
		
		if(tree.nbRelations() > 1)
			menu_view_relations = new Menu("Relations");
		else
			menu_view_relations = new Menu("Relation");

		// Menus Contextuels
		vertexContextMenu = new ContextMenu();
			vCM_edit = new MenuItem("Editer");
			vCM_delete = new MenuItem("Supprimer");
			vCM_separator = new SeparatorMenuItem();
			vCM_add_vertex = new MenuItem("Nouveau sommet");
			vCM_add_relation = new MenuItem("Nouvelle relation");
			vCM_add_edge = new Menu("Nouvel arc");
		
		edgeContextMenu = new ContextMenu();
			eCM_edit = new MenuItem("Editer");
			eCM_delete = new MenuItem("Supprimer");
			eCM_separator = new SeparatorMenuItem();
			eCM_add_vertex = new MenuItem("Nouveau sommet");
			eCM_add_relation = new MenuItem("Nouvelle relation");
			eCM_add_edge = new Menu("Nouvel arc");
			
		treeContextMenu = new ContextMenu();
			tCM_add_vertex = new MenuItem("Nouveau sommet");
			tCM_add_relation = new MenuItem("Nouvelle relation");
			tCM_add_edge = new Menu("Nouvel arc");
		
		for(Relation relation : tree.getRelations()) {
			item_add_edge_relations.add(new MenuItem(relation.getName()));
			CheckMenuItem menuItem = new CheckMenuItem(relation.getName());
			menuItem.setSelected(true);
			item_view_relations.add(menuItem);
			vCM_add_edge_relations.add(new MenuItem(relation.getName()));
			eCM_add_edge_relations.add(new MenuItem(relation.getName()));
			tCM_add_edge_relations.add(new MenuItem(relation.getName()));
		}
		
		if(tree.nbRelations() == 0) {
			menu_add_edge.setDisable(true);
			menu_view_relations.setDisable(true);
			vCM_add_edge.setDisable(true);
			eCM_add_edge.setDisable(true);
			tCM_add_edge.setDisable(true);
		}
		
		if(tree.getFile() == null) {
			item_ontologie.setDisable(true);
			item_save.setDisable(true);
		}
		
		// CENTRE
		center = new Pane();
		// EST
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
					return new VertexViewList();
				}
			});
		// BAS
		bottom = new HBox();
			pb = new ProgressBar();
				pb.setProgress(0);
			info_progress = new Label();
	}
	
	private void buildInterface() {
		// Barre de Menu
			menu_file.getItems().addAll(item_new, item_open, item_save, item_save_under, item_quit);
				menu_add_edge.getItems().addAll(item_add_edge_relations);
			menu_edit.getItems().addAll(item_ontologie, item_separator, item_add_vertex, item_add_relation, menu_add_edge);
				menu_view_relations.getItems().addAll(item_view_relations);
			menu_tools.getItems().add(item_tools_data);
			menu_view.getItems().addAll(menu_view_relations);
			menu_settings.getItems().add(item_auto_save);
		menuBar.getMenus().addAll(menu_file, menu_edit, menu_tools, menu_view, menu_settings);
		menuBar.setUseSystemMenuBar(true);
		
		// Menus Contextuels
			vCM_add_edge.getItems().addAll(vCM_add_edge_relations);
		vertexContextMenu.getItems().addAll(vCM_edit, vCM_delete, vCM_separator, vCM_add_vertex, vCM_add_relation, vCM_add_edge);
		
			eCM_add_edge.getItems().addAll(eCM_add_edge_relations);
		edgeContextMenu.getItems().addAll(eCM_edit, eCM_delete, eCM_separator, eCM_add_vertex, eCM_add_relation, eCM_add_edge);
		
			tCM_add_edge.getItems().addAll(tCM_add_edge_relations);
		treeContextMenu.getItems().addAll(tCM_add_vertex, tCM_add_relation, tCM_add_edge);
		
		// TODO gestion dans le cas d'une ouverture directe de fichier
		// drawVertices();
		// drawEdges();
		
		east.getChildren().addAll(info_label, info_area, info_list);
		bottom.getChildren().addAll(pb, info_progress);
		
		setTop(menuBar);
		setCenter(center);
		setRight(east);
		setBottom(bottom);
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
				edgeView = new EdgeView(relationName, start, end, color);
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
	
	private void buildEvents() {
		item_new.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				tree = new Tree();
				build();
			}
		});
		item_open.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				File F = fileChooser.showOpenDialog(null);
				if(F != null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Erreur");
					alert.setHeaderText("Erreur dans la lecture du fichier");
					alert.initStyle(StageStyle.UTILITY);
					try {
						TreeLoader loader = new TreeLoader(tree, F);
						loader.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
							@Override
							public void handle(WorkerStateEvent event) {
								tree = loader.getTree();
								build();
								// TODO Ne pas reconstruire toute la fenêtre.
							}
						});
						info_progress.textProperty().bind(loader.messageProperty());
						pb.progressProperty().bind(loader.progressProperty());
						loader.run();
						Thread thread = new Thread(loader);
						thread.start();
					} catch (Exception e) {
						e.printStackTrace();
						alert.setContentText(e.getMessage());
						alert.showAndWait();
					}
				}
			}
		});
		item_save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("Enregistrement impossible.");
				alert.initStyle(StageStyle.UTILITY);
				try {
					TreeSaver saver = new TreeSaver(tree);
					info_progress.textProperty().bind(saver.messageProperty());
					pb.progressProperty().bind(saver.progressProperty());
					new Thread(saver).start();
				} catch (Exception e) {
					e.printStackTrace();
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				}
			}
		});
		item_save_under.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("Enregistrement impossible.");
				alert.initStyle(StageStyle.UTILITY);
				File file = fileChooser.showSaveDialog(null);
				if(file != null) {
					tree.setFile(file);
					try {
						TreeSaver saver = new TreeSaver(tree);
						info_progress.textProperty().bind(saver.messageProperty());
						pb.progressProperty().bind(saver.progressProperty());
						new Thread(saver).start();
					} catch (Exception e) {
						e.printStackTrace();
						alert.setContentText(e.getMessage());
						alert.showAndWait();
					}
					item_save.setDisable(false);
				}
			}
		});
		item_quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});
		item_ontologie.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!Desktop.isDesktopSupported()) {
					System.err.println("Ouverture non supportée ! ");
					return;
				}
				try {
					File file = tree.getFile();
					if(file != null && file.exists()) {
						Desktop desktop = Desktop.getDesktop();
						desktop.open(file);
					} else System.err.println("File don't exist");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		item_add_vertex.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AddVertexStage addVertex = new AddVertexStage();
				addVertex.showAndWait();
				Vertex vertex = addVertex.getVertex();
				if(vertex != null ) {
					try {
						addVertex(vertex);
					} catch (TreeException e) {
						e.printStackTrace();
					}
				}
			}
		});
		item_add_relation.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AddRelationStage addRelation = new AddRelationStage();
				addRelation.showAndWait();
				Relation relation = addRelation.getRelation();
				if(relation != null) {
					try {
						addRelation(relation);
					} catch (TreeException e) {
						e.printStackTrace();
					}
				}
			}
		});
		for(MenuItem item : item_add_edge_relations) {
			String relationName= item.getText();
			item.setOnAction(new AddEdgeClicked(relationName));
		}
		item_tools_data.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DataStage stage = new DataStage(tree);
				stage.show();
			}
		});
		for(CheckMenuItem item : item_view_relations) {
			String text = item.getText();
			item.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					List<EdgeView> edges = edgesView.get(text);
					if(newValue)
						center.getChildren().addAll(edges);
					else
						center.getChildren().removeAll(edges);
				}
			});
		}
		for(MenuItem item : vCM_add_edge_relations) {
			String text = item.getText();
			item.setOnAction(new AddEdgeClicked(text));
		}
		for(MenuItem item : eCM_add_edge_relations) {
			String text = item.getText();
			item.setOnAction(new AddEdgeClicked(text));
		}
		for(MenuItem item : tCM_add_edge_relations) {
			String text = item.getText();
			item.setOnAction(new AddEdgeClicked(text));
		}
		vCM_edit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				EditVertexStage editVertexStage = new EditVertexStage(vertexViewSelected.getVertex());
				editVertexStage.showAndWait();
				info_area.setText(editVertexStage.getVertex().toString());
			}
		});
		vCM_delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					removeVertex(vertexViewSelected);
				} catch (TreeException e) {
					e.printStackTrace();
				}
			}
		});
		vCM_add_vertex.setOnAction(item_add_vertex.getOnAction());
		vCM_add_relation.setOnAction(item_add_relation.getOnAction());
		eCM_edit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		eCM_delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					removePair(relationToEdit, pairToEdit);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		eCM_add_vertex.setOnAction(item_add_vertex.getOnAction());
		eCM_add_relation.setOnAction(item_add_relation.getOnAction());
		tCM_add_vertex.setOnAction(item_add_vertex.getOnAction());
		tCM_add_relation.setOnAction(item_add_relation.getOnAction());
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
				if(event.getButton() == MouseButton.SECONDARY)
					return;
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
		center.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				treeContextMenu.hide();
				if(!event.isConsumed() && event.getButton() == MouseButton.SECONDARY)
					treeContextMenu.show(center, event.getScreenX(), event.getScreenY());
			}
		});
		for(VertexView vertex : verticesView) {
			vertex.setOnMouseClicked(new VertexClicked(vertex));
			vertex.setOnMouseDragged(new VertexDragged(vertex));
		}
		for(Entry<String, List<EdgeView>> edges : edgesView.entrySet()) {
			for(EdgeView edge : edges.getValue()) {
				edge.setOnMouseClicked(new EdgeClicked(edge));
			}
		}
		info_list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<VertexView>() {
			@Override
			public void changed(ObservableValue<? extends VertexView> observable,
					VertexView oldValue, VertexView newValue) {
						Vertex vertex = newValue.getVertex();
						info_area.setText(vertex.info());
						newValue.setSelected(true);
						if(oldValue != null) oldValue.setSelected(false);
			}
		});
	}
	
	private VertexView getViewFromVertex(Vertex vertex) {
		for(VertexView vertexView : verticesView) {
			if(vertexView.getVertex().equals(vertex))
				return vertexView;
		}
		return null;
	}
	
	private EdgeView getViewFromPair(String relationName, Pair<Vertex, Vertex> pair) {
		List<EdgeView> list = edgesView.get(relationName);
		for(EdgeView edgeView : list) {
			System.out.println(edgeView.getPair());
			if(edgeView.getPair().equals(pair));
				return edgeView;
		}
		return null;
	}
	
	/**
	 * Ajout d'un sommet
	 * @param vertex : sommet à ajouter
	 * @throws TreeException si le sommet existe déjà
	 */
	private void addVertex(Vertex vertex) throws TreeException {
		tree.createVertex(vertex);
		
		// Création de son composant graphique et events
		VertexView vertexView = new VertexView(vertex, placement.next());
		verticesView.add(vertexView);
		vertexView.setOnMouseClicked(new VertexClicked(vertexView));
		vertexView.setOnMouseDragged(new VertexDragged(vertexView));

		center.getChildren().setAll(verticesView);
		
		// Edition de la zone d'info
		info_area.setText(tree.toString());
		
		// Edition de la liste
		verticesViewForList = FXCollections.observableArrayList(verticesView);
		info_list.setItems(verticesViewForList);
	}
	
	/**
	 * Suppression d'un sommet (graphique)
	 * @param vertex : sommet à supprimer
	 * @throws TreeException si le sommet n'existe pas
	 */
	private void removeVertex(VertexView vertex) throws TreeException {
		tree.removeVertex(vertexViewSelected.getVertex());	// Lors de sa suppression, tous les arcs qui le liaient avec d'autres sommets sont supprimés 
		// Suppression de son composant graphique
		verticesView.remove(vertexViewSelected);
		
		center.getChildren().setAll(verticesView);
		
		// Edition de la zone d'info
		info_area.setText(tree.toString());
		
		// Edition de la liste
		verticesViewForList.remove(vertex);
		info_list.setItems(verticesViewForList);
		
		// TODO Redessiner les arcs (Peut-être long ?)
	}
	
	/**
	 * Ajout d'une relation
	 * @param relation : relation à ajouter
	 * @throws TreeException si la relation existe déjà
	 */
	private void addRelation(Relation relation) throws TreeException {
		tree.createRelation(relation);
		
		// Création de ses composants graphiques et events
		MenuItem menuItem = new MenuItem(relation.getName());
		menuItem.setOnAction(new AddEdgeClicked(relation.getName()));
		CheckMenuItem checkMenuItem = new CheckMenuItem(relation.getName());
		checkMenuItem.setSelected(true);
		checkMenuItem.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				List<EdgeView> edges = edgesView.get(relation.getName());
				if(newValue)
					center.getChildren().addAll(edges);
				else
					center.getChildren().removeAll(edges);
			}
		});
		
		item_add_edge_relations.add(menuItem);
		vCM_add_edge_relations.add(menuItem);
		eCM_add_edge_relations.add(menuItem);
			eCM_add_edge.setDisable(false);
		tCM_add_edge_relations.add(menuItem);
			tCM_add_edge.setDisable(false);
		
		item_view_relations.add(checkMenuItem);
		
		// Menu "Edition"
			menu_add_edge.getItems().setAll(item_add_edge_relations);
			menu_add_edge.setDisable(false);
			int index = menu_edit.getItems().indexOf(menu_add_edge);
		menu_edit.getItems().set(index, menu_add_edge);
		// Menu Contextuel Sommet
			vCM_add_edge.getItems().setAll(vCM_add_edge_relations);
			vCM_add_edge.setDisable(false);
			index = vertexContextMenu.getItems().indexOf(vCM_add_edge);
		vertexContextMenu.getItems().set(index, vCM_add_edge);
		// Menu Contextuel Arc
			eCM_add_edge.getItems().setAll(eCM_add_edge_relations);
			eCM_add_edge.setDisable(false);
			index = edgeContextMenu.getItems().indexOf(eCM_add_edge);
		edgeContextMenu.getItems().set(index, eCM_add_edge);
		// Menu Contextuel Arbre
			tCM_add_edge.getItems().setAll(tCM_add_edge_relations);
			tCM_add_edge.setDisable(false);
			index = treeContextMenu.getItems().indexOf(tCM_add_edge);
		treeContextMenu.getItems().set(index, tCM_add_edge);
		// Menu "Affichage"
			menu_view_relations.getItems().setAll(item_view_relations);
			menu_view_relations.setDisable(false);
			index = menu_view.getItems().indexOf(menu_view_relations);
		menu_view.getItems().set(index, menu_view_relations);
		
		// Edition de la zone d'info
		info_area.setText(tree.toString());
		
		edgesView.put(relation.getName(), new ArrayList<>());
	}
	
	private void removeRelation(String name) {
		// TODO
	}
	
	/**
	 * Ajout d'une paire dans une relation
	 * @param relationName : nom de la relation
	 * @param pair : paire à ajouter
	 * @throws RelationException si la paire, dans cet ordre, existe déjà
	 * @throws TreeException  si la relation n'existe pas
	 */
	private void addPair(String relationName, Pair<Vertex, Vertex> pair) throws RelationException, TreeException {
		tree.addPair(relationName, pair);
		
		// Création de ses composantes graphiques et events
		List<EdgeView> list = edgesView.get(relationName);
		
		Color color = colorDistribution.next();
		VertexView start = getViewFromVertex(pair.getFirst());
		VertexView end = getViewFromVertex(pair.getSecond());
		EdgeView edgeView = new EdgeView(relationName, start, end, color);
		edgeView.setOnMouseClicked(new EdgeClicked(edgeView));
		list.add(edgeView);
		edgesView.replace(relationName, list);
		
		// Edition de la zone d'info
		info_area.setText(tree.toString());
		
		center.getChildren().add(edgeView);
	}
	
	/**
	 * Suppression d'une paire d'une relation
	 * @param relationName : nom de la relation
	 * @param pair : paire à supprimer
	 * @throws TreeException : si la relation n'existe pas
	 * @throws RelationException si la paire n'existe pas
	 */
	private void removePair(String relationName, Pair<Vertex, Vertex> pair) throws TreeException, RelationException {
		tree.removePair(relationName, pair);
		
		// Suppression des composantes graphiques
		List<EdgeView> list = edgesView.get(relationName);
		EdgeView edgeView = getViewFromPair(relationName, pair);
		System.out.println(pair + " == " + edgeView.getPair());
		list.remove(edgeView);
		
		center.getChildren().remove(edgeView);
	}
	
	/**
	 * {@link EventHandler} lors d'un clic sur un {@link VertexView}
	 * @author jfourmond
	 */
	protected class VertexClicked implements EventHandler<MouseEvent> {
		private VertexView vertexView;
		
		public VertexClicked(VertexView vertexView) { this.vertexView = vertexView; }
		
		@Override
		public void handle(MouseEvent event) {
			event.consume();
			if(event.getButton() == MouseButton.PRIMARY) {
				Vertex v = vertexView.getVertex();
				info_area.setText(v.info());
				vertexView.setSelected(true);
				for(VertexView other: verticesView) {
					if(other != vertexView) other.setSelected(false);
				}
				info_list.getSelectionModel().select(vertexView);
			} else if(event.getButton() == MouseButton.SECONDARY) {
				vertexViewSelected = vertexView;
				vertexContextMenu.show(vertexView, event.getScreenX(), event.getScreenY());
			}
		}
	}
	
	/**
	 * {@link EventHandler} lors d'un clic sur un {@link EdgeView}
	 * @author jfourmond
	 */
	protected class EdgeClicked implements EventHandler<MouseEvent> {
		private EdgeView edgeView;
		
		public EdgeClicked(EdgeView edgeView) { this.edgeView = edgeView; }
		
		@Override
		public void handle(MouseEvent event) {
			event.consume();
			if(event.getButton() == MouseButton.PRIMARY) {
				System.err.println("PAS IMPLEMENTE");
			} else if(event.getButton() == MouseButton.SECONDARY) {
				relationToEdit = edgeView.getRelation();
				pairToEdit = new Pair<Vertex, Vertex>(edgeView.getVertexStart(), edgeView.getVertexEnd());
				edgeContextMenu.show(edgeView, event.getScreenX(), event.getScreenY());
			}
		}	
	}
	
	/**
	 * {@link EventHandler} lors d'un drag sur un {@link VertexView}
	 * @author jfourmond
	 */
	protected class VertexDragged implements EventHandler<MouseEvent> {
		private VertexView vertexView;
		
		public VertexDragged(VertexView vertexView) { this.vertexView = vertexView; }
		
		@Override
		public void handle(MouseEvent event) {
			event.consume();
			if(event.getButton() == MouseButton.PRIMARY) {
				vertexView.setCenterX(event.getX());
				vertexView.setCenterY(event.getY());
				redrawLines();
			}
		}
	}
	
	/**
	 * {@link EventHandler} lors d'un clic sur un {@link MenuItem} d'ajout d'arc
	 * @author etudiant
	 */
	protected class AddEdgeClicked implements EventHandler<ActionEvent> {
		private String relationName;
		
		public AddEdgeClicked(String relationName) { this.relationName = relationName; }
		
		@Override
		public void handle(ActionEvent event) {
			if(!tree.isVerticesEmpty()) {
				AddEdgeToRelationStage addEdge = new AddEdgeToRelationStage(tree.getVertices(), relationName);
				addEdge.showAndWait();
				String name = addEdge.getRelationName();
				Pair<Vertex, Vertex> pair = addEdge.getPair();
				try {
					addPair(name, pair);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else System.err.println("Pas de sommets");
		}
	}
}
