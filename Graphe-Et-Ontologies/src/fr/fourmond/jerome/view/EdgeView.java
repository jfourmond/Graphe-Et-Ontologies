package fr.fourmond.jerome.view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import fr.fourmond.jerome.framework.Edge;
import fr.fourmond.jerome.framework.Vertex;

public class EdgeView<T_Vertex extends Vertex, T_Value> extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private Edge<T_Vertex, T_Value> edge;
	private VertexView start;
	private VertexView end;
	
	public EdgeView(Edge<T_Vertex, T_Value> edge) {
		this.edge = edge;
	}
	
	public EdgeView(Edge<T_Vertex, T_Value> edge, VertexView start, VertexView end) {
		this.edge = edge;
		this.start = start;
		this.end = end;
	}
	
	//	GETTERS
	public Edge<T_Vertex, T_Value> getEdge() { return edge; }
	
	public VertexView getStart() { return start; }
	
	public VertexView getEnd() { return end; }
	
	//	SETTERS
	public void setEdge(Edge<T_Vertex, T_Value> edge) { this.edge = edge; }
	
	public void setStart(VertexView start) { this.start = start; }
	
	public void setEnd(VertexView end) { this.end = end; }
	
	@Override
	public Dimension getPreferredSize() { return new Dimension(500, 500); }
	
	@Override
	public void paint(Graphics g) {
		int ecartX = end.getX() - start.getX();
		int ecartY = end.getY() - start.getY();
		System.out.println("Ecart X : " + ecartX);
		System.out.println("Ecart Y : " + ecartY);
		g.drawLine(0, 0, ecartX, ecartY);
		g.drawString(Integer.toString((int) edge.getValue()), ecartX/2, ecartY/2);
	}
}
