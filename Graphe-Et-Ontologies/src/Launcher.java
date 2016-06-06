
import fr.fourmond.jerome.config.Settings;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.view.LoadingSplashStage;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe principale
 * Exécution du programme
 * @author jfourmond
 */
public class Launcher extends Application {
	private static Tree tree;
	private static String filename;
	
	@Override
	public void start(Stage primaryStage) {
		if(filename == null)
			new LoadingSplashStage(tree);
		else
			new LoadingSplashStage(tree, filename);
	}

	public static void main(String[] args) {
		tree = new Tree();
		if(args.length > 0)
			filename = args[0];
		
		try {
			Settings.loadSettings();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		launch(args);
	}
}
