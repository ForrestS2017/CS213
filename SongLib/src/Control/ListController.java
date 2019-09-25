package Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * @author Forrest Smith
 * @author Jim Tang
 */

public class ListController {
	
	
	/*
	 * Side-panel list
	 */
	@FXML
	ListView<String> listView;

	/*
	 * Side-panel list casted as Observable List
	 */
	@FXML
	private ObservableList<String> obsList;
	
	/*
	 * Buttons in the Song Details pane
	 */
	@FXML
	Button deleteButton, updateButton;
	
	/*
	 * Menu items under "File"
	 */
	@FXML
	MenuItem newSongMenuItem, deleteAllMenuItem;
	
	public void start(Stage mainStage)
	{
		obsList = FXCollections.observableArrayList();	// TODO: fill somehow
		listView.setItems(obsList);
	}
	
	private void showItem(Stage mainStage)
	{
		// TODO
		return;
	}
	
	private void showItemInputDialog(Stage mainStage)
	{
		// TODO
		return;
	}
}
