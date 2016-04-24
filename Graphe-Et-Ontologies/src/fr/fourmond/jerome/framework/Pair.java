package fr.fourmond.jerome.framework;

/**
 * {@link Pair} est une classe générique permettant
 * la gestion de pair d'objets
 * @param <T_First> : type du premier objet
 * @param <T_Second> : type du second objet
 * @author jfourmond
 */
public class Pair<T_First, T_Second> {
	private T_First first;
	private T_Second second;
	
	//	CONSTRUCTEURS
	public Pair() { }
	
	public Pair(T_First first, T_Second second) {
		this.setFirst(first);
		this.second = second;
	}

	//	GETTERS
	public T_First getFirst() { return first; }

	public T_Second getSecond() { return second; }
	
	//	SETTERS
	public void setFirst(T_First first) { this.first = first; }
	
	public void setSecond(T_Second second) { this.second = second; }
	
	@Override
	public String toString() { return "( " + first + " , " + second + " )"; }
	
	@Override
	public boolean equals(Object obj) {
		if (getClass() != obj.getClass())
			return false;
		else {
			@SuppressWarnings("unchecked")
			Pair<T_First, T_Second> pair = (Pair<T_First, T_Second>) obj;
			return (first.equals(pair.first) && second.equals(pair.second));
		}
	}
}
