package Model;

import java.util.UUID;

/**
 * @author Forrest Smith
 * @author Jim Tang
 */

public class Song {
    
	private String album, artist, name, year, ID;
	
	public Song(String album, String artist, String name, String year)
	{
		this.album = album;
		this.artist = artist;
		this.name = name;
		this.year = year;
		
		String id = UUID.randomUUID().toString();
		ID = id.isEmpty() ? id : null;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public boolean setArtist(String artist) {
		if (artist.isBlank()) return false;
		this.artist = artist;
		return true;
	}

	public boolean setName(String name) {
		if (name.isBlank()) return false;
		this.name = name;
		return true;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getAlbum() {
		return this.album;
	}

	public String getArtist() {
		return this.artist;
	}
	
	public String getName() {
		return this.name;
	}

	public String getYear() {
		return this.year;
	}

	public String getID() {
		return this.ID;
	}

	/**
	 * @param song the song to compare this song to
	 * @return true if the song exists already
	 */
	public boolean compareSong(Song song)
	{
		// TODO: By name & artist? Or UUID?
		return true;
	}
}
