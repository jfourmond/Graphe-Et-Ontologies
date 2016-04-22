package fr.fourmond.jerome.view.fx.ontology;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import fr.fourmond.jerome.ontology.TreeOntology;
import fr.fourmond.jerome.ontology.TreeOntologyException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class OntologyStage extends Stage {
	private TreeOntology ontology;
	
	private OntologyFxView ontologyView;
	
	private File file;
	
	private MenuBar menuBar;
	private Menu menu_file;
		private MenuItem item_open;
		private MenuItem item_quit;
	private Menu menu_edit;
		private MenuItem item_ontologie;
	private Menu menu_view;
	
	private FileChooser fileChooser;
	
	public OntologyStage(TreeOntology ontology) {
		this.setTitle("Graphe Et Ontologies");
		this.ontology = ontology;
		fileChooser = new FileChooser();
		
		menuBar = new MenuBar();
			menu_file = new Menu("Fichier");
				item_open = new MenuItem("Ouvrir");
				item_open.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						File F;
						TreeOntology newOntology;
						F = fileChooser.showOpenDialog(null);
						if(F != null) {
							file = F;
							System.out.println(file);
							try {
								newOntology = new TreeOntology();
								newOntology.readFromFile(F.getAbsolutePath());
								System.out.println(newOntology);
								new OntologyStage(newOntology);
							} catch (TreeOntologyException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
				item_quit = new MenuItem("Quitter");
				item_quit.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Platform.exit();
					}
				});
			menu_file.getItems().addAll(item_open, item_quit);
			menu_edit = new Menu("Edition");
				item_ontologie = new MenuItem("Ontologie");
				item_ontologie.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						try {
							if(file != null && file.exists()) {
								Desktop desktop = Desktop.getDesktop();
								desktop.open(file);
							} else System.err.println("File don't exist");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			menu_edit.getItems().add(item_ontologie);
			menu_view = new Menu("Affichage");
		menuBar.getMenus().addAll(menu_file, menu_edit, menu_view);
		
		ontologyView = new OntologyFxView(ontology);
		
		ontologyView.setTop(menuBar);
		
		this.setScene(new Scene(ontologyView));
		this.show();
	}
}
