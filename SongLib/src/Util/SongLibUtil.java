/**
 * @author Forrest Smith
 * @author Jim Tang
 */

package Util;

import Model.Song;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

public class SongLibUtil {
    // TODO
	
	private static final String FILE_PATH = "SongList.JSON";

	/**
	 * @param songList the current list of songs to be stored once updated
	 * @param song the song to be added to the list of songs
	 * @return List of stored songs
	 * Write new song details to JSON file
	 */
	public static void WriteToJSON(ObservableList<Song> songList, Song song) throws IOException
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String songListJSON = gson.toJson(songList);
		BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false));
		songList.forEach((n) -> {
			try {
				writer.append(gson.toJson(n));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		writer.close();
	}
	
	/**
	 * @return List of stored songs
	 * Load song details from JSON file
	 */
	public static ObservableList<Song> LoadSongs() throws IOException
	{
		Gson gson = new Gson();
		BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
		return FXCollections.observableArrayList(gson.fromJson(reader, Song.class));
	}

	/**
	 * Deletes all content from JSON file
	 */
	public static void DeleteAll() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false));
		writer.write("");
		writer.close();
		System.out.println("Cleared song list");
	}
}
