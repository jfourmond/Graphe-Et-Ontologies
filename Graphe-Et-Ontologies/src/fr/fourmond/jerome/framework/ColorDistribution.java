package fr.fourmond.jerome.framework;

import java.util.ArrayList;
import java.util.List;
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
		Color.BLUE, Color.ORANGE, Color.GREEN, Color.PINK, Color.BROWN,
		Color.CYAN, Color.ORANGERED, Color.GREENYELLOW, Color.DEEPPINK,
		Color.CORNFLOWERBLUE, Color.DARKORANGE, Color.LAWNGREEN, Color.VIOLET,
		Color.AQUA, Color.CRIMSON,
		Color.GRAY, Color.DARKGRAY, Color.LIGHTGRAY, Color.BLACK
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
	 * Retourne la {@link Color} suivante dans le tableau de {@link Color}s
	 * sauf si tout le tableau a été parcouru, retourne une {@link Color} aléatoire
	 * @return la {@link Color} suivante dans le tableau de {@link Color}s
	 * sauf si tout le tableau a été parcouru, retourne une {@link Color} aléatoire
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
	
	/**
	 * Retourne une couleur aléatoire
	 * @return une couleur aléatoire
	 */
	private Color randomColor() {
		last = new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1.0);
		return last;
	}
	
	/**
	 * Retourne une liste des {@link Color} disponibles statiquement
	 * @return une liste des {@link Color} disponibles statiquement
	 */
	public List<Color> list() {
		List<Color> list = new ArrayList<>();
		for(Color color : colors)
			list.add(color);
		return list;
	}
}
