package fr.fourmond.jerome.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import fr.fourmond.jerome.framework.Tree;

public class MainView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static final String title = "Graphe & Ontologies";
	
	private static TreeView treeView;
		private static Tree<?, ?> tree;
	
	// Menu
	private JMenuBar menu_bar;
		private JMenu file;
			private JMenuItem item_open;
			private JMenuItem item_close;
	private JPanel main_panel;
			
	public MainView() {
		super(title);
		
		tree = new Tree<>();
		
		buildComposants();
		buildInterface();
		buildEvents();
		
		setSize(400, 300);
		setVisible(true);
	}
	
	public MainView(TreeView<?, ?> treeView) {
		super(title);
		
		this.treeView = treeView;
		
		buildComposants();
		buildInterface();
		buildEvents();
		
		setSize(400, 300);
		setVisible(true);
	}
	
	private void buildComposants() {
		// treeView = new TreeView(tree);
		
		menu_bar = new JMenuBar();
		file = new JMenu("Fichier");
			item_open = new JMenuItem("Ouvrir");
			item_close = new JMenuItem("Quitter");
	
			main_panel = new JPanel(new BorderLayout());
	}
	
	private void buildInterface() {
		file.add(item_open);
		file.add(item_close);
		menu_bar.add(file);
	
		main_panel.add(treeView, BorderLayout.CENTER);
		
		setJMenuBar(menu_bar);
		setContentPane(main_panel);
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
