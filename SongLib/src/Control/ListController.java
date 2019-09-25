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
	
	/*
	 * Iniialize list	
	 */
	public void start(Stage mainStage)
	{
		obsList = FXCollections.observableArrayList();	// TODO: fill somehow
		if (!obsList.isEmpty()) listView.setItems(obsList);
	}
	
	/*
	 * @param mainStage
	 * Show selected song in details pane
	 */
	private void showItem(Stage mainStage)
	{
		// TODO
		return;
	}
	
	/*
	 * @param mainStage
	 * Show selected song details in dialog
	 */
	private void showItemInputDialog(Stage mainStage)
	{
		// TODO
		return;
	}
}
