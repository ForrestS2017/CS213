package Control;

import Model.Song;
import Util.SongLibUtil;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
	 * TextAreas for editing/adding songs
	 */
	@FXML
	TextArea song, artist, album, year;
	
	/**
	 * Initialize list	
	 */
	private static final String FILE_PATH = "SongList.JSON";
	public void start(Stage mainStage) throws IOException {
		obsList = FXCollections.observableArrayList();	// TODO: fill somehow
		if (!obsList.isEmpty()) listView.setItems(obsList);

		Gson gson = new Gson();
		BufferedReader br = new BufferedReader(new FileReader(FILE_PATH)); //Read in songs from JSON file
		
		Song newSong = gson.fromJson(br, Song.class); //
		System.out.println(newSong.getName());
		System.out.println(newSong.getArtist());
		System.out.println(newSong.getYear());
		System.out.println(newSong.getAlbum());
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
	 * Add new song with the details in the text areas
	 */
	@FXML
	private void AddSong()
	{
		System.out.println("Adding Song");
		Song newSong = new Song(song.getText(), artist.getText(), album.getText(), year.getText());
		try
		{
			SongLibUtil.AddSong(newSong);
		} catch (Exception e)
		{
			System.out.println("Oops: " + e.toString());
		}
	}
}
