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
import javafx.scene.control.*;
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

	/**
	 * Method to insert new songs into the correct place in obsList
	 * @param song
	 */
	private boolean insertAlphabetically(Song song) {
		if(obsList.size() == 0) {
			obsList.add(0, song);
			return true;
		}
		for (int i=0; i<obsList.size(); i++) {
			if (song.getName().compareToIgnoreCase(obsList.get(i).getName()) < 0) {
				obsList.add(i, song);
				return true;
			}
			else if (song.getName().compareToIgnoreCase(obsList.get(i).getName()) == 0) {
				if (song.getArtist().compareToIgnoreCase(obsList.get(i).getArtist()) == 0) {
					System.out.println("Duplicate. Not adding");
					return false;
				}
				else if (song.getArtist().compareToIgnoreCase(obsList.get(i).getArtist()) < 0) {
					obsList.add(i, song);
					return true;
				}
			}
		}
		obsList.add(obsList.size(), song); //Since this was reached it must be the last alphabetically
		return true;
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
		if(is.available() !=0) {
			while (p.hasNext()) { //Convert all data into Song objects from SongList.JSON
				JsonElement e = p.next();
				if (e.isJsonObject()) {
					Song newSong = gson.fromJson(e, Song.class);
					obsList.add(newSong);
				}
			}
		}

		listView.setItems(obsList);
		listView.setCellFactory((list) -> {
			return new ListCell<Song>() {
				protected void updateItem(Song item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.getName() + ", by " + item.getArtist());
					}
				}
			};
		});

		//Add listeners for all the songs in the song list so that when clicked, info populates fields
		listView.getSelectionModel()
				.selectedIndexProperty()
				.addListener((e) -> showItem(listView.getSelectionModel().getSelectedItem()));

		//Select first song in list
		listView.getSelectionModel().select(0);
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
	}
	
	/**
	 * Update the current song with details in the text areas
	 */
	@FXML
	private void UpdateSong()
	{
		Song selected = listView.getSelectionModel().getSelectedItem();
		try
		{
			Song newSong = new Song(song.getText(), artist.getText(), album.getText(), year.getText());
			if (insertAlphabetically(newSong)) { //If update is not a duplicate, then remove the selected song. Else, show duplicate error
				obsList.remove(selected);
			}
			else {
				System.out.println("Duplicate error");
			}
			SongLibUtil.WriteToJSON(obsList, selected);
		} catch (Exception e)
		{
			System.out.println("Oops: " + e.toString());
		}
	}
	
	/**
	 * Delete selected song
	 */
	@FXML
	private void DeleteSong()
	{
		Song selected = listView.getSelectionModel().getSelectedItem();
		try
		{
			obsList.remove(selected);
			SongLibUtil.WriteToJSON(obsList, selected);
		} catch (Exception e)
		{
			System.out.println("Oops: " + e.toString());
		}

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
			insertAlphabetically(newSong); //Inserts the new song in the correct place of the array list
			SongLibUtil.WriteToJSON(obsList, newSong);
			System.out.println("Appended: " + newSong.getName() + "\nTo: " + FILE_PATH);
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
