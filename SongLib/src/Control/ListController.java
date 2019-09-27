package Control;

import Model.Song;
import Util.SongLibUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Forrest Smith
 * @author Jim Tang
 */

public class ListController {
	/**
	 * Side-panel list
	 */
	@FXML
	ListView<Song> listView;

	/**
	 * Side-panel list casted as Observable List
	 */
	@FXML
	private ObservableList<Song> obsList;
	
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

	private static void order(ObservableList<Song> songs) {
		Collections.sort(songs, new Comparator() {
			public int compare(Object o1, Object o2) {
				String x1 = ((Song) o1).getName();
				String x2 = ((Song) o2).getName();
				int sComp = x1.compareTo(x2);

				if (sComp != 0) {
					return sComp;
				}

				x1 = ((Song) o1).getArtist();
				x2 = ((Song) o2).getArtist();
				return x1.compareTo(x2);
			}});
	}

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
				obsList.add(newSong);
			}
		}

		order(obsList);
		listView.setItems(obsList);
		listView.setCellFactory((list) -> {
			return new ListCell<Song>() {
				protected void updateItem(Song item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.getName() + " " + item.getArtist());
					}
				}
			};
		});

		//Add listeners for all the songs in the song list so that when clicked, info populates fields
		listView.getSelectionModel()
				.selectedIndexProperty()
				.addListener((obs,oldVal,newVal) -> showItem(listView.getSelectionModel().getSelectedItem()));
	}
	
	/**
	 * @param selectedSong
	 * Show selected song in details pane
	 */
	private void showItem(Song selectedSong)
	{
		song.setText(selectedSong.getName());
		artist.setText(selectedSong.getArtist());
		album.setText(selectedSong.getAlbum());
		year.setText(selectedSong.getYear());
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
	private void AddSong(ActionEvent actionEvent)
	{
		System.out.println("Adding Song");
		Song newSong = new Song(song.getText(), artist.getText(), album.getText(), year.getText());
		try
		{
			SongLibUtil.AddSong(newSong);
			obsList.add(newSong);
		} catch (Exception e)
		{
			System.out.println("Oops: " + e.toString());
		}
	}

	/**
	 * Delete all songs
	 */
	@FXML
	private void DeleteAll()
	{
		try
		{
			SongLibUtil.DeleteAll();
		} catch (Exception e)
		{
			System.out.println("Oops: " + e.toString());
		}
	}
}
