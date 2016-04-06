package fr.fourmond.jerome.view;

import fr.fourmond.jerome.framework.Vertex;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JComponent;

/**
 * {@link VertexView} est un {@link JComponent} dessinant
 * un sommet du graphe
 * @author jfourmond
 */
public class VertexView extends JComponent {
	
	private static final long serialVersionUID = 1L;
	
	private int x;
	private int y;
	
	private Vertex vertex;
	
	//	CONSTRUCTEURS
	public VertexView(Vertex vertex) {
		this.vertex = vertex;
		Random rand = new Random();
		x = rand.nextInt(100);
		y = rand.nextInt(100);
	}
	
	//	GETTERS
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	//	SETTERS
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(300, 300);
	}
	
	@Override
	public void paint(Graphics g) {
		System.out.println("Draw : (" + x + "," + y + ")");
		g.drawOval(x, y, 20, 20);
		g.drawString(vertex.briefData(), x+5, y+15);
	}
}
