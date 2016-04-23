package fr.fourmond.jerome.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import fr.fourmond.jerome.framework.RelationException;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.TreeException;
import fr.fourmond.jerome.framework.VertexException;
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
	private Tree ontology;
	
	private TreeView treeView;
	
	private File file;
	
	private MenuBar menuBar;
	private Menu menu_file;
		private MenuItem item_open;
		private MenuItem item_quit;
	private Menu menu_edit;
		private MenuItem item_ontologie;
	private Menu menu_view;
	
	private FileChooser fileChooser;
	
	public OntologyStage(Tree ontology) {
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
						Tree newOntology;
						F = fileChooser.showOpenDialog(null);
						if(F != null) {
							file = F;
							System.out.println(file);
							try {
								newOntology = new Tree();
								newOntology.readFromFile(F.getAbsolutePath());
								System.out.println(newOntology);
								new OntologyStage(newOntology);
							} catch (TreeException e) {
								e.printStackTrace();
							} catch (ParserConfigurationException e) {
								e.printStackTrace();
							} catch (SAXException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							} catch (RelationException e) {
								e.printStackTrace();
							} catch (VertexException e) {
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
		
		treeView = new TreeView(this.ontology);
		
		treeView.setTop(menuBar);
		
		this.setScene(new Scene(treeView));
		this.show();
	}
}
