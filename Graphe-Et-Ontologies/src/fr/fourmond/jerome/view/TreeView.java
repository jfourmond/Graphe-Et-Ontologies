package fr.fourmond.jerome.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import fr.fourmond.jerome.framework.Edge;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.Vertex;

public class TreeView<T_Vertex extends Vertex, T_Edge> extends JPanel implements ActionListener, MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;

	private Tree<T_Vertex, T_Edge> tree;

	private List<VertexView> vertices;
	private List<EdgeView<T_Vertex, T_Edge>> edges;
	
	private JSplitPane main_panel;
		private static JPanel center_panel;
		private static JPanel east_panel;
			private static JLabel info_label;
			private static JTextArea info_area;
			private static JPanel info_button;
				private static JButton info_edit;
				private static JButton info_delete;
	
	// Attribut pour le déplacement
	private static VertexView vertexPressedOn;
	private static VertexView vertexClickedOn;
	private static boolean pressed = false;
	private static MouseEvent decalage;
	private static Point actual;
	private static Point end;
	
	public TreeView(Tree<T_Vertex, T_Edge> tree) {
		this.tree = tree;
		
		buildComposants();
		buildInterface();
		buildEvents();
	}

	/**
	 * Récupération du {@link VertexView} correspondant au {@link Vertex} T
	 * @param T : le sommet à rechercher
	 * @return le {@link VertexView} correspondant
	 */
	public VertexView getVertexViewFrom(Vertex T) {
		for(VertexView vertex : vertices) {
			if(vertex.getVertex() == T)
				return vertex;
		}
		return null;
	}
	
	private void buildComposants() {
		Random rand = new Random();
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
			center_panel = new JPanel();
			center_panel.setLayout(null);
			
			east_panel = new JPanel();
			east_panel.setLayout(new BoxLayout(east_panel, BoxLayout.Y_AXIS));
				info_label = new JLabel("Informations");
				info_area = new JTextArea();
					info_area.setEditable(false);
					info_area.setText(tree.toString());
				info_button = new JPanel();
					info_edit = new JButton("Editer");
					info_edit.setVisible(false);
					info_delete = new JButton("Supprimer");
					info_delete.setVisible(false);
			east_panel.setBorder(BorderFactory.createLineBorder(Color.black));
			
		// setLayout(null);
		//	Build VertexView
		vertices = new ArrayList<VertexView>();
		for(T_Vertex vertex : tree.getVertices()) {
			VertexView vertexView = new VertexView(vertex, rand.nextInt(500), rand.nextInt(500));
			vertices.add(vertexView);
		}
		// Build EdgeView
		edges = new ArrayList<>();
		for(Edge<T_Vertex, T_Edge> edge : tree.getEdges()) {
			EdgeView<T_Vertex, T_Edge> edgeView = new EdgeView<>(edge, getVertexViewFrom(edge.getFirstVertex()), getVertexViewFrom(edge.getSecondVertex()));
			edges.add(edgeView);
		}
	}
	
	private void buildInterface() {
		// Print VertexView
		for(VertexView vertex : vertices) {
			center_panel.add(vertex);
		}
		// Print EdgeView
		for(EdgeView<T_Vertex, T_Edge> edge : edges) {
			center_panel.add(edge);
		}
		
		drawVertices();
		drawEdges();
		
		info_button.add(info_edit);
		info_button.add(info_delete);
		
		east_panel.add(info_label);
		east_panel.add(info_area);
		east_panel.add(info_button);
		
		main_panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, center_panel, east_panel);
		main_panel.setDividerSize(3);
		main_panel.setDividerLocation((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 300);
		
		add(main_panel);
	}
	
	private void drawVertices() {
		Insets insets = center_panel.getInsets();
		Dimension size;
		for(VertexView vertex : vertices) {
			size = vertex.getPreferredSize();
			vertex.setBounds(vertex.getX() + insets.left, vertex.getY() + insets.top, size.width, size.height);
		}
	}
	
	private void drawEdges() {
		Dimension size;
		for(EdgeView<T_Vertex, T_Edge> edge : edges) {
			size = edge.getPreferredSize();
			if(edge.getGapY() < 0) {
				if(edge.getGapX() < 0) {
					edge.setBounds(edge.getEnd().getX() + VertexView.getRadius()/2, edge.getEnd().getY() + VertexView.getRadius()/2, size.width, size.height);
				} else {
					edge.setBounds(edge.getStart().getX() + VertexView.getRadius()/2, edge.getEnd().getY() + VertexView.getRadius()/2, size.width, size.height);
				}
			} else {
				if(edge.getGapX() < 0) {
					edge.setBounds(edge.getEnd().getX() + VertexView.getRadius()/2, edge.getStart().getY() + VertexView.getRadius()/2, size.width, size.height);
				} else {
					edge.setBounds(edge.getStart().getX() + VertexView.getRadius()/2, edge.getStart().getY() + VertexView.getRadius()/2, size.width, size.height);
				}
			}
		}
	}
	
	private void buildEvents() {
		for(VertexView vertex : vertices) {
			vertex.addMouseListener(this);
			vertex.addMouseMotionListener(this);
		}
		
		for(EdgeView<T_Vertex, T_Edge> edge : edges) {
			edge.addMouseListener(this);
		}
		
		info_edit.addActionListener(this);
		info_delete.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object O = e.getSource();
		if(O.getClass() == JButton.class) {
			JButton B = (JButton) O;
			if(B == info_edit) {
				// TODO implement
				System.err.println("NON IMPLEMENTÉ");
			} else if(B == info_delete) {
				// TODO implement
				System.err.println("NON IMPLEMENTÉ");
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(pressed) {
			Insets insets = center_panel.getInsets();
			Dimension size = vertexPressedOn.getPreferredSize();
			
			double posX = e.getX() + vertexPressedOn.getX() - decalage.getX();
			double posY = e.getY() + vertexPressedOn.getY() - decalage.getY();
			
			actual = new Point((int)posX,(int)posY);
			vertexPressedOn.setBounds(actual.x + insets.left, actual.y + insets.top, size.width, size.height);
			
			drawEdges();
			
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) { }

	@Override
	public void mouseClicked(MouseEvent e) {
		Object O = e.getSource();
		if(O.getClass() == VertexView.class) {
			vertexClickedOn = (VertexView) O;
			Vertex vertex = vertexClickedOn.getVertex();
			info_area.setText(vertex.fullData());
			info_delete.setVisible(true);
			info_edit.setVisible(true);
		} else if(O.getClass() == EdgeView.class) {
			// TODO On click -> Show info EdgeView
			System.err.println("NON IMPLEMENTÉ");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Object O = e.getSource();
		if(O.getClass() == VertexView.class) {
			VertexView vertexView = (VertexView) O;
			if(!pressed) {
				decalage = e;
				vertexPressedOn = vertexView;
				pressed = true;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(pressed) {
			Insets insets = getInsets();
			Dimension size = vertexPressedOn.getPreferredSize();
			double posX = e.getX() + vertexPressedOn.getX();
			double posY = e.getY() + vertexPressedOn.getY();
			
			end = new Point((int)(posX-decalage.getX()), (int)(posY-decalage.getY()));
			vertexPressedOn.setBounds(end.x + insets.left, end.y + insets.top, size.width, size.height);
			
			pressed = false;
			vertexPressedOn = null;
		}
		
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }
}
