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
	
	/**
	 * @param song Song to add
	 * @return True if successful, False if failed
	 * Append new song details to JSON file if song is not already stored
	 */
	public static void AddSong(Song song) throws IOException
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String songJSON = gson.toJson(song);
		BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true));
		writer.append(songJSON);
		writer.close();
		System.out.println("Appended: " + songJSON + "\nTo: " + FILE_PATH);
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
	 * @param song Song to edit
	 * @return True if successful, False if failed
	 * Edit song details in JSON file
	 */
	public static void EditSong()
	{
		// TODO
	}
	
	/**
	 * @param song Song to remove
	 * @return True if successful, False if failed
	 * Delete song from  JSON file
	 */
	public static void DeleteSong(ObservableList<Song> songList, Song song) throws IOException
	{
		if(songList.remove(song))
		{
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String songJSON = gson.toJson(song);
			BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false));
			writer.write(songJSON);
			writer.close();
			System.out.println("Wrote: " + songJSON + "\nTo: " + FILE_PATH);
		}
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
