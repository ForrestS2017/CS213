package Util;

import Model.Song;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

/**
 * @author Forrest Smith
 * @author Jim Tang
 */

public class SongLibUtil {
    // TODO
	
	private static final String FILE_PATH = "SongList.JSON";

	//My reasoning for using this instead of the add/write method is that it's easier to just rewrite the file each time since
	//the songs have to be in alphabetical order. Would be a pain to append to the correct place in the JSON file for every insert
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
	 * Write new song details to JSON file
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
