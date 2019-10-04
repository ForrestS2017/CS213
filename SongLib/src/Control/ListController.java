/**
 * @author Forrest Smith
 * @author Jim Tang
 */

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
	
	private static final String FILE_PATH = "SongList.JSON";
	private InputStream Reader;

	/**
	 * Initialize list	
	 */
	public void start(Stage mainStage) throws IOException {
		obsList = FXCollections.observableArrayList();
		InputStream is;
		try
		{
			is = new FileInputStream(FILE_PATH);
		}
		catch (FileNotFoundException e)
		{
			FileWriter file = new FileWriter(FILE_PATH);
			is = new FileInputStream(FILE_PATH);						
		}
		Reader r = new InputStreamReader(is, "UTF-8");
		Gson gson = new GsonBuilder().create();
		JsonStreamParser p = new JsonStreamParser(r);

		//Convert all data into Song objects from SongList.JSON
		ArrayList<Song> songObjs = new ArrayList<Song>();
		if(is.available() != 0) {
			while (p.hasNext()) {
				JsonElement e = p.next();
				if (e.isJsonObject()) {
					Song newSong = gson.fromJson(e, Song.class);
					obsList.add(newSong);
				}
			}
		}

		//Turn song data into ListCells
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
	 * Make sure song and artist are not blank
	 * @return true if valid, else return false
	 */
	private boolean validate()
	{
		if(song.getText().isBlank() || artist.getText().isBlank()){ //One of the fields is blank
			showAlert("Invalid Input", "Both song name and artist are required to add song");
			System.out.println("OOps: Missing song name or artist.");
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * @param selectedSong
	 * Show selected song in details pane
	 */
	private void showItem(Song selectedSong)
	{
		if(selectedSong == null) return;
		song.setText(selectedSong.getName());
		artist.setText(selectedSong.getArtist());
		album.setText(selectedSong.getAlbum());
		year.setText(selectedSong.getYear());
	}

	/**
	 * Add new song with the details in the text areas
	 */
	@FXML
	private void AddSong()
	{
		if(!validate()){
			return;
		}
		Song newSong = new Song(song.getText(), artist.getText(), album.getText(), year.getText());
		try
		{
			if(insertAlphabetically(newSong)) { //Inserts the new song in the correct place of the array list
				SongLibUtil.WriteToJSON(obsList, newSong); //Write the new JSON file with the added song
				showAlert("Add Song", "Added: " + newSong.getName());
				System.out.println("Added Song: " + newSong.getName() + "\nTo: " + FILE_PATH);
			}
			else {
				showAlert("Duplicate error", "Song/Artist must be unique");
			}
		} catch (Exception e)
		{
			System.out.println("Oops: " + e.toString());
		}
	}

	/**
	 * Update the current song with details in the text areas
	 */
	@FXML
	private void UpdateSong()
	{
		if(!validate()){
			return;
		}
		Song selected = listView.getSelectionModel().getSelectedItem();
		try
		{
			Song newSong = new Song(song.getText(), artist.getText(), album.getText(), year.getText());
			if(selected.getName().equals(song.getText()) && selected.getArtist().equals(artist.getText())) { //If update is only updating album/year then don't need to check for duplicates or sort
				 selected.setAlbum(album.getText());
				 selected.setYear(year.getText());
				 SongLibUtil.WriteToJSON(obsList, selected);
			}
			else if (insertAlphabetically(newSong)) { //If update is not a duplicate, then remove the selected song. Else, show duplicate error
				obsList.remove(selected);
				SongLibUtil.WriteToJSON(obsList, selected);
				System.out.println("Song updated");
			}
			else { //If this case is reached, song must be a duplicate
				showAlert("Duplicate error", "Song/Artist must be unique");
			}

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
			showAlert("Delete Song", "Deleted: " + selected.getName());
			System.out.println("Deleted Song: " + selected.getName() + "\nFrom: " + FILE_PATH);
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
			obsList.clear();
			SongLibUtil.DeleteAll();
		} catch (Exception e)
		{
			System.out.println("Oops: " + e.toString());
		}
	}

	/**
	 * @param song
	 * Method to insert new songs into the correct place in obsList
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
					return false;
				}
				else if (song.getArtist().compareToIgnoreCase(obsList.get(i).getArtist()) < 0) {
					obsList.add(i, song);
					return true;
				}
			}
		}
		obsList.add(obsList.size(), song); //Since this was reached it must be the highest value alphabetically
		return true;
	}

	/**
	 * Alerts for when user inputs non-kosher data
	 */
	private void showAlert(String header, String content) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Alert");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}

