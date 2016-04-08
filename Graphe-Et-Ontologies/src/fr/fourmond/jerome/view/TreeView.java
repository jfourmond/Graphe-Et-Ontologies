package fr.fourmond.jerome.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
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

	List<VertexView> vertices;
	
	public TreeView(Tree<T_Vertex, T_Edge> tree) {
		setBorder(BorderFactory.createLineBorder(Color.black));
		setBackground(Color.RED);
		
		this.tree = tree;
		
		buildComposants();
		buildInterface();
		// drawVertex();
		buildEvents();
	}

	private void buildComposants() {
		Random rand = new Random();
		
		setLayout(null);
		vertices = new ArrayList<VertexView>();
		for(T_Vertex vertex : tree.getVertices()) {
			vertices.add(new VertexView(vertex, rand.nextInt(100), rand.nextInt(100)));
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
			vertex.setBounds(vertex.getPosition().x + insets.left, vertex.getPosition().y + insets.top, size.width, size.height);
		}
		
		//Size and display the window.
		insets = getInsets();
		setSize(300 + insets.left + insets.right,125 + insets.top + insets.bottom);
	}
	
	private void buildEvents() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }
}
