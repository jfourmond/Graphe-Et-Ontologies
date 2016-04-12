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
	private int gapX;
	private int gapY;
	
	// private static double distance;
	
	public EdgeView(Edge<T_Vertex, T_Value> edge) {
		this.edge = edge;
	}
	
	public EdgeView(Edge<T_Vertex, T_Value> edge, VertexView start, VertexView end) {
		this.edge = edge;
		this.start = start;
		this.end = end;
	
		computeDistance();
	}
	
	//	GETTERS
	public Edge<T_Vertex, T_Value> getEdge() { return edge; }
	
	public VertexView getStart() { return start; }
	
	public VertexView getEnd() { return end; }
	
	public int getGapX() { return gapX; }
	
	public int getGapY() { return gapY; }
	
	//	SETTERS
	public void setEdge(Edge<T_Vertex, T_Value> edge) { this.edge = edge; }
	
	public void setStart(VertexView start) { this.start = start; }
	
	public void setEnd(VertexView end) { this.end = end; }
	
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
