package fr.fourmond.jerome.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import fr.fourmond.jerome.framework.Edge;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.Vertex;

public class TreeView<T_Vertex extends Vertex, T_Edge> extends JPanel implements ActionListener, MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;

	private Tree<T_Vertex, T_Edge> tree;

	private List<VertexView> vertices;
	private List<EdgeView<T_Vertex, T_Edge>> edges;
	
	private static JPanel center_panel;
	private static JPanel east_panel;
		private static JTextArea info_area;
	
	// Attribut pour le déplacement
	private static VertexView vertexPressedOn;
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
				info_area = new JTextArea();
				info_area.setEditable(false);
				info_area.setText(tree.toString());
			east_panel.setBorder(BorderFactory.createLineBorder(Color.black));
			
		// setLayout(null);
		//	Build VertexView
		vertices = new ArrayList<VertexView>();
		for(T_Vertex vertex : tree.getVertices()) {
			VertexView vertexView = new VertexView(vertex, rand.nextInt(100), rand.nextInt(100));
			vertexView.addMouseListener(this);
			vertexView.addMouseMotionListener(this);
			vertices.add(vertexView);
		}
		// Build EdgeView
		edges = new ArrayList<>();
		for(Edge<T_Vertex, T_Edge> edge : tree.getEdges()) {
			EdgeView<T_Vertex, T_Edge> edgeView = new EdgeView<>(edge, getVertexViewFrom(edge.getFirstVertex()), getVertexViewFrom(edge.getSecondVertex()));
			System.out.println(getVertexViewFrom(edge.getFirstVertex()));
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
		
		Insets insets = center_panel.getInsets();
		Dimension size;
		for(VertexView vertex : vertices) {
			size = vertex.getPreferredSize();
			vertex.setBounds(vertex.getX() + insets.left, vertex.getY() + insets.top, size.width, size.height);
		} 
		for(EdgeView<T_Vertex, T_Edge> edge : edges) {
			size = edge.getPreferredSize();
			edge.setBounds(edge.getStart().getX(), edge.getStart().getY(), size.width, size.height);
		}
		
		east_panel.add(info_area);
		//Size and display the window.
		// insets = getInsets();
		// setSize(300 + insets.left + insets.right, 125 + insets.top + insets.bottom);
		
		add(center_panel, BorderLayout.CENTER);
		add(east_panel, BorderLayout.EAST);
	}
	
	private void buildEvents() {
		for(VertexView vertex : vertices) {
			vertex.addMouseListener(this);
			vertex.addMouseMotionListener(this);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { }

	@Override
	public void mouseDragged(MouseEvent e) {
		if(pressed) {
			Insets insets = getInsets();
			Dimension size = vertexPressedOn.getPreferredSize();
			
			double posX = e.getX() + vertexPressedOn.getX() - decalage.getX();
			double posY = e.getY() + vertexPressedOn.getY() - decalage.getY();
			
			actual = new Point((int)posX,(int)posY);
			vertexPressedOn.setBounds(actual.x + insets.left, actual.y + insets.top, size.width, size.height);
			
			for(EdgeView<T_Vertex, T_Edge> edge : edges) {
				size = edge.getPreferredSize();
				edge.setBounds(edge.getStart().getX(), edge.getStart().getY(), size.width, size.height);
			}
			
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) { }

	@Override
	public void mouseClicked(MouseEvent e) {
		VertexView vertexView = (VertexView) e.getSource();
		Vertex vertex = vertexView.getVertex();
		info_area.setText(vertex.fullData());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		VertexView vertexView = (VertexView) e.getSource();
		if(!pressed) {
			decalage = e;
			vertexPressedOn = vertexView;
			pressed = true;
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
	}

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }
}
