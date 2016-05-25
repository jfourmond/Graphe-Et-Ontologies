package fr.fourmond.jerome.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class SavedPos {
	private static final String FILE = "FILE";
		private static final String PATH = "path";
	private static final String ENTREE = "ENTREE";
			private static final String ID = "id";
		private static final String X = "X";
		private static final String Y = "Y";
	
	private final String directoryPath = "tmp/";
	
	private File treeFile;
	private Map<String, Pair<Double, Double>> positions;
	
	//	CONSTRUCTEURS
	public SavedPos(File treeFile) {
		this.treeFile = treeFile;
		positions = new HashMap<>();
	}
	
	public SavedPos(File treeFile, List<VertexView> vertices) {
		this.treeFile = treeFile;
		positions = new HashMap<>();
		setPositions(vertices);
	}
	
	//	SETTERS
	public void setPositions(Map<String, Pair<Double, Double>> positions) { this.positions = positions; }
	
	public void setTreeFile(File treeFile) { this.treeFile = treeFile; }
	
	//	GETTERS
	public File getTreeFile() { return treeFile; }
	
	public Map<String, Pair<Double, Double>> getPositions() { return positions; }

	// METHODES
	public void save(List<VertexView> vertices) throws IOException {
		setPositions(vertices);
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
			
			List<Element> entries =  racine.getChildren(ENTREE);

			// Création des sommets
			for(Element courant : entries) {
				String vertex = courant.getAttributeValue(ID);		// Clé du sommet
				Element posX = courant.getChild(X);					// Position X
					double x = Double.parseDouble(posX.getText());
				Element posY = courant.getChild(Y);					// Position Y
					double y = Double.parseDouble(posY.getText());
				Pair<Double, Double> pair = new Pair<Double, Double>(x, y);
				
				positions.put(vertex, pair);
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
				directory.mkdir();
			
			File file = new File(directory, nameFile + "_pos.xml");
			
			if(!file.exists())
				file.createNewFile();
			
			Document documentJDOM;
			// Racine du document
			Element racine = new Element(FILE);
				Attribute filePath = new Attribute(PATH, treeFile.getCanonicalPath());
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
			
			// Enregistrement
			documentJDOM = new Document(racine);
			
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(documentJDOM, new FileOutputStream(file));
			
			return true;
		}
		
	}
}
