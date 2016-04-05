package test;

import fr.fourmond.jerome.framework.Vertex;


public class VertexForTest implements Vertex{
	private static int ID = 0;
	
	private int id;
	private int value;
	
	public VertexForTest(int value) {
		this.id = ID++;
		this.value = value;
	}
	
	//	GETTERS
	public int getValue() {
		return value;
	}
	
	//	SETTERS
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public String fullData() {
		String ch = "";
		ch += "ID : " + id + "\n";
		ch += "VALUE : " + value + "\n";
		return ch;
	}

	@Override
	public String briefData() {
		return Integer.toString(id);
	}
	
	@Override
	public String toString() {
		return fullData();
	}
}
