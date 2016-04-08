package fr.fourmond.jerome.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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

import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.Vertex;

public class TreeView<T_Vertex extends Vertex, T_Edge> extends JPanel implements ActionListener, MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;

	private Tree<T_Vertex, T_Edge> tree;

	private  List<VertexView> vertices;
	
	// Attribut pour le déplacement
	private static VertexView vertexPressedOn;
	private static boolean pressed = false;
	private static MouseEvent decalage;
	private static Point actual;
	private static Point end;
	
	public TreeView(Tree<T_Vertex, T_Edge> tree) {
		setBorder(BorderFactory.createLineBorder(Color.black));
		this.tree = tree;
		
		buildComposants();
		buildInterface();
		buildEvents();
	}

	private void buildComposants() {
		Random rand = new Random();
		
		setLayout(null);
		vertices = new ArrayList<VertexView>();
		for(T_Vertex vertex : tree.getVertices()) {
			VertexView vertexView = new VertexView(vertex, rand.nextInt(100), rand.nextInt(100));
			vertexView.addMouseListener(this);
			vertexView.addMouseMotionListener(this);
			vertices.add(vertexView);
		}
	}
	
	private void buildInterface() {
		for(VertexView vertex : vertices) {
			add(vertex);
		}
		
		Insets insets = getInsets();
		Dimension size;
		for(VertexView vertex : vertices) {
			size = vertex.getPreferredSize();
			// vertex.setBounds(vertex.getPosition().x + insets.left, vertex.getPosition().y + insets.top, size.width, size.height);
			vertex.setBounds(vertex.getX() + insets.left, vertex.getY() + insets.top, size.width, size.height);
		}
		
		//Size and display the window.
		insets = getInsets();
		setSize(300 + insets.left + insets.right, 125 + insets.top + insets.bottom);
	}
	
	private void buildEvents() {
		for(VertexView vertex : vertices) {
			vertex.addMouseListener(this);
			vertex.addMouseMotionListener(this);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(pressed) {
			Insets insets = getInsets();
			Dimension size = vertexPressedOn.getPreferredSize();
			
			double posX = e.getX() + vertexPressedOn.getX() - decalage.getX();
			double posY = e.getY() + vertexPressedOn.getY() - decalage.getY();
			
			System.out.println((e.getX() + vertexPressedOn.getX()) + " , " + (e.getY() + vertexPressedOn.getY()));
			
			actual = new Point((int)posX,(int)posY);
			vertexPressedOn.setBounds(actual.x + insets.left, actual.y + insets.top, size.width, size.height);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) { }

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) {
		VertexView vertexView = (VertexView) e.getSource();
		double posX = vertexView.getX() + e.getX();
		double posY = vertexView.getY() + e.getY();
		System.out.println("PRESSÉ : " + vertexView.getVertex().briefData() + "(" + posX + "," + posY + ")");
		if(!pressed) {
			decalage = e;
			vertexPressedOn = vertexView;
			pressed = true;
			
			System.out.println("Decalage : X = " + decalage.getX() + " Y = " + decalage.getY() );
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
