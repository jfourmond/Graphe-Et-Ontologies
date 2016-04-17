package fr.fourmond.jerome.view.swing;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import fr.fourmond.jerome.framework.Edge;
import fr.fourmond.jerome.framework.Vertex;

/**
 * {@link EdgeView} est un {@link JComponent} représentant
 * un arc du graphe
 * @param <T_Vertex> : type du sommet implémentant l'interface {@link Vertex}
 * @param <T_Value> : type de la valeur de {@link Edge}
 * @author jfourmond
 */
public class EdgeView<T_Vertex extends Vertex, T_Value> extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private Edge<T_Vertex, T_Value> edge;
	private VertexView<T_Vertex> start;
	private VertexView<T_Vertex> end;
	private int gapX;
	private int gapY;
	
	public EdgeView(Edge<T_Vertex, T_Value> edge) {
		this.edge = edge;
	}
	
	public EdgeView(Edge<T_Vertex, T_Value> edge, VertexView<T_Vertex> start, VertexView<T_Vertex> end) {
		this.edge = edge;
		this.start = start;
		this.end = end;
	
		computeDistance();
	}
	
	//	GETTERS
	public Edge<T_Vertex, T_Value> getEdge() { return edge; }
	
	public VertexView<T_Vertex> getStart() { return start; }
	
	public VertexView<T_Vertex> getEnd() { return end; }
	
	public int getGapX() { return gapX; }
	
	public int getGapY() { return gapY; }
	
	//	SETTERS
	public void setEdge(Edge<T_Vertex, T_Value> edge) { this.edge = edge; }
	
	public void setStart(VertexView<T_Vertex> start) { this.start = start; }
	
	public void setEnd(VertexView<T_Vertex> end) { this.end = end; }
	
	private void computeDistance() {
		// distance = Math.sqrt(Math.pow(start.getX() - end.getX(), 2)+ Math.pow(start.getY() - end.getY(), 2));
		gapX = end.getX() - start.getX();
		gapY = end.getY() - start.getY();
	}
	
	@Override
	public Dimension getPreferredSize() { return new Dimension(1000, 1000); }
	
	@Override
	public void paint(Graphics g) {
		computeDistance();
		
		if(gapY < 0) {
			if(gapX < 0) {
				g.drawLine(0, 0, -gapX, -gapY);
				g.drawString(edge.data(), -gapX/2, -gapY/2);
			} else {
				g.drawLine(0, -gapY, gapX, 0);
				g.drawString(edge.data(), gapX/2, -gapY/2);
			}
		} else {
			if(gapX < 0) {
				g.drawLine(-gapX, 0, 0, gapY);
				g.drawString(edge.data(), -gapX/2, gapY/2);
			} else {
				g.drawLine(0, 0, gapX, gapY);
				g.drawString(edge.data(), gapX/2, gapY/2);
			}
		}
	}
}
