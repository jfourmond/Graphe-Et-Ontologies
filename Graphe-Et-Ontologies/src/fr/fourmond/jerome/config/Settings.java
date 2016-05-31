package fr.fourmond.jerome.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import fr.fourmond.jerome.view.VertexView;
import javafx.concurrent.Task;

public class Settings {
	private static final String propFileName = "config.properties";
	private static final String widthPP = "width";
		private static double width = 800;
	private static final String heightPP = "height";
		private static double height = 600;
	private static final String autoIdPP = "auto-id";
		private static boolean autoId = false;
	private static final String showVerticesPP = "show-vertices";
		private static boolean showVertices = true;
	private static final String showWordingPP = "show-wording";
		private static boolean showWording = false;
	private static final String vertexRadiusPP = "vertex-radius";
	
	/**
	 * Chargement des propriétés/options du programme
	 * @throws Exception si une erreur se produit lors de la lecture du fichier
	 */
	public static void loadSettings() throws Exception {
		Loader loader = new Loader();
		loader.call();
	}
	
	/**
	 * Sauvegarde des propriétés/options du programme
	 * @throws Exception si une erreur se produit lors de l'ouverture du fichier
	 */
	public static void saveSettings() throws Exception {
		Saver saver = new Saver();
		new Thread(saver).start();
	}
	
	//	GETTERS STATIQUES
	public static double getWidth() { return width; }
	
	public static double getHeight() { return height; }
	
	public static boolean isAutoId() { return autoId; }
	
	public static boolean isShowVertices() { return showVertices; }
	
	public static boolean isShowWording() { return showWording; }
	
	public static int getVertexRadius() { return VertexView.getRadius(); }
	
	//	SETTERS STATIQUES
	public static void setWidth(double width) { Settings.width = width; }
	
	public static void setHeight(double height) { Settings.height = height; }
	
	public static void setAutoId(boolean autoId) { Settings.autoId = autoId; }
	
	public static void setShowVertices(boolean showVertices) { Settings.showVertices = showVertices; }
	
	public static void setShowWording(boolean showWording) { Settings.showWording = showWording; }
	
	public static void setVertexRadius(int vertexDiameter) { VertexView.setRadius(vertexDiameter); }
	
	protected static class Saver extends Task<Boolean> {
		@Override
		protected Boolean call() throws Exception {
			Properties properties = new Properties();
			
			OutputStream os = new FileOutputStream(propFileName);
			
			if(width < 800) width = 800;
			if(height < 600) height = 600;
			
			properties.setProperty(widthPP, String.valueOf(width));
			properties.setProperty(heightPP, String.valueOf(height));
			properties.setProperty(autoIdPP, String.valueOf(autoId));
			properties.setProperty(showVerticesPP, String.valueOf(showVertices));
			properties.setProperty(showWordingPP, String.valueOf(showWording));
			properties.setProperty(vertexRadiusPP, String.valueOf(VertexView.getRadius()));
			
			properties.store(os, null);
			
			os.close();
			
			return true;
		}
	}
	
	protected static class Loader extends Task<Boolean> {

		@Override
		protected Boolean call() throws Exception {
			Properties properties = new Properties();
			InputStream is = new FileInputStream(propFileName);
			properties.load(is);
				
			width = Double.parseDouble(properties.getProperty(widthPP));
			height = Double.parseDouble(properties.getProperty(heightPP));
			autoId = Boolean.parseBoolean(properties.getProperty(autoIdPP));
			showVertices = Boolean.parseBoolean(properties.getProperty(showVerticesPP));
			showWording = Boolean.parseBoolean(properties.getProperty(showWordingPP));
			VertexView.setRadius(Integer.parseInt(properties.getProperty(vertexRadiusPP)));

			is.close();
			
			return true;
		}
		
	}
}
