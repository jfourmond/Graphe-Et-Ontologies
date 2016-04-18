package fr.fourmond.jerome.view.swing;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import fr.fourmond.jerome.framework.Tree;

public class Window extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static final String title = "Graphe & Ontologies";
	
	private File fileTree;
	private static Tree<?, ?> tree;
	
	// Menu
	private JMenuBar menu_bar;
		private JMenu file;
			private JMenuItem item_open;
			private JMenuItem item_close;
		private JMenu edition;
			private JMenuItem item_edit;
	private TreeView<?, ?> treeView;
			
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
	
	public Window(String fileTree) {
		super(title);
		
		this.fileTree = new File(fileTree);
		
		// TODO Crée l'arbre en fonction du contenu du fichier
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
				item_edit = new JMenuItem("Ontologie");
	}
	
	private void buildInterface() {
			file.add(item_open);
			file.add(item_close);
		menu_bar.add(file);
			edition.add(item_edit);
		menu_bar.add(edition);

		setJMenuBar(menu_bar);
		setContentPane(treeView);
	}
	
	private void buildEvents() {
		item_open.addActionListener(this);
		item_close.addActionListener(this);
		
		item_edit.addActionListener(this);
		
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
			} else if(MI == item_edit) {
				try {
					if(fileTree != null && fileTree.exists()) {
						Desktop desktop = Desktop.getDesktop();
						desktop.open(fileTree);
					} else System.err.println("File don't exist");
				} catch (IOException E) {
					E.printStackTrace();
				}
			}
		}
	}
}