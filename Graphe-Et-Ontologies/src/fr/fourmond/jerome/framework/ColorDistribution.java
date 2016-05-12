package fr.fourmond.jerome.framework;

import java.util.Random;

import javafx.scene.paint.Color;

/**
 * {@link ColorDistribution} est une classe reprenant (dans le concept) la classe {@link Random}.
 * Elle permet de créer un {@link Object} envoyant différentes {@link Color} à chaque appel de next().
 * 9 {@link Color} peuvent être renvoyés "statiquement" avant d'être générées aléatoirement.
 * @author jfourmond
 */
public class ColorDistribution {
	public final static Color[] colors = {
		Color.BLUE,
		Color.DARKKHAKI,
		Color.GREEN,
		Color.AQUA,
		Color.BROWN,
		Color.CRIMSON,
		Color.CORNFLOWERBLUE,
		Color.DEEPPINK
	};
	
	public Random rand;
	public int current;
	private Color last;
	
	public ColorDistribution() {
		current = 0;
		rand = new Random();
	}
	
	public ColorDistribution(int current) {
		this.current = current;
		rand = new Random();
	}
	
	/**
	 * Retourne la dernière {@link Color}
	 * @return la dernière {@link Color}
	 */
	public Color last() { return last; }
	
	/**
	 * @return the next {@link Point} in the positions tab
	 * unless all the tab have been browsed, it return a random {@link Point}
	 */
	public Color next() {
		if(current >= colors.length) {
			last = randomColor();
		} else {
			last = colors[current];
		}
		current++;
		return last;
	}

	private Color randomColor() {
		last = new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1.0);
		return last;
	}
}
