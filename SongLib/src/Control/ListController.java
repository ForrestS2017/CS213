package Control;

import Model.Song;
import Util.SongLibUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

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
	private InputStream Reader;

	public void start(Stage mainStage) throws IOException {
		obsList = FXCollections.observableArrayList();
		InputStream is = new FileInputStream(FILE_PATH);
		Reader r = new InputStreamReader(is, "UTF-8");
		Gson gson = new GsonBuilder().create();
		JsonStreamParser p = new JsonStreamParser(r);

		ArrayList<Song> songObjs = new ArrayList<Song>();
		while (p.hasNext()) { //Convert all data into Song objects from SongList.JSON
			JsonElement e = p.next();
			if (e.isJsonObject()) {
				Song newSong = gson.fromJson(e, Song.class);
				songObjs.add(newSong);
				obsList.add(newSong.getName() + ", " + newSong.getArtist());
			}
		}
		obsList.sort(String::compareToIgnoreCase); //Sort by song name alphabetically
		listView.setItems(obsList);

		//Add listeners for all the songs in the song list so that when clicked, info populates fields
//		listView.getSelectionModel()
//				.selectedIndexProperty()
//				.addListener((obs,oldVal,newVal) -> showItem(songObjs));
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
