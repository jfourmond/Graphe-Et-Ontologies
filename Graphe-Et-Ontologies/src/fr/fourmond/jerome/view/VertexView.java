package fr.fourmond.jerome.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import fr.fourmond.jerome.framework.Vertex;

/**
 * {@link VertexView} est un {@link JComponent} dessinant
 * un sommet du graphe
 * @author jfourmond
 */
public class VertexView extends JComponent implements MouseListener {
	
	private static final long serialVersionUID = 1L;
	
	private static int radius = 25;
	
	public Vertex vertex;
	
	//	CONSTRUCTEURS
	public VertexView(Vertex vertex) {
		this.vertex = vertex;
		setLocation(0, 0);
		setBackground(Color.BLUE);
		addMouseListener(this);
	}
	
	public VertexView(Vertex vertex, int x, int y) {
		this.vertex = vertex;
		// position = new Point(x, y);
		setLocation(x, y);
		setBackground(Color.BLUE);
		addMouseListener(this);
	}
	
	//	GETTERS
	
	public Vertex getVertex() { return vertex; }
	
	// public double getX() { return position.getX(); }
	
	
	//	SETTERS
	public void setVertex(Vertex vertex) { this.vertex = vertex; }
	
	@Override
	public Dimension getPreferredSize() { return new Dimension(radius * 2, radius * 2); }
	
	@Override
	public void paint(Graphics g) {
		g.drawOval(0, 0, radius, radius);
		g.drawString(vertex.briefData(), radius, radius);
	}

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }
}
