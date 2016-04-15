package fr.fourmond.jerome.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import fr.fourmond.jerome.framework.Tree;

public class Window extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static final String title = "Graphe & Ontologies";
	
	private static Tree<?, ?> tree;
	
	// Menu
	private JMenuBar menu_bar;
		private JMenu file;
			private JMenuItem item_open;
			private JMenuItem item_close;
		private JMenu edition;
			private JMenuItem item_edit_vertices;
			private JMenuItem item_edit_edges;
		private JMenu show;
			private JMenuItem item_show_vertices;
			private JMenuItem item_show_edges;
	private TreeView treeView;
			
	private JFileChooser fileChooser;
	
	public Window() {
		super(title);
		
		tree = new Tree<>();
		
		buildComposants();
		buildInterface();
		buildEvents();
		
		pack();
		setMinimumSize(new Dimension(400, 300));
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setVisible(true);
	}
	
	public Window(TreeView<?, ?> treeView) {
		super(title);
		
		this.treeView = treeView;
		
		buildComposants();
		buildInterface();
		buildEvents();
		
		pack();
		setMinimumSize(new Dimension(400, 300));
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setVisible(true);
	}
	
	private void buildComposants() {
		// treeView = new TreeView(tree);
		fileChooser = new JFileChooser(new File("~"));
		
		menu_bar = new JMenuBar();
			file = new JMenu("Fichier");
				item_open = new JMenuItem("Ouvrir");
				item_close = new JMenuItem("Quitter");
			edition = new JMenu("Edition");
				item_edit_vertices = new JMenuItem("Sommets");
				item_edit_edges = new JMenuItem("Arcs");
			show = new JMenu("Affichage");
				item_show_vertices = new JMenuItem("Sommets");
				item_show_edges = new JMenuItem("Arcs");
		
	}
	
	private void buildInterface() {
			file.add(item_open);
			file.add(item_close);
		menu_bar.add(file);
			edition.add(item_edit_vertices);
			edition.add(item_edit_edges);
		menu_bar.add(edition);
			show.add(item_show_vertices);
			show.add(item_show_edges);
		menu_bar.add(show);

		setJMenuBar(menu_bar);
		setContentPane(treeView);
	}
	
	private void buildEvents() {
		item_open.addActionListener(this);
		item_close.addActionListener(this);
		
		item_edit_vertices.addActionListener(this);
		item_edit_edges.addActionListener(this);
		
		item_show_edges.addActionListener(this);
		item_show_edges.addActionListener(this);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object O = e.getSource();
		if(O.getClass() == JMenuItem.class) {
			JMenuItem MI = (JMenuItem) O;
			if(MI == item_close)
				System.exit(EXIT_ON_CLOSE);
			else if(MI == item_open) {
				File file;
				if (fileChooser.showOpenDialog(null)== 
					JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					// TODO implement
					System.err.println("NON IMPLEMENTÉ");
					System.err.println("Fichier : " + file.getAbsolutePath());
				}
			} else if(MI == item_edit_vertices) {
				// TODO implement
				System.err.println("NON IMPLEMENTÉ");
			} else if(MI == item_edit_edges) {
				// TODO implement
				System.err.println("NON IMPLEMENTÉ");
			} else if(MI == item_show_vertices) {
				// TODO implement
				System.err.println("NON IMPLEMENTÉ");
			} else if(MI == item_show_edges) {
				// TODO implement
				System.err.println("NON IMPLEMENTÉ");
			}
		}
	}

}
