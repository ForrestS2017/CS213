package Control;

import Model.Song;
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
	
	
	/**
	 * Side-panel list
	 */
	@FXML
	ListView<String> listView;

	/**
	 * Side-panel list casted as Observable List
	 */
	@FXML
	private ObservableList<String> obsList;
	
	/**
	 * Buttons in the Song Details pane
	 */
	@FXML
	Button deleteButton, updateButton;
	
	/**
	 * Menu items under "File"
	 */
	@FXML
	MenuItem newSongMenuItem, deleteAllMenuItem;
	
	/**
	 * Initialize list	
	 */
	public void start(Stage mainStage)
	{
		obsList = FXCollections.observableArrayList();	// TODO: fill somehow
		if (!obsList.isEmpty()) listView.setItems(obsList);
	}
	
	/**
	 * @param selectedSong
	 * Show selected song in details pane
	 */
	private void showItem(Song selectedSong)
	{
		// TODO
		return;
	}
	
	/**
	 * Update the current song with details in the text areas
	 */
	@FXML
	private void UpdateSong()
	{
		// TODO
	}
	
	/**
	 * Delete selected song
	 */
	@FXML
	private void DeleteSong()
	{
		// TODO
	}
	
	/**
	 * Add new song ith the details in the text areas
	 */
	@FXML
	private void AddSong()
	{
		// TODO
	}
}
