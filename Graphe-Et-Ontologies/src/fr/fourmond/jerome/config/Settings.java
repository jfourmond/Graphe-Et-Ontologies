package fr.fourmond.jerome.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Settings {
	private static final String propFileName = "config.properties";
	private static final String autoSavePP = "auto-save";
		private static boolean autoSave = false;
	private static final String autoIdPP = "auto-id";
		private static boolean autoId = false;
	private static final String showVerticesPP = "show-vertices";
		private static boolean showVertices = true;
	private static final String showWordingPP = "show-wording";
		private static boolean showWording = false;
	
	/**
	 * Chargement des propriétés/options du programme
	 * @throws Exception si une erreur se produit lors de la lecture du fichier
	 */
	public static void loadSettings() throws Exception {
		Properties properties = new Properties();
		InputStream is = new FileInputStream(propFileName);
		properties.load(is);
			
		autoSave = Boolean.parseBoolean(properties.getProperty(autoSavePP));
		autoId = Boolean.parseBoolean(properties.getProperty(autoIdPP));
		showVertices = Boolean.parseBoolean(properties.getProperty(showVerticesPP));
		showWording = Boolean.parseBoolean(properties.getProperty(showWordingPP));

		is.close();
	}
	
	/**
	 * Sauvegarde des propriétés/options du programme
	 * @throws Exception si une erreur se produit lors de l'ouverture du fichier
	 */
	public static void saveSettings() throws Exception {
		Properties properties = new Properties();
		
		OutputStream os = new FileOutputStream(propFileName);
		
		properties.setProperty(autoSavePP, String.valueOf(autoSave));
		properties.setProperty(autoIdPP, String.valueOf(autoId));
		properties.setProperty(showVerticesPP, String.valueOf(showVertices));
		properties.setProperty(showWordingPP, String.valueOf(showWording));
		
		properties.store(os, null);
		
		os.close();
	}
	
	//	GETTERS STATIQUES
	public static boolean isAutoSave() { return autoSave; }
	
	public static boolean isAutoId() { return autoId; }
	
	public static boolean isShowVertices() { return showVertices; }
	
	public static boolean isShowWording() { return showWording; }
	
	//	SETTERS STATIQUES
	public static void setAutoSave(boolean autoSave) { Settings.autoSave = autoSave; }
	
	public static void setAutoId(boolean autoId) { Settings.autoId = autoId; }
	
	public static void setShowVertices(boolean showVertices) { Settings.showVertices = showVertices; }
	
	public static void setShowWording(boolean showWording) { Settings.showWording = showWording; }
}
