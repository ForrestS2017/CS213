package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author Forrest Smith
 * @author Jim Tang
 */

public class SongLib extends Application {

	private final String LAYOUT_PATH = "/View/SongLib.fxml";
	private final String APPLICATION_NAME = "Song Library";
	private final boolean IS_RESIZEABLE = false;

    @Override
    public void start(Stage primaryStage) throws Exception{
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource(LAYOUT_PATH));
    	BorderPane root = (BorderPane)loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setScene(scene);
    	primaryStage.setTitle(APPLICATION_NAME);
    	primaryStage.setResizable(IS_RESIZEABLE);
    	primaryStage.show();
        return;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
