package fr.fourmond.jerome.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import fr.fourmond.jerome.framework.Pair;
import fr.fourmond.jerome.framework.TreeLoaderException;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;

/**
 * {@link SavedPos} est une classe utilisée pour sauvegarder des positions
 * de {@link VertexView} dans un fichier XML
 * @author jfourmond
 */
public class SavedPos {
	private static final String FILE = "FILE";
		private static final String PATH = "path";
	private static final String ENTREE = "ENTREE";
			private static final String ID = "id";
		private static final String X = "X";
		private static final String Y = "Y";
	private static final String RELATION = "RELATION";
			private static final String NOM = "nom";
		private static final String ROUGE = "rouge";
		private static final String VERT = "vert";
		private static final String BLEU = "bleu";
	
	private final String directoryPath = "tmp/";
	
	private File treeFile;
	private Map<String, Pair<Double, Double>> positions;
	private Map<String, Color> relationsColor;
	
	//	CONSTRUCTEURS
	public SavedPos(File treeFile) {
		this.treeFile = treeFile;
		positions = new HashMap<>();
		relationsColor = new HashMap<>();
	}
	
	public SavedPos(File treeFile, List<VertexView> vertices, Map<String, Color> relationsColor) {
		this.treeFile = treeFile;
		positions = new HashMap<>();
		setPositions(vertices);
		this.relationsColor = relationsColor;
	}
	
	//	SETTERS
	public void setPositions(Map<String, Pair<Double, Double>> positions) { this.positions = positions; }
	
	public void setTreeFile(File treeFile) { this.treeFile = treeFile; }
	
	public void setRelationsColor(Map<String, Color> relationsColor) { this.relationsColor = relationsColor; }
	
	//	GETTERS
	public File getTreeFile() { return treeFile; }
	
	public Map<String, Pair<Double, Double>> getPositions() { return positions; }

	public Map<String, Color> getRelationsColor() { return relationsColor; }
	
	// METHODES
	public void save(List<VertexView> vertices) {
		setPositions(vertices);
		Saver saver = new Saver();
		new Thread(saver).start();
	}
	
	public void save(List<VertexView> vertices, Map<String, Color> relationsColor) {
		setPositions(vertices);
		setRelationsColor(relationsColor);
		Saver saver = new Saver();
		new Thread(saver).start();
	}
	
	public void load() throws Exception {
		Loader loader = new Loader();
		loader.call();
	}
	
	public void setPositions(List<VertexView> vertices) {
		positions.clear();
		for(VertexView vertex : vertices) {
			Pair<Double, Double> pair = new Pair<>(vertex.getCenterX(), vertex.getCenterY());
			positions.put(vertex.getVertex().getID(), pair);
		}
	}
	
	public String withoutExtension(String fileName) {
		String ch = null;
		int i = fileName.lastIndexOf('.');
		if(i > 0)
			ch = fileName.substring(0, i);
		return ch;
	}
	
	public Pair<Double, Double> positionOf(String id) { return positions.get(id); }
	
	protected class Loader extends Task<Boolean> {
		private SAXBuilder saxBuilder;
		
		@Override
		protected Boolean call() throws Exception {
			String nameFile= withoutExtension(treeFile.getName());
			if(nameFile == null)
				throw new Exception("Construction du nom du fichier impossible");
			
			File directory = new File(directoryPath);
			File file = new File(directory, nameFile + "_pos.xml");

			saxBuilder = new SAXBuilder();
			
			if(!file.exists())
				throw new FileNotFoundException("Aucun fichier de positions");

			Document document = saxBuilder.build(file);
			
			Element racine = document.getRootElement();
			if(!racine.getName().equals(FILE))
				throw new TreeLoaderException("La DTD n'est pas respectée.");
			
			if(!racine.getAttribute(PATH).getValue().equals(treeFile.getName()))
				throw new Exception("Le chemin associé est différent.");
			
			// Chargement des positions des sommets
			List<Element> entries =  racine.getChildren(ENTREE);
			for(Element courant : entries) {
				String vertex = courant.getAttributeValue(ID);		// Clé du sommet
				Element posX = courant.getChild(X);					// Position X
					double x = Double.parseDouble(posX.getText());
				Element posY = courant.getChild(Y);					// Position Y
					double y = Double.parseDouble(posY.getText());
				Pair<Double, Double> pair = new Pair<Double, Double>(x, y);
				
				positions.put(vertex, pair);
			}
			
			// Chargement des couleurs des relations
			List<Element> relations = racine.getChildren(RELATION);
			for(Element courant : relations) {
				Color color;
				String name = courant.getAttributeValue(NOM);
				double red = Double.parseDouble(courant.getChildText(ROUGE));
				double green = Double.parseDouble(courant.getChildText(VERT));
				double blue = Double.parseDouble(courant.getChildText(BLEU));
				color = new Color(red, green, blue, 1.0);
				relationsColor.put(name, color);
			}
			
			return true;
		}
	}
	
	protected class Saver extends Task<Boolean> {
		@Override
		protected Boolean call() throws Exception {
			String nameFile= withoutExtension(treeFile.getName());
			if(nameFile == null)
				throw new Exception("Construction du nom du fichier impossible");
			
			File directory = new File(directoryPath);
			if(!directory.exists())
				if(!directory.mkdirs())
					throw new Exception("Echec de la création du répertoire tmp");
			
			File file = new File(directory, nameFile + "_pos.xml");
			
			if(!file.exists())
				file.createNewFile();
			
			Document documentJDOM;
			// Racine du document
			Element racine = new Element(FILE);
				Attribute filePath = new Attribute(PATH, treeFile.getName());
				racine.setAttribute(filePath);
			for(Entry<String, Pair<Double, Double>> entry : positions.entrySet()) {
				Element entree = new Element(ENTREE);
				racine.addContent(entree);
				// Ajout des attributs
				Attribute id = new Attribute(ID, entry.getKey());
				entree.setAttribute(id);
				// Ajout des positions
					// Position X
					Element posX = new Element(X);
					posX.setText(String.valueOf(entry.getValue().getFirst()));
					entree.addContent(posX);
					// Position Y
					Element posY = new Element(Y);
					posY.setText(String.valueOf(entry.getValue().getSecond()));
					entree.addContent(posY);
			}
			
			for(Entry<String, Color> entry : relationsColor.entrySet()) {
				Element relation = new Element(RELATION);
					Attribute name = new Attribute(NOM, entry.getKey());
						relation.setAttribute(name);
						// Traitement de la couleur
						Color color = entry.getValue();
						Element red = new Element(ROUGE);
							red.setText(Double.toString(color.getRed()));
							relation.addContent(red);
						Element green = new Element(VERT);
							green.setText(Double.toString(color.getGreen()));
							relation.addContent(green);
						Element blue = new Element(BLEU);
							blue.setText(Double.toString(color.getBlue()));
							relation.addContent(blue);
				racine.addContent(relation);
			}
			
			// Enregistrement
			documentJDOM = new Document(racine);
			
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(documentJDOM, new FileOutputStream(file));
			
			return true;
		}
		
	}
}
