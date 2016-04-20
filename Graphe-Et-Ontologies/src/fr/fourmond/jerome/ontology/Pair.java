package fr.fourmond.jerome.ontology;

public class Pair<T_First, T_Second> {
	private T_First first;
	private T_Second second;
	
	public Pair() { }
	
	public Pair(T_First first, T_Second second) {
		this.setFirst(first);
		this.second = second;
	}

	public T_First getFirst() { return first; }

	public T_Second getSecond() { return second; }
	
	public void setFirst(T_First first) { this.first = first; }
	
	public void setSecond(T_Second second) { this.second = second; }

}
