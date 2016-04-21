package fr.fourmond.jerome.framework;

import java.awt.Point;
import java.util.Random;

import javafx.scene.paint.Color;

/**
 * {@link Placement} est une classe reprenant (dans le concept) la classe {@link Random}.
 * Elle permet de créer un {@link Object} envoyant différentes positions à chaque appel de next().
 * 24 {@link Point} peuvent être renvoyés "statiquement" avant d'être générés aléatoirement.
 * @author jfourmond
 */
/**
 * {@link ColorDistribution} est une classe reprenant (dans le concept) la classe {@link Random}.
 * Elle permet de créer un {@link Object} envoyant différentes {@link Color} à chaque appel de next().
 * 5 {@link Color} peuvent être renvoyés "statiquement" avant d'être générées aléatoirement.
 * @author jfourmond
 */
public class ColorDistribution {
	public final static Color[] colors = {
		Color.BLACK,
		Color.RED,
		Color.BLUE,
		Color.YELLOW,
		Color.GREEN,
	};
	
	public Random rand;
	public int current;
	
	public ColorDistribution() {
		current = 0;
		rand = new Random();
	}
	
	public ColorDistribution(int current) {
		this.current = current;
		rand = new Random();
	}
	
	/**
	 * @return the next {@link Point} in the positions tab
	 * unless all the tab have been browsed, it return a random {@link Point}
	 */
	public Color next() {
		Color c;
		if(current >= colors.length) {
			c = randomColor();
		} else {
			c = colors[current];
		}
		current++;
		return c;
	}

	private Color randomColor() {
		return new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1.0);
	}
}
