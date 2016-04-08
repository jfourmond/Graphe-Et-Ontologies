import fr.fourmond.jerome.framework.Vertex;

public class DefaultVertex implements Vertex {

	private static int ID = 0;
	
	private int id;
	private int value;
	
	public DefaultVertex(int value) {
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
