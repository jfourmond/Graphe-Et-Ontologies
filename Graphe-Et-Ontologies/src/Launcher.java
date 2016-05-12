
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.TreeLoader;
import fr.fourmond.jerome.view.AppStage;
import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class Launcher extends Application {
	private static Tree tree;
	
	@Override
	public void start(Stage primaryStage) { new AppStage(tree); }

	public static void main(String[] args) {
		tree = new Tree();
		if(args.length > 0) {
			// Ouverture avec argument
			TreeLoader loader = new TreeLoader(tree, args[0]);
			loader.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent event) {
					tree = loader.getTree();
				}
			});
			Thread thread = new Thread(loader);
			thread.start();
			try {
				thread.join();
				launch(args);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else launch(args);
	}
}
