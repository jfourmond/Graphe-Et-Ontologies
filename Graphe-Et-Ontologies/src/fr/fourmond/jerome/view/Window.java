package fr.fourmond.jerome.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private TreeView treeView;
			
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
		
		menu_bar = new JMenuBar();
		file = new JMenu("Fichier");
			item_open = new JMenuItem("Ouvrir");
			item_close = new JMenuItem("Quitter");
	}
	
	private void buildInterface() {
		file.add(item_open);
		file.add(item_close);
		menu_bar.add(file);

		setJMenuBar(menu_bar);
		setContentPane(treeView);
	}
	
	private void buildEvents() {
		item_open.addActionListener(this);
		item_close.addActionListener(this);
		
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
				//TODO implement
			}
		}
	}

}
