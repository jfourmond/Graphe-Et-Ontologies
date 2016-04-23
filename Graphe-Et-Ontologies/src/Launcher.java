
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.view.OntologyStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

	private static Tree tree;
	
	@Override
	public void start(Stage primaryStage) {
		new OntologyStage(tree);
	}

	public static void main(String[] args) {
		tree = new Tree();
		launch(args);
	}
}
