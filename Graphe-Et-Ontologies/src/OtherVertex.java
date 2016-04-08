import fr.fourmond.jerome.framework.Vertex;

public class OtherVertex implements Vertex {
	static int ID = 0;
	
	private int id;
	private String value;

	public OtherVertex(String value) {
		this.id = ID++;
		this.value = value;
	}
	
	@Override
	public String fullData() {
		String ch = "";
		ch += "ID : " + id + "\n";
		ch += "VALUE : \"" + value + "\"\n";
		return ch;
	}

	@Override
	public String briefData() {
		return value;
	}
	
	@Override
	public String toString() {
		return fullData();
	}
}
