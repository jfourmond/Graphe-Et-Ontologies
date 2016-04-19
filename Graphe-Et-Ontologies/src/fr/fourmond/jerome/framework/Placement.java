package fr.fourmond.jerome.framework;

import java.awt.Point;

public class Placement {
	public final static Point[] positions = {
		new Point(100, 100),
		new Point(400, 100),
		new Point(400, 400),
		new Point(100, 400),
		new Point(250, 50),
		new Point(450, 250),
		new Point(250, 450),
		new Point(50, 250)
	};
	
	public static int current = 0;
	
	public static Point next() {
		Point p = positions[current];
		current++;
		return p;
	}
}
