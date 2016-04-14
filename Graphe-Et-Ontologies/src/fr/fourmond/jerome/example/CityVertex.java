package fr.fourmond.jerome.example;

import fr.fourmond.jerome.framework.Vertex;

/**
 * Exemple d'implémentation concrète de Vertex
 * @author jfourmond
 */
public class CityVertex implements Vertex, Comparable<CityVertex> {
	
	private String name;
	private String postalCode;
	private int population;
	private double area;
	
	public CityVertex(String name, String postalCode, int population, double area) {
		this.name = name;
		this.postalCode = postalCode;
		this.population = population;
		this.area = area;
	}
	
	//	GETTERS
	public String getName() { return name; }
	
	public double getArea() { return area; }
	
	public int getPopulation() { return population; }
	
	public String getPostalCode() { return postalCode; }
	
	//	SETTERS
	public void setName(String name) { this.name = name; }
	
	public void setArea(double area) { this.area = area; }
	
	public void setPopulation(int population) { this.population = population; }
	
	public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
	
	@Override
	public String toString() {
		String ch = postalCode + " \t";
		ch += name + " \t";
		ch += population + " \t";
		ch += area + "\n";
		return ch;
	}
	
	@Override
	public String fullData() {
		String ch = "Ville : \n";
		ch += "\tCode postal : " + postalCode + " \n";
		ch += "\tNom : " + name + " \n";
		ch += "\tPopulation : " + population + " \n";
		ch += "\tSuperficie : " + area + "\n";
		return ch;
	}

	@Override
	public String briefData() {
		return name;
	}

	@Override
	public int compareTo(CityVertex o) {
		int comparePostalCode = (this.postalCode.compareToIgnoreCase(o.postalCode));
		if(comparePostalCode != 0) return comparePostalCode;
		int compareName = (this.name.compareToIgnoreCase(o.name));
		if(compareName != 0) return compareName;
		int comparePopulation = (this.population < o.population) ? -1 : (this.population > o.population) ? 1 : 0;
		if(comparePopulation != 0) return comparePopulation;
		int compareArea = (this.area < o.area) ? -1 : (this.area > o.area) ? 1 : 0;
		if(compareArea != 0) return compareArea;
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			CityVertex o = (CityVertex) obj;
			return (this.postalCode.equals(o.postalCode) && this.name.equals(o.name) &&
					this.population == o.population && this.area == o.area);
		} catch (Exception E) {
			E.printStackTrace();
		}
		return false;
	}

}
