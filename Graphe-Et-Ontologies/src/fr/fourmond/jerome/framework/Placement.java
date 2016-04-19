package fr.fourmond.jerome.framework;

import java.awt.Point;
import java.util.Random;

/**
 * {@link Placement} est une classe reprenant (dans le concept) la classe {@link Random}.
 * Elle permet de créer un {@link Object} envoyant différentes positions à chaque appel de next().
 * @author jfourmond
 */
public class Placement {
	public final static Point[] positions = {
		// First Rectangle
		new Point(100, 100),
		new Point(400, 100),
		new Point(400, 400),
		new Point(100, 400),
		// Second Rectangle
		new Point(250, 50),
		new Point(450, 250),
		new Point(250, 450),
		new Point(50, 250),
		// Third Rectangle
		new Point(175, 75),
		new Point(425, 175),
		new Point(325, 425),
		new Point(75, 325),
		// Fourth Rectangle
		new Point(75, 175),
		new Point(325,  75),
		new Point(425,  325),
		new Point(175, 425)
	};
	
	public Random rand;
	public int current;
	
	public Placement() {
		current = 0;
		rand = new Random();
	}
	
	public Placement(int current) {
		this.current = current;
		rand = new Random();
	}
	
	/**
	 * @return the next {@link Point} in the positions tab
	 * unless all the tab have been browsed, it return a random {@link Point}
	 */
	public Point next() {
		Point p;
		if(current >= positions.length) {
			p = randomPoint();
		} else {
			p = positions[current];
		}
		current++;
		return p;
	}
	
	private Point randomPoint() { return new Point(rand.nextInt(500), rand.nextInt(500)); }
}
